package org.confluence.mod.client;

import de.dafuqs.revelationary.api.revelations.WorldRendererAccessor;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.network.EchoBlockVisibilityPacket;
import org.confluence.mod.network.HolyWaterColorUpdatePacket;
import org.confluence.mod.network.ManaPacketS2C;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class ClientPacketHandler {
    private static int maxMana = 20;
    private static int currentMana = 20;
    private static boolean echoBlockVisible = false;
    private static boolean showHolyWaterColor = false;

    public static void handleMana(ManaPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            maxMana = packet.maxMana();
            currentMana = packet.currentMana();
        });
        context.setPacketHandled(true);
    }

    public static void handleEchoBlock(EchoBlockVisibilityPacket packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            echoBlockVisible = packet.visible();
            ((WorldRendererAccessor) Minecraft.getInstance().levelRenderer).rebuildAllChunks();
        });
        context.setPacketHandled(true);
    }

    public static void handleHolyWater(HolyWaterColorUpdatePacket packet, Supplier<NetworkEvent.Context> ctx){
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            showHolyWaterColor = packet.show();
            ((WorldRendererAccessor) Minecraft.getInstance().levelRenderer).rebuildAllChunks();
        });
        context.setPacketHandled(true);
    }

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
}
