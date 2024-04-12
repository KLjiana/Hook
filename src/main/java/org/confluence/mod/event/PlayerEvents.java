package org.confluence.mod.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.Confluence;
import org.confluence.mod.capability.ability.PlayerAbilityProvider;
import org.confluence.mod.capability.mana.ManaProvider;
import org.confluence.mod.effect.HarmfulEffect.CursedEffect;
import org.confluence.mod.effect.HarmfulEffect.SilencedEffect;
import org.confluence.mod.item.common.LifeCrystal;
import org.confluence.mod.item.curio.combat.IAutoAttack;
import org.confluence.mod.item.curio.combat.ICriticalHit;
import org.confluence.mod.item.curio.combat.IFireAttack;
import org.confluence.mod.item.curio.movement.IMayFly;
import org.confluence.mod.item.curio.movement.IMultiJump;
import org.confluence.mod.network.s2c.InfoCurioCheckPacketS2C;
import org.confluence.mod.util.PlayerUtils;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerEvents {
    @SubscribeEvent
    public static void playerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            PlayerUtils.syncMana2Client(serverPlayer);
            PlayerUtils.syncSavedData(serverPlayer);
            InfoCurioCheckPacketS2C.send(serverPlayer);
        }
    }

    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.CLIENT || event.phase == TickEvent.Phase.START) return;

        ServerPlayer serverPlayer = (ServerPlayer) event.player;
        PlayerUtils.regenerateMana(serverPlayer);
    }

    @SubscribeEvent
    public static void playerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return;
        Player oldPlayer = event.getOriginal();
        Player neoPlayer = event.getEntity();
        oldPlayer.revive();

        oldPlayer.getCapability(ManaProvider.CAPABILITY).ifPresent(old -> neoPlayer.getCapability(ManaProvider.CAPABILITY).ifPresent(neo -> neo.copyFrom(old)));
        oldPlayer.getCapability(PlayerAbilityProvider.CAPABILITY).ifPresent(old -> neoPlayer.getCapability(PlayerAbilityProvider.CAPABILITY)
            .ifPresent(neo -> {
                neo.copyFrom(old);
                LifeCrystal.applyModifier(neoPlayer, neo);
            }));

        if (neoPlayer instanceof ServerPlayer serverPlayer) {
            IMultiJump.sendMsg(serverPlayer);
            IMayFly.sendMsg(serverPlayer);
            IAutoAttack.sendMsg(serverPlayer);
        }
    }

    @SubscribeEvent
    public static void attackEntity(AttackEntityEvent event) {
        IFireAttack.apply(event.getEntity(), event.getTarget());
    }

    @SubscribeEvent
    public static void criticalHit(CriticalHitEvent event) {
        ICriticalHit.apply(event);
    }

    @SubscribeEvent
    public static void rightClickItem(PlayerInteractEvent.RightClickItem event) {
        SilencedEffect.apply(event.getEntity(), event);
        CursedEffect.onRightClick(event.getEntity(),event);
    }
}
