package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.curio.ILavaImmune;
import org.confluence.mod.item.curio.combat.IFireImmune;
import org.confluence.mod.item.curio.combat.ILavaHurtReduce;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LavaWaders extends BaseCurioItem implements IFireImmune, ILavaImmune, ILavaHurtReduce, IFluidWalk {
    public LavaWaders() {
        super(ModRarity.LIME);
    }

    @Override
    public boolean canStandOn(FluidState fluidState) {
        return IFluidWalk.super.canStandOn(fluidState) || fluidState.is(Fluids.LAVA);
    }
    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(IFluidWalk.ALL_FLUID);
        list.add(Component.translatable("item.confluence.lava_waders.tooltip2"));
    }
}
