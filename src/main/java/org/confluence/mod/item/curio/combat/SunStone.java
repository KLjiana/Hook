package org.confluence.mod.item.curio.combat;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class SunStone extends BaseCurioItem {
    public static final UUID ATTACK_SPEED_UUID = UUID.fromString("6B2CFC65-3C84-7C1F-69D8-7B37556578E0");
    public static final UUID DAMAGE_UUID = UUID.fromString("56A08AD3-ADA1-F838-E09C-28B08935F5C2");
    public static final UUID ARMOR_UUID = UUID.fromString("7E929677-A019-1C19-1A2C-36A07268A66B");

    private static final ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTE = ImmutableMultimap.of(
        Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_UUID, "Sun Stone", 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL),
        Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE_UUID, "Sun Stone", 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL),
        Attributes.ARMOR, new AttributeModifier(ARMOR_UUID, "Sun Stone", 4, AttributeModifier.Operation.ADDITION)
    );

    public SunStone() {
        super(ModRarity.LIME);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        LivingEntity living = slotContext.entity();
        if (living == null) return EMPTY_ATTRIBUTE;
        return living.level().getDayTime() % 24000 < 12000 ? ATTRIBUTE : EMPTY_ATTRIBUTE;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity living = slotContext.entity();
        if (living.level().getGameTime() % 24000 > 12000) return;
        ModEffects.heal(living, 2);
        living.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 1, 0, false, false, false));
    }
}
