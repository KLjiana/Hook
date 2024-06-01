package org.confluence.mod.client.handler;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public final class ClientPacketHandler {
    private static int maxMana = 20;
    private static int currentMana = 20;

    private static boolean echoBlockVisible = false;
    private static boolean showHolyWaterColor = false;
    private static boolean autoAttack = false;
    private static boolean hasCthulhu = false;

    private static ResourceLocation moonTexture = null;
    private static int moonSpecific = -1;

    public static int getCurrentMana() {
        return currentMana;
    }

    public static int getMaxMana() {
        return maxMana;
    }

    public static boolean isEchoBlockVisible() {
        return echoBlockVisible;
    }

    public static boolean showHolyWaterColor() {
        return showHolyWaterColor;
    }

    public static boolean couldAutoAttack() {
        return autoAttack;
    }

    public static boolean isHasCthulhu() {
        return hasCthulhu;
    }

    public static @Nullable ResourceLocation getMoonTexture() {
        return moonTexture;
    }

    public static int getMoonSpecific() {
        return moonSpecific;
    }

}
