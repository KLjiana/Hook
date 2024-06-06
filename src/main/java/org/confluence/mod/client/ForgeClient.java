package org.confluence.mod.client;

import com.mojang.datafixers.util.Either;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.Confluence;
import org.confluence.mod.capability.prefix.PrefixProvider;
import org.confluence.mod.capability.prefix.PrefixType;
import org.confluence.mod.client.color.AnimateColor;
import org.confluence.mod.client.handler.GunShootingHandler;
import org.confluence.mod.client.handler.HookThrowingHandler;
import org.confluence.mod.client.handler.InformationHandler;
import org.confluence.mod.client.handler.PlayerJumpHandler;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.effect.beneficial.GravitationEffect;
import org.confluence.mod.effect.harmful.CursedEffect;
import org.confluence.mod.effect.harmful.StonedEffect;
import org.confluence.mod.event.ShimmerEvents;
import org.confluence.mod.item.curio.combat.IAutoAttack;
import org.confluence.mod.util.ModUtils;

import java.util.List;
import java.util.Optional;

import static net.minecraft.world.item.ItemStack.ATTRIBUTE_MODIFIER_FORMAT;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class ForgeClient {
    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer localPlayer = minecraft.player;
        if (event.phase == TickEvent.Phase.START) return;
        GravitationEffect.tick(localPlayer);
        if (localPlayer == null) return;
        InformationHandler.handle(localPlayer);
        IAutoAttack.apply(minecraft, localPlayer);
        HookThrowingHandler.handle(localPlayer);

        AnimateColor.doUpdateExpertColor();
        AnimateColor.doUpdateMasterColor();
        ShimmerEvents.doUpdateTorchColor();
    }

    @SubscribeEvent
    public static void movementInputUpdate(MovementInputUpdateEvent event) {
        LocalPlayer localPlayer = (LocalPlayer) event.getEntity();
        boolean jumping = event.getInput().jumping;
        if (GravitationEffect.isHasGlobe() || localPlayer.hasEffect(ModEffects.GRAVITATION.get())) {
            GravitationEffect.handle(localPlayer, jumping);
        } else {
            GravitationEffect.expire();
            PlayerJumpHandler.handle(localPlayer, jumping);
        }
    }

    @SubscribeEvent
    public static void gatherComponents(RenderTooltipEvent.GatherComponents event) {
        Optional<FormattedText> displayName = event.getTooltipElements().get(0).left();
        if (displayName.isEmpty()) return;

        if (displayName.get() instanceof Component component) {
            PrefixProvider.getPrefix(event.getItemStack()).ifPresent(itemPrefix ->
                event.getTooltipElements().set(0, Either.left(Component.translatable("prefix.confluence." + itemPrefix.name)
                    .withStyle(itemPrefix.tier >= 0 ? ChatFormatting.GREEN : ChatFormatting.RED)
                    .append(" ").append(component)
                )));
        }
    }

    @SubscribeEvent
    public static void leftClick(InputEvent.InteractionKeyMappingTriggered event) {
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        if (localPlayer == null) return;
        GunShootingHandler.handle(event, localPlayer);
        CursedEffect.onLeftClick(localPlayer, event);
        StonedEffect.onLeftClick(localPlayer, event);
    }

    @SubscribeEvent
    public static void itemToolTip(ItemTooltipEvent event) {
        ItemStack itemStack = event.getItemStack();
        if (itemStack.getTag() == null) return;
        List<Component> tooltip = event.getToolTip();

        PrefixProvider.getPrefix(itemStack).ifPresent(itemPrefix -> {
            if (itemPrefix.type != PrefixType.UNIVERSAL && itemPrefix.type != PrefixType.MELEE) {
                if (itemPrefix.attackDamage != 0.0) {
                    tooltip.add(ModUtils.getModifierTooltip(itemPrefix.attackDamage, "attack_damage"));
                }
                if (itemPrefix.attackSpeed != 0.0) {
                    tooltip.add(ModUtils.getModifierTooltip(itemPrefix.attackSpeed, "attack_speed"));
                }
                if (itemPrefix.knockBack != 0.0) {
                    tooltip.add(ModUtils.getModifierTooltip(itemPrefix.knockBack, "knock_back"));
                }
            }
            if (itemPrefix.criticalChance != 0.0) {
                tooltip.add(ModUtils.getModifierTooltip(itemPrefix.criticalChance, "critical_chance"));
            }
            if (itemPrefix.type == PrefixType.RANGED) {
                if (itemPrefix.velocity != 0.0) {
                    tooltip.add(ModUtils.getModifierTooltip(itemPrefix.velocity, "velocity"));
                }
            } else if (itemPrefix.type == PrefixType.MAGIC_AND_SUMMING) {
                if (itemPrefix.manaCost != 0.0) {
                    boolean b = itemPrefix.manaCost > 0.0;
                    tooltip.add(Component.translatable(
                        "prefix.confluence.tooltip." + (b ? "plus" : "take"),
                        ATTRIBUTE_MODIFIER_FORMAT.format(itemPrefix.manaCost * (b ? 100.0 : -100.0)),
                        Component.translatable("prefix.confluence.tooltip.mana_cost")
                    ).withStyle(b ? ChatFormatting.RED : ChatFormatting.BLUE));
                }
            } else if (itemPrefix.type == PrefixType.CURIO) {
                if (itemPrefix.armor > 0) {
                    tooltip.add(Component.translatable(
                        "prefix.confluence.tooltip.add",
                        itemPrefix.armor,
                        Component.translatable("prefix.confluence.tooltip.armor")
                    ).withStyle(ChatFormatting.BLUE));
                }
                if (itemPrefix.additionalMana > 0) {
                    tooltip.add(Component.translatable(
                        "prefix.confluence.tooltip.add",
                        itemPrefix.additionalMana,
                        Component.translatable("prefix.confluence.tooltip.additional_mana")
                    ).withStyle(ChatFormatting.BLUE));
                }
                if (itemPrefix.movementSpeed > 0.0) {
                    tooltip.add(Component.translatable(
                        "prefix.confluence.tooltip.plus",
                        ATTRIBUTE_MODIFIER_FORMAT.format(itemPrefix.movementSpeed * 100.0),
                        Component.translatable("prefix.confluence.tooltip.movement_speed")
                    ).withStyle(ChatFormatting.BLUE));
                }
            }
        });
    }

    @SubscribeEvent
    public static void cameraSetup(ViewportEvent.ComputeCameraAngles event) {
        if (GravitationEffect.isShouldRot()) event.setRoll(180.0F);
    }
}
