package org.hook.mod.network.c2s;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
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

public record HookThrowingPacketC2S(boolean aBoolean, float x, float y) {
    public static int id;

    public static HookThrowingPacketC2S hook(boolean aBoolean, float rotX, float rotY) {
        return new HookThrowingPacketC2S(aBoolean, rotX, rotY);
    }

    public static void encode(HookThrowingPacketC2S packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBoolean(packet.aBoolean);
        friendlyByteBuf.writeFloat(packet.x);
        friendlyByteBuf.writeFloat(packet.y);
    }

    public static HookThrowingPacketC2S decode(FriendlyByteBuf friendlyByteBuf) {
        return new HookThrowingPacketC2S(friendlyByteBuf.readBoolean(), friendlyByteBuf.readFloat(), friendlyByteBuf.readFloat());
    }

    public static void handle(HookThrowingPacketC2S packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;
            ServerLevel level = player.serverLevel();
            CuriosUtils.getSlot(player, "hook", 0).ifPresent(itemStack -> {
                if (itemStack.getItem() instanceof AbstractHookItem item && item.canHook(level, itemStack)) {
                    AbstractHookEntity hook = item.getHook(itemStack, item, player, level);
                    CompoundTag tag = new CompoundTag();
                    tag.putInt("id", hook.getId());
                    if (itemStack.getOrCreateTag().get("hooks") instanceof ListTag list) {
                        AbstractHookItem.removeAll(list, level);
                        list.add(tag);
                    }
                    if (packet.aBoolean) {
                        hook.shootFromRotation(player, packet.x, packet.y, 0.0F, 0.1F, 0.5F);
                        level.addFreshEntity(hook);
                    } else {
                        Entity entity = level.getEntity(hook.getId());
                        if (entity instanceof AbstractHookEntity hookEntity) {
                            hookEntity.setHookState(AbstractHookEntity.HookState.PULL);
                        }
                    }
                }
            });
        });
        context.setPacketHandled(true);
    }
}
