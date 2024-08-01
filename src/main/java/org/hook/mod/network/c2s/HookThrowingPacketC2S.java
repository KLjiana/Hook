package org.hook.mod.network.c2s;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import org.hook.mod.entity.hook.AbstractHookEntity;
import org.hook.mod.item.hook.AbstractHookItem;
import org.hook.mod.util.CuriosUtils;

import java.util.Optional;
import java.util.function.Supplier;

public record HookThrowingPacketC2S(float x, float y) {
    public static int id;

    public static HookThrowingPacketC2S hook(float rotX, float rotY) {
        return new HookThrowingPacketC2S(rotX, rotY);
    }

    public static void encode(HookThrowingPacketC2S packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeFloat(packet.x);
        friendlyByteBuf.writeFloat(packet.y);
    }

    public static HookThrowingPacketC2S decode(FriendlyByteBuf friendlyByteBuf) {
        return new HookThrowingPacketC2S(friendlyByteBuf.readFloat(), friendlyByteBuf.readFloat());
    }

    public static void handle(HookThrowingPacketC2S rot, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;
            ServerLevel level = player.serverLevel();

            if (level.getEntity(id) == null) {
                Optional<ItemStack> hook = CuriosUtils.getSlot(player, "hook", 0);
                AbstractHookItem item = (AbstractHookItem) hook.get().getItem();
                AbstractHookEntity hookEntity = item.getHook(hook.get(), item, player, level);
                id = hookEntity.getId();

                hookEntity.shootFromRotation(player, rot.x, rot.y, 0.0F, 0.1F, 0.0F);
                level.addFreshEntity(hookEntity);
            } else{
                Entity entity = level.getEntity(id);
                if (entity instanceof AbstractHookEntity hookEntity) {
                    hookEntity.setHookState(AbstractHookEntity.HookState.PULL);
                }
            }
        });
        context.setPacketHandled(true);
    }
}
