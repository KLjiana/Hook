package org.confluence.mod.effect.beneficial;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.effect.ModEffects;

public class ArcheryEffect extends MobEffect {
    public ArcheryEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xBBAA00);
    }

    public static float apply(LivingEntity living, DamageSource damageSource, float amount) {
        if (damageSource.is(DamageTypes.ARROW) && living.hasEffect(ModEffects.ARCHERY.get())) {
            return amount * 1.1F;
        }
        return amount;
    }
}
