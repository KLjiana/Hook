package org.hook.mod.client.handler;

import net.minecraft.client.Camera;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.hook.mod.client.KeyBindings;
import org.hook.mod.entity.hook.AbstractHookEntity;
import org.hook.mod.network.NetworkHandler;
import org.hook.mod.network.c2s.HookThrowingPacketC2S;
import org.hook.mod.util.CuriosUtils;

@OnlyIn(Dist.CLIENT)
public final class HookThrowingHandler {
    private static final Vec3 LEFT_VEC = new Vec3(0.0, -0.008, 0.0);
    private static final Vec3 RIGHT_VEC = new Vec3(0.0, 0.008, 0.0);
    private static boolean aBoolean = true;
    public static void handle(Camera camera, LocalPlayer localPlayer) {
        CuriosUtils.getSlot(localPlayer, "hook", 0).ifPresent(itemStack -> {
            if (KeyBindings.HOOK.get().consumeClick()) {
                float rotX = camera.getXRot();
                float rotY = camera.getYRot();
                NetworkHandler.CHANNEL.sendToServer(HookThrowingPacketC2S.hook(aBoolean, rotX, rotY));
            }
            CompoundTag tag = itemStack.getTag();
            if (tag != null && tag.get("hooks") instanceof ListTag list) {
                list.forEach(tag1 -> {
                    int id = ((CompoundTag) tag1).getInt("id");
                    aBoolean = localPlayer.level().getEntity(id)==null;
                    if (localPlayer.level().getEntity(id) instanceof AbstractHookEntity hookEntity) {
                        if (hookEntity.getHookState() == AbstractHookEntity.HookState.HOOKED){
                            Vec3 subtract = hookEntity.position().subtract(localPlayer.position());
                            Vec3 deltaMovement = localPlayer.getDeltaMovement();
                            if (localPlayer.input.up) {
                                localPlayer.setDeltaMovement(deltaMovement.add(Vec3.directionFromRotation(localPlayer.getXRot(), localPlayer.getYRot()).scale(0.05)));
                            }
                            if (localPlayer.input.leftImpulse != 0.0) {
                                localPlayer.setDeltaMovement(deltaMovement.add(subtract.cross(localPlayer.input.left ? LEFT_VEC : RIGHT_VEC)));

                            }
                            if (localPlayer.input.jumping) {
                                localPlayer.setDeltaMovement(deltaMovement.add(0, 0.2, 0));
                            }
                            if (localPlayer.input.shiftKeyDown) {
                                localPlayer.setDeltaMovement(deltaMovement.add(0, -0.3, 0));
                            }
                            if (subtract.lengthSqr() < 1.0) {
                                Vec3 motion = localPlayer.getDeltaMovement().scale(0.05);
                                localPlayer.setDeltaMovement(motion.x, 0.0, motion.z);
                            } else {
                                Vec3 motion = subtract.normalize().scale(0.13);
                                localPlayer.setDeltaMovement(localPlayer.getDeltaMovement().scale(0.96).add(motion));
                            }
                        }
                    }
                });
            }
        });
    }
}
