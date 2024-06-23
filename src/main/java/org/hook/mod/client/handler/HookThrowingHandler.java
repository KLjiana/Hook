package org.hook.mod.client.handler;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.hook.mod.client.KeyBindings;
import org.hook.mod.network.NetworkHandler;
import org.hook.mod.network.c2s.HookThrowingPacketC2S;

@OnlyIn(Dist.CLIENT)
public final class HookThrowingHandler {
    public static void handle() {
        if (KeyBindings.HOOK.get().consumeClick()) {
            NetworkHandler.CHANNEL.sendToServer(HookThrowingPacketC2S.hook());
        }
    }
}
