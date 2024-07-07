package org.hook.mod.item.hook;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.hook.mod.entity.hook.AbstractHookEntity;
import org.hook.mod.entity.hook.BaseHookEntity;

public class GrapplingHookItem extends AbstractHookItem {
    public static final Rarity WHITE = Rarity.create("white", style -> style.withColor(0xFFFFFF));
    public GrapplingHookItem() {
        super(WHITE);
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
    public float getHookVelocity() {
        return 4F;
    }

    @Override
    public AbstractHookEntity getHook(ItemStack itemStack, AbstractHookItem item, Player player, Level level) {
        return new BaseHookEntity(item, player, level, BaseHookEntity.Variant.DIAMOND);
    }

    @Override
    public HookType getHookType() {
        return HookType.SINGLE;
    }
}
