package org.confluence.mod.effect.harmful;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.event.InputEvent;
import org.confluence.mod.effect.ModEffects;

import java.util.function.Consumer;

public class CursedEffect extends MobEffect {   //诅咒 禁止玩家使用物品
    public CursedEffect() {
        super(MobEffectCategory.HARMFUL, 0x4F4F4F);
    }

    public static void onRightClick(LivingEntity entity, Consumer<Boolean> consumer) {
        if (!entity.isSpectator() && !entity.getUseItem().isEmpty() && entity.hasEffect(ModEffects.CURSED.get())) {
            consumer.accept(true);
        }
    }

    public static void onLeftClick(LocalPlayer player, InputEvent.InteractionKeyMappingTriggered event) {
        if (player.hasEffect(ModEffects.CURSED.get()) && (event.isUseItem() || event.isAttack() || event.isPickBlock())) {
            event.setCanceled(true);
        }
    }
}
