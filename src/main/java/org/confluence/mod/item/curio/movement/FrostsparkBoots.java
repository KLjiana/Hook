package org.confluence.mod.item.curio.movement;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.block.natural.ThinIceBlock;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class FrostsparkBoots extends LightningBoots implements ThinIceBlock.IceSafe {
    public static final UUID SPEED_UUID = UUID.fromString("FF5835B7-FA33-A02D-A91F-E0043403CE69");
    private static final AttributeModifier SPEED_MODIFIER = new AttributeModifier(SPEED_UUID, "Frostspark Boots", 0.08, AttributeModifier.Operation.MULTIPLY_TOTAL);

    public FrostsparkBoots() {
        super(ModRarity.LIME);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        LivingEntity living = slotContext.entity();
        if (living != null && living.level().getBlockState(living.getOnPos().below()).is(BlockTags.ICE)) {
            return ImmutableMultimap.of(
                Attributes.MOVEMENT_SPEED, SPEED_MODIFIER,
                Attributes.MOVEMENT_SPEED, new AttributeModifier(BaseSpeedBoots.SPEED_UUID, "Speed Boots", stack.getOrCreateTag().getInt("speed") * 0.01, AttributeModifier.Operation.MULTIPLY_TOTAL),
                Attributes.MOVEMENT_SPEED, IceSkates.MODIFIER
            );
        }
        return super.getAttributeModifiers(slotContext, uuid, stack);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.confluence.frostspark_boots.tooltip"));
    }
}
