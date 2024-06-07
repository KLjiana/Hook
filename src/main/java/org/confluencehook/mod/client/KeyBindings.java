package org.confluencehook.mod.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.confluencehook.mod.ConfluenceHook;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = ConfluenceHook.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class KeyBindings {
    @SubscribeEvent
    public static void keyBinding(RegisterKeyMappingsEvent event) {
        event.register(HOOK.get());
    }

    public static final Lazy<KeyMapping> HOOK = Lazy.of(() -> new KeyMapping(
        "key.confluencehook.hook",
        KeyConflictContext.IN_GAME,
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_F,
        "key.categories.movement"
    ));
}
