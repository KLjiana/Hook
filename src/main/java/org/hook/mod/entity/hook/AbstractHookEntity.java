package org.hook.mod.entity.hook;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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
    public static final EntityDataAccessor<Integer> DATA_HOOK_STATE = SynchedEntityData.defineId(AbstractHookEntity.class, EntityDataSerializers.INT);
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
                Vec3 deltaMovement = localPlayer.getDeltaMovement();
                Vec3 subtract = position().subtract(localPlayer.position());

                // 增加WASD移动幅度
                if (localPlayer.input.left && deltaMovement.y < -1.2) {
                    Hook.LOGGER.info(String.valueOf(deltaMovement));
                    localPlayer.setDeltaMovement(deltaMovement.subtract(subtract.cross(new Vec3(0, 0.4, 0)).normalize().scale(1.2)));
                }
                if (localPlayer.input.right && deltaMovement.y < 1.2) {
                    Hook.LOGGER.info(String.valueOf(deltaMovement));
                    localPlayer.setDeltaMovement(deltaMovement.add(subtract.cross(new Vec3(0, 0.4, 0)).normalize().scale(1.2)));
                }

                // 增加空格（跳跃）键的向上移动幅度
                if (localPlayer.input.jumping && deltaMovement.y < 2.2) {
                    Hook.LOGGER.info(String.valueOf(deltaMovement));
                    localPlayer.setDeltaMovement(deltaMovement.add(0, 0.6, 0));
                } else if(localPlayer.input.jumping) {
                    localPlayer.setDeltaMovement(deltaMovement.add(0, -2, 0));
                }
            }
            Vec3 subtract = position().subtract(owner.position());
            Vec3 motions = subtract.normalize().scale(0.15); // 调整钩子拉动的速度

            owner.setDeltaMovement(owner.getDeltaMovement().scale(0.85).add(motions)); // 调整减速度
            return;
        }

        if (level().isClientSide) return;
        Optional<ItemStack> hook = CuriosUtils.getSlot((LivingEntity) owner, "hook", 0);
        if (hook.isEmpty()) {
            discard();
            return;
        }

        if (hookState == HookState.PULL) {
            if (distanceToSqr(owner) < 2.0) {
                discard();
                return;
            } else if(distanceToSqr(owner) > 1500.0) {
                setDeltaMovement(getDeltaMovement().scale(0.85).add(owner.position().subtract(position()).normalize().scale(4)));
            } else {
                setDeltaMovement(getDeltaMovement().scale(0.85).add(owner.position().subtract(position()).normalize().scale(0.5)));
            }
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

                double newX = pos.x + adjustX * 0.8;
                double newY = pos.y + adjustY * 0.8;
                double newZ = pos.z + adjustZ * 0.8;

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
        setDeltaMovement(getDeltaMovement().scale(15));
    }

    public enum HookState implements StringRepresentable {
        PUSH(0, "push"), // 发射
        PULL(1, "pull"), // 拉回
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
