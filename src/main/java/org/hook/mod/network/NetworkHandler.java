package org.hook.mod.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.hook.mod.Hook;
import org.hook.mod.network.c2s.HookThrowingPacketC2S;

public final class NetworkHandler {
    public static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
        new ResourceLocation(Hook.MODID, "main"),
        () -> PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;

    public static void register() {
        CHANNEL.registerMessage(packetId++, HookThrowingPacketC2S.class, HookThrowingPacketC2S::encode, HookThrowingPacketC2S::decode, HookThrowingPacketC2S::handle);
    }
}
