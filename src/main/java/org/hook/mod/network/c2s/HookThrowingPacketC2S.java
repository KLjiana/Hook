package org.hook.mod.network.c2s;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;
import org.hook.mod.Hook;
import org.hook.mod.entity.hook.AbstractHookEntity;
import org.hook.mod.item.hook.AbstractHookItem;
import org.hook.mod.util.CuriosUtils;

import java.util.function.Supplier;

public record HookThrowingPacketC2S() {
    public static int id;
    public static boolean hookLive = false;
    public static HookThrowingPacketC2S hook() {
        return new HookThrowingPacketC2S();
    }

    public static void encode(HookThrowingPacketC2S packet, FriendlyByteBuf friendlyByteBuf) {

    }

    public static HookThrowingPacketC2S decode(FriendlyByteBuf friendlyByteBuf) {
        return new HookThrowingPacketC2S();
    }

    public static void handle(HookThrowingPacketC2S packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;
            ServerLevel level = player.serverLevel();

            CuriosUtils.getSlot(player, "hook", 0).ifPresent(itemStack -> {
                if (itemStack.getItem() instanceof AbstractHookItem item && item.canHook(level, itemStack) && level.getEntity(id) == null) {
                    AbstractHookEntity hook = item.getHook(itemStack, item, player, level);
                    hook.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, item.getHookVelocity(), 0.5F);
                    level.addFreshEntity(hook);
                    id = hook.getId();
                } else {
                    Entity entity = level.getEntity(id);
                    if (entity instanceof AbstractHookEntity hookEntity) {
                        hookEntity.setHookState(AbstractHookEntity.HookState.PULL);
                    }
                }
            });
        });
        context.setPacketHandled(true);
    }
}
