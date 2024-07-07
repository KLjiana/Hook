package org.hook.mod.entity.hook;

import com.mojang.serialization.Codec;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
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

    protected void onHooked(BlockHitResult hitResult) {
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

            if (owner instanceof LocalPlayer localPlayer) {
                if (localPlayer.input.up) {
                        Hook.LOGGER.info("1114");
                }
            }

            Vec3 playerPos = owner.position();
            Vec3 hookPos = position();

            Vec3 direction = hookPos.subtract(playerPos).normalize();
            double pullStrength = 0.2;

            if (owner.isCrouching()) return;

            Vec3 subtract = position().subtract(owner.position());
            double distance = subtract.lengthSqr();

            if (distance < 1.0) {
                Vec3 motions = owner.getDeltaMovement().scale(0.05); // 调整玩家加速的速度
                owner.setDeltaMovement(motions.x, 0.0, motions.z);
            } else {
                Vec3 motions = subtract.normalize().scale(0.015); // 调整钩子拉动的速度
                owner.setDeltaMovement(owner.getDeltaMovement().scale(0.95).add(motions)); // 调整减速度
            }

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

            if (hitResult.getType() == HitResult.Type.BLOCK) {
                Vec3 hitPos = hitResult.getLocation();

                double adjustX = hitPos.x - pos.x;
                double adjustY = hitPos.y - pos.y;
                double adjustZ = hitPos.z - pos.z;

                double newX = pos.x + adjustX * 0.5;
                double newY = pos.y + adjustY * 0.5;
                double newZ = pos.z + adjustZ * 0.5;

                if (hitResult.isInside()) {
                    setPos(getX() - adjustX, getY() - adjustY, getZ() - adjustZ);
                } else {
                    setPos(newX, newY, newZ);
                }

                onHitBlock(hitResult);
                onHooked(hitResult);
            }
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
