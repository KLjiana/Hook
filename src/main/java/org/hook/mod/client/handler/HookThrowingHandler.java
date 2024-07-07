package org.hook.mod.client.handler;

import net.minecraft.client.Camera;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.hook.mod.client.KeyBindings;
import org.hook.mod.network.NetworkHandler;
import org.hook.mod.network.c2s.HookThrowingPacketC2S;

@OnlyIn(Dist.CLIENT)
public final class HookThrowingHandler {
    public static void handle(Camera camera) {
        if (KeyBindings.HOOK.get().consumeClick()) {
            float rotX = camera.getXRot();
            float rotY = camera.getYRot();
            NetworkHandler.CHANNEL.sendToServer(HookThrowingPacketC2S.hook(rotX, rotY));
        }
    }
}
