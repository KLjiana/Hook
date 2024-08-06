package org.hook.mod.item.hook;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.hook.mod.entity.hook.AbstractHookEntity;
import org.hook.mod.entity.hook.BaseHookEntity;

public class GrapplingHookItem extends AbstractHookItem {
    public GrapplingHookItem() {
        super(Rarity.create("white", style -> style.withColor(0xFFFFFF)));
    }

    @Override
    public int getHookAmount() {
        return 1;
    }

    @Override
    public float getHookRange() {
        return 40F;
    }

    @Override
    public AbstractHookEntity getHook(ItemStack itemStack, AbstractHookItem item, Player player, Level level) {
        return new BaseHookEntity(item, player, level, BaseHookEntity.Variant.GRAPPLING);
    }
}
