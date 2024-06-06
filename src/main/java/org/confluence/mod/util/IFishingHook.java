package org.confluence.mod.util;

import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.fishing.Baits;
import org.jetbrains.annotations.Nullable;

public interface IFishingHook {
    void c$setIsLavaHook();

    boolean c$isLavaHook();

    @Nullable ItemStack c$getBait();

    default float c$getBonus() {
        ItemStack itemStack = c$getBait();
        if (itemStack != null && itemStack.getItem() instanceof Baits.IBait iBait) {
            return iBait.getBaitBonus();
        }
        return 0.0F;
    }
}
