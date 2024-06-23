package org.hook.mod.entity.hook;

import com.mojang.serialization.Codec;
import com.sun.jdi.Value;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.hook.mod.Hook;
import org.hook.mod.item.hook.AbstractHookItem;
import org.hook.mod.util.CuriosUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.IntFunction;

public abstract class AbstractHookEntity extends Projectile {
    private static final EntityDataAccessor<Integer> DATA_HOOK_STATE = SynchedEntityData.defineId(AbstractHookEntity.class, EntityDataSerializers.INT);
    private final float hookRangeSqr;
    private final AbstractHookItem.HookType hookType;
    public float lastDelta = 0.0F;

    public AbstractHookEntity(EntityType<? extends AbstractHookEntity> entityType, Level pLevel) {
        super(entityType, pLevel);
        this.hookRangeSqr = 0.0F;
        this.hookType = null;
    }

    public AbstractHookEntity(EntityType<? extends AbstractHookEntity> entityType, AbstractHookItem item, Player player, Level level) {
        super(entityType, level);
        this.hookRangeSqr = item.getHookRange() * item.getHookRange();
        this.hookType = item.getHookType();
        setOwner(player);
        setNoGravity(true);
        setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(DATA_HOOK_STATE, 0);
    }

    public HookState getHookState() {
        return HookState.byId(entityData.get(DATA_HOOK_STATE));
    }

    public void setHookState(HookState state) {
        entityData.set(DATA_HOOK_STATE, state.id);
    }

    public double getPullVelocity() {
        return 0.15;
    }

    protected void onHooked(BlockHitResult hitResult, ItemStack itemStack) {
        BlockPos blockPos = hitResult.getBlockPos();
        level().gameEvent(GameEvent.PROJECTILE_LAND, blockPos, GameEvent.Context.of(this, level().getBlockState(blockPos)));
        setDeltaMovement(Vec3.ZERO);
        setHookState(HookState.HOOKED);
        this.hasImpulse = true;
    }

    @Override
    public void tick() {
        super.tick();
        Entity owner = getOwner();
        if (owner == null || owner.isRemoved()) {
            discard();
            return;
        }

        Vec3 motion = getDeltaMovement();
        double x = getX() + motion.x;
        double y = getY() + motion.y;
        double z = getZ() + motion.z;
        setPos(x, y, z);

        HookState hookState = getHookState();
        if (hookState == HookState.HOOKED) {
            // 获取玩家和钩子的位置
            Vec3 playerPos = owner.position();
            Vec3 hookPos = position();

            // 计算从玩家到钩子的方向向量
            Vec3 direction = hookPos.subtract(playerPos).normalize();

            // 计算拉力大小，可以根据需要调整这个值
            double pullStrength = 0.2;

            // 调整玩家的速度向量
            Vec3 newVelocity = owner.getDeltaMovement().add(direction.scale(pullStrength));
            owner.setDeltaMovement(newVelocity);
        }
        if (hookState == HookState.PULL) {
            setDeltaMovement(getDeltaMovement().scale(0.95).add(owner.position().subtract(position()).normalize().scale(0.5)));
            if (distanceToSqr(owner) < 4.0) {
                discard();
                return;
            }
        }
        if (level().isClientSide) return;
        Optional<ItemStack> hook = CuriosUtils.getSlot((LivingEntity) owner, "hook", 0);
        if (hook.isEmpty()) {
            discard();
            return;
        }
        if (distanceToSqr(owner) > hookRangeSqr) {
            setHookState(HookState.PULL);
            return;
        }
        if (hookState == HookState.PUSH) {
            Vec3 pos = position();
            Vec3 nextPos = pos.add(motion);
            BlockHitResult hitResult = level().clip(new ClipContext(pos, nextPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
            if (hitResult.getType() != HitResult.Type.BLOCK) return;
            motion = motion.scale(0.5);
            if (hitResult.isInside()) {
                setPos(getX() - motion.x, getY() - motion.y, getZ() - motion.z);
            } else {
                setPos(getX() + motion.x, getY() + motion.y, getZ() + motion.z);
            }
            onHitBlock(hitResult);
            onHooked(hitResult, hook.get());
        }
    }

    @Override
    public void shootFromRotation(@NotNull Entity pShooter, float pX, float pY, float pZ, float pVelocity, float pInaccuracy) {
        float x = -Mth.sin(pY * Mth.DEG_TO_RAD) * Mth.cos(pX * Mth.DEG_TO_RAD);
        float y = -Mth.sin((pX + pZ) * Mth.DEG_TO_RAD);
        float z = Mth.cos(pY * Mth.DEG_TO_RAD) * Mth.cos(pX * Mth.DEG_TO_RAD);
        shoot(x, y, z, pVelocity, pInaccuracy);
        Vec3 motion = pShooter.getDeltaMovement();
        setDeltaMovement(getDeltaMovement().add(motion.x, 0.0, motion.z));
    }

    public enum HookState implements StringRepresentable {
        PUSH(0, "push"), // 发射
        PULL(1, "pop"), // 拉回
        HOOKED(2, "hooked"); // 抓住

        public static final Codec<HookState> CODEC = StringRepresentable.fromEnum(HookState::values);
        private static final IntFunction<HookState> BY_ID = ByIdMap.continuous(HookState::getId, values(), ByIdMap.OutOfBoundsStrategy.CLAMP);
        final int id;
        private final String name;

        HookState(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public static HookState byId(int pId) {
            return BY_ID.apply(pId);
        }

        public @NotNull String getSerializedName() {
            return name;
        }
    }
}
