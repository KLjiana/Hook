package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;
import org.confluence.mod.item.curio.IFunctionCouldEnable;
import org.confluence.mod.item.curio.informational.*;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.util.CuriosUtils;

import java.util.ArrayList;

/**
 * @param enabled length == 13
 */
public record InfoCurioCheckPacketS2C(byte[] enabled) {
    public static void encode(InfoCurioCheckPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeByteArray(packet.enabled);
    }

    public static InfoCurioCheckPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new InfoCurioCheckPacketS2C(friendlyByteBuf.readByteArray());
    }

    public static void send(ServerPlayer serverPlayer, Inventory inventory) {
        ArrayList<ItemStack> itemStacks = CuriosUtils.getCurios(serverPlayer);
        itemStacks.addAll(inventory.items);
        byte watch = 0;
        byte weatherRadio = 0;
        byte sextant = 0;
        byte fishermansPocketGuide = 0;
        byte metalDetector = 0;
        byte lifeFormAnalyzer = 0;
        byte radar = 0;
        byte tallyCounter = 0;
        byte dpsMeter = 0;
        byte stopwatch = 0;
        byte compass = 0;
        byte depthMeter = 0;
        byte mechanicalLens = 0;
        for (ItemStack stack : itemStacks) {
            Item item = stack.getItem();
            if (item instanceof IFunctionCouldEnable couldEnable && !couldEnable.isEnabled(stack)) continue;
            if (watch < 1 && item instanceof HourWatch) watch = 1;
            else if (watch < 2 && item instanceof HalfHourWatch) watch = 2;
            else if (watch < 3 && IWatch.isMinuteWatch(item)) watch = 3;
            if (item instanceof IWeatherRadio) weatherRadio = 1;
            if (item instanceof ISextant) sextant = 1;
            if (item instanceof IFishermansPocketGuide) fishermansPocketGuide = 1;
            if (item instanceof IMetalDetector) metalDetector = 1;
            if (item instanceof ILifeFormAnalyzer) lifeFormAnalyzer = 1;
            if (item instanceof IRadar) radar = 1;
            if (item instanceof ITallyCounter) tallyCounter = 1;
            if (item instanceof IDPSMeter) dpsMeter = 1;
            if (item instanceof IStopwatch) stopwatch = 1;
            if (item instanceof ICompass) compass = 1;
            if (item instanceof IDepthMeter) depthMeter = 1;
            if (item instanceof MechanicalLens) mechanicalLens = 1;
        }
        NetworkHandler.CHANNEL.send(
            PacketDistributor.PLAYER.with(() -> serverPlayer),
            new InfoCurioCheckPacketS2C(new byte[]{
                watch, weatherRadio, sextant, fishermansPocketGuide, metalDetector, lifeFormAnalyzer,
                radar, tallyCounter, dpsMeter, stopwatch, compass, depthMeter, mechanicalLens
            })
        );
    }
}
