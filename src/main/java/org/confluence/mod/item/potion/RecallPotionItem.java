package org.confluence.mod.item.potion;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelData;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;

public class RecallPotionItem extends AbstractPotionItem {
    public RecallPotionItem() {
        super(new Properties().rarity(ModRarity.BLUE));
    }

    @Override
    public int getUseDuration(@NotNull ItemStack itemStack) {
        return 10;
    }

    @Override
    protected void apply(ItemStack itemStack, Level level, LivingEntity living) {
        if (living instanceof ServerPlayer serverPlayer) {
            BlockPos pos = serverPlayer.getRespawnPosition();
            if (pos == null) {
                ServerLevel serverLevel = serverPlayer.server.getLevel(Level.OVERWORLD);
                if (serverLevel == null) serverLevel = (ServerLevel) level;
                LevelData data = serverLevel.getLevelData();
                serverPlayer.teleportTo(data.getXSpawn(), data.getYSpawn(), data.getXSpawn());
            } else {
                serverPlayer.teleportTo(pos.getX(), pos.getY(), pos.getZ());
            }
            serverPlayer.getCooldowns().addCooldown(this, 10);
        }
    }
}
