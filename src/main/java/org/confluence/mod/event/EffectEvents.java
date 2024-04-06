package org.confluence.mod.event;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.Confluence;
import org.confluence.mod.effect.BeneficialEffect.IronSkinEffect;
import org.confluence.mod.item.curio.combat.EffectInvulnerable;
import org.confluence.mod.item.magic.IMagicAttack;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EffectEvents {
    @SubscribeEvent
    public static void effectApplicable(MobEffectEvent.Applicable event) {
        MobEffect mobEffect = event.getEffectInstance().getEffect();
        LivingEntity living = event.getEntity();

        if (EffectInvulnerable.apply(mobEffect, living)) {
            event.setResult(Event.Result.DENY);
        }
    }

    @SubscribeEvent
    public static void effectAdded(MobEffectEvent.Added event) {
        MobEffect mobEffect = event.getEffectInstance().getEffect();
        AttributeMap attributeMap = event.getEntity().getAttributes();

        IronSkinEffect.onAdd(mobEffect, attributeMap);
    }

    @SubscribeEvent
    public static void effectExpired(MobEffectEvent.Expired event) {
        MobEffectInstance mobEffectInstance = event.getEffectInstance();
        if (mobEffectInstance == null) return;
        MobEffect mobEffect = mobEffectInstance.getEffect();
        AttributeMap attributeMap = event.getEntity().getAttributes();

        IronSkinEffect.onRemove(mobEffect, attributeMap);
    }

    @SubscribeEvent
    public static void effectRemove(MobEffectEvent.Remove event) {
        MobEffectInstance mobEffectInstance = event.getEffectInstance();
        if (mobEffectInstance == null) return;
        MobEffect mobEffect = mobEffectInstance.getEffect();
        AttributeMap attributeMap = event.getEntity().getAttributes();

        IronSkinEffect.onRemove(mobEffect, attributeMap);
    }

    @SubscribeEvent
    public void noHealthBoost(LivingHealEvent event) {
        if (event.getAmount() > 0) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void noUseMagicItem(PlayerInteractEvent.RightClickItem event) {
        if (event.getHand() == InteractionHand.MAIN_HAND && event.getItemStack().getItem() instanceof IMagicAttack) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void noUseItem(PlayerInteractEvent event) {
        if (event.getEntity().isUsingItem()) {
            event.setCanceled(true);
        }
    }
}
