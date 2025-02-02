package org.hook.mod.client;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.hook.mod.Hook;
import org.hook.mod.client.handler.HookThrowingHandler;

@Mod.EventBusSubscriber(modid = Hook.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class ForgeClient {
    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) return;
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        HookThrowingHandler.handle(camera, localPlayer);
    }
}
