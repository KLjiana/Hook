package org.confluence.mod.item.common;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;

public class MasterTestItem extends Item implements ModRarity.Master {
    public MasterTestItem() {
        super(new Properties().rarity(ModRarity.MASTER));
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack itemStack) {
        return withColor(getDescriptionId(itemStack));
    }
}
