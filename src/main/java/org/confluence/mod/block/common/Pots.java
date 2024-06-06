package org.confluence.mod.block.common;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.command.ConfluenceData;
import org.confluence.mod.datagen.limit.CustomModel;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.potion.TerraPotions;
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.misc.ModTags;
import org.confluence.mod.util.EnumRegister;
import org.confluence.mod.util.ModUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static net.minecraft.world.level.block.Block.box;
import static org.confluence.mod.item.potion.TerraPotions.*;

public enum Pots implements EnumRegister<Pots.BasePotsBlock> {
    FOREST_POTS("forest_pots", 1.0F, 0.002F),
    TUNDRA_POTS("tundra_pots", 1.25F, 0.002167F),
    SPIDER_NEST_POTS("spider_nest_pots", 3.5F, 0.003676F),
    UNDERGROUND_DESERT_POTS("underground_desert_pots", 1.25F, 0.002169F, Shapes.or(box(4, 0, 4, 12, 1, 12), box(4, 10, 4, 12, 11, 12), box(4, 17, 4, 12, 19, 12), box(5, 11, 5, 11, 17, 11), box(3, 1, 3, 13, 3, 13), box(3, 8, 3, 13, 10, 13), box(2, 3, 2, 14, 8, 14))),
    JUNGLE_POTS("jungle_pots", 1.75F, 0.0025F, Shapes.or(box(3, 1, 3, 13, 13, 13), box(4, 14, 4, 12, 15, 12), box(5, 13, 5, 11, 14, 11), box(4, 0, 4, 12, 1, 12))),
    MARBLE_CAVE_POTS("marble_cave_pots", 2.0F, 0.002667F, Shapes.or((box(4, 2, 4, 12, 3, 12)), box(5, 1, 5, 11, 2, 11), box(5, 12, 5, 11, 13, 11), box(4, 11, 4, 12, 12, 12), box(4, 13, 4, 12, 14, 12), box(4, 0, 4, 12, 1, 12), box(3, 3, 3, 13, 4, 13), box(2, 4, 2, 14, 10, 14), box(3, 10, 3, 13, 11, 13))),
    ANOTHER_CRIMSON_POTS("another_crimson_pots", 1.6F, 0.00274F, Shapes.or(box(4, 0, 4, 12, 1, 12), box(5, 11, 5, 11, 12, 11), box(4, 3, 4, 12, 11, 12), box(5, 1, 5, 11, 3, 11))),
    PYRAMID_POTS("pyramid_pots", 10.0F, 0.008F, Shapes.or(box(2, 2, 2, 14, 13, 14), box(3, 1, 3, 13, 2, 13), box(2, 0, 2, 14, 1, 14), box(3, 13, 3, 13, 15, 13), box(2, 15, 2, 14, 17, 14))),
    CORRUPTION_POTS("corruption_pots", 1.6F, 0.00274F, Shapes.or(box(3, 0, 3, 13, 1, 13), box(4, 1, 4, 12, 3, 12), box(3, 3, 3, 13, 12, 13), box(2, 12, 2, 14, 14, 14))),
    DUNGEON_POTS("dungeon_pots", 1.9F, 0.002604F, Shapes.or(box(3, 0, 3, 13, 6, 13), box(3, 15, 3, 13, 16, 13), box(2, 6, 2, 14, 15, 14))),
    UNDERWORLD_POTS("underworld_pots", 2.1F, 0.00274F),
    LIHZAHRD_POTS("lihzahrd_pots", 4.0F, 0.004F, Shapes.or(box(3, 0, 3, 13, 1, 13), box(4, 1, 4, 12, 3, 12), box(3, 3, 3, 13, 12, 13), box(2, 12, 2, 14, 14, 14)));

    private final RegistryObject<BasePotsBlock> value;

    Pots(String id, float moneyHoleChance, float moneyRatio, VoxelShape voxelShape) {
        this.value = ModBlocks.registerWithItem(id, () -> new BasePotsBlock(moneyHoleChance, moneyRatio, voxelShape));
    }

    Pots(String id, float moneyHoleChance, float moneyRatio) {
        this.value = ModBlocks.registerWithItem(id, () -> new BasePotsBlock(moneyHoleChance, moneyRatio, Shapes.or(box(3, 1, 3, 13, 10, 13), box(4, 11, 4, 12, 12, 12), box(5, 10, 5, 11, 11, 11), box(4, 0, 4, 12, 1, 12))));
    }

    @Override
    public RegistryObject<BasePotsBlock> getValue() {
        return value;
    }

    public static void init() {}

    public static class BasePotsBlock extends HorizontalDirectionalBlock implements CustomModel {
        private final VoxelShape voxelShape;
        private final float moneyHoleChance;
        private final float moneyRatio;

        public BasePotsBlock(float moneyHoleChance, float moneyRatio, VoxelShape voxelShape) {
            super(Properties.of().sound(SoundType.DECORATED_POT).strength(1.0F, 10.0F).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
            this.voxelShape = voxelShape;
            this.moneyHoleChance = moneyHoleChance;
            this.moneyRatio = moneyRatio;
            registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            builder.add(FACING);
        }

        @Override
        @Nullable
        public BlockState getStateForPlacement(BlockPlaceContext placeContext) {
            return defaultBlockState().setValue(FACING, placeContext.getHorizontalDirection().getOpposite());
        }

        @Override
        public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
            return voxelShape;
        }

        @Override
        public void playerDestroy(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull BlockPos pPos, @NotNull BlockState pState, @Nullable BlockEntity pBlockEntity, @NotNull ItemStack pTool) {
            pPlayer.awardStat(Stats.BLOCK_MINED.get(this));
            pPlayer.causeFoodExhaustion(0.005F);
            dropSequence(pLevel, pPos);
        }

        @Override
        public void wasExploded(@NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Explosion pExplosion) {
            dropSequence(pLevel, pPos);
        }

        @Override
        public void onProjectileHit(@NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockHitResult pHit, @NotNull Projectile pProjectile) {
            BlockPos blockPos = pHit.getBlockPos();
            Entity entity = pProjectile.getOwner();
            if (pLevel.destroyBlock(blockPos, true, entity)) {
                if (entity instanceof Player player) {
                    player.awardStat(Stats.BLOCK_MINED.get(this));
                }
                dropSequence(pLevel, blockPos);
            }
        }

        // todo 专家模式掉落
        private void dropSequence(Level level, BlockPos blockPos) {
            if (level.isClientSide) return;
            Vec3 center = blockPos.getCenter();
            if (summonHole(level, center)) return;
            // 如果罐子位于天然地牢墙前方且低于地表地层，有 1/35 (2.86%) 的几率掉落金钥匙。若掉落，则流程结束。
            // 如果玩家正在游玩秘密世界种子 for the worthy，有 1/4 (25%) 的几率掉落一个点燃的炸弹。若掉落，则流程结束。
            if (dropPotion(level, center)) return;
            if (dropWormhole(level, center)) return;
            boolean flag = switch (level.random.nextInt(7)) {
                case 0 -> dropHeart(level, blockPos, center);
                case 1 -> dropTorch(level, blockPos, center);
                case 2 -> dropAmmo(level, center);
                case 3 -> dropHeal(level, center);
                case 4 -> dropBomb(level, center);
                case 5 -> dropRope(level, center);
                case 6 -> dropMoney(level, center);
                default -> false;
            };
            if (!flag) dropMoney(level, center);
        }

        private boolean summonHole(Level level, Vec3 center) {
            if (level.random.nextFloat() < moneyHoleChance) {
                // 金钱洞
                return true;
            }
            return false;
        }

        private boolean dropPotion(Level level, Vec3 center) {
            // 专家模式 0.0444F
            if (level.random.nextFloat() < 0.0222F) {
                double y = center.y;
                Item item = null;
                if (level.dimension() == Confluence.HELL) {
                    item = switch (level.random.nextInt(14)) {
                        // 洞穴探险
                        case 1 -> FEATHERFALL_POTION.get();
                        case 2 -> MANA_REGENERATION_POTION.get();
                        case 3 -> OBSIDIAN_SKIN_POTION.get();
                        case 4 -> MAGIC_POWER_POTION.get();
                        case 5 -> INVISIBILITY_POTION.get();
                        // 狩猎
                        case 7 -> GRAVITATION_POTION.get();
                        case 8 -> THORNS_POTION.get();
                        case 9 -> WATER_WALKING_POTION.get();
                        // 战斗
                        case 11 -> HEART_REACH_POTION.get();
                        case 12 -> TITAN_POTION.get();
                        default -> null;
                    };
                    if (level.random.nextFloat() < 0.2F) {
                        // 返回
                    }
                } else if (y <= 0.0) {
                    item = switch (level.random.nextInt(15)) {
                        // 洞穴探险
                        case 1 -> FEATHERFALL_POTION.get();
                        case 2 -> NIGHT_OWL_POTION.get();
                        case 3, 4 -> WATER_WALKING_POTION.get();
                        case 5 -> ARCHERY_POTION.get();
                        case 6 -> GRAVITATION_POTION.get();
                        // 狩猎
                        case 8 -> INVISIBILITY_POTION.get();
                        case 9 -> THORNS_POTION.get();
                        case 10 -> MINING_POTION.get();
                        case 11 -> HEART_REACH_POTION.get();
                        // 脚蹼
                        // 危险感
                        default -> RECALL_POTION.get();
                    };
                } else if (y <= 63.0) {
                    item = switch (level.random.nextInt(11)) {
                        case 0 -> REGENERATION_POTION.get();
                        // 光芒
                        case 2 -> SWIFTNESS_POTION.get();
                        case 3 -> ARCHERY_POTION.get();
                        case 4 -> GILLS_POTION.get();
                        // 狩猎
                        case 6 -> MINING_POTION.get();
                        // 危险感
                        default -> RECALL_POTION.get();
                    };
                } else if (y <= 240.0) {
                    item = switch (level.random.nextInt(10)) {
                        case 0 -> IRON_SKIN_POTION.get();
                        // 光芒
                        case 2 -> NIGHT_OWL_POTION.get();
                        case 3 -> SWIFTNESS_POTION.get();
                        case 4 -> MINING_POTION.get();
                        // 镇静
                        case 6 -> BUILDER_POTION.get();
                        default -> RECALL_POTION.get();
                    };
                }
                if (item != null) {
                    ModUtils.createItemEntity(item, 1, center.x, y, center.z, level);
                    return true;
                }
            }
            return false;
        }

        private boolean dropWormhole(Level level, Vec3 center) {
            if (level.players().size() > 1 && level.random.nextFloat() < 0.0333F) {
                // 生成虫洞药水
                return true;
            }
            return false;
        }

        private boolean dropHeart(Level level, BlockPos blockPos, Vec3 center) {
            Optional<? extends Player> optional = level.players().stream()
                .min((a, b) -> (int) (a.distanceToSqr(center) - b.distanceToSqr(center)));
            if (optional.isPresent()) {
                Player player = optional.get();
                if (player.getHealth() < player.getMaxHealth()) {
                    int amount = 1;
                    if (level.random.nextBoolean()) amount++;
                    /* 在专家模式中，有 1/8 的几率掉落 1 个心，3/8 的几率掉落 2 个心，3/8 的几率掉落 3 个心，以及 1/8 的几率掉落 4 个心。*/
                    ModUtils.createItemEntity(ModItems.HEART.get(), amount, center.x, center.y, center.z, level);
                } else if (player.getInventory().hasAnyMatching(itemStack -> itemStack.getCount() < 20 && itemStack.is(ModTags.Items.TORCH))) {
                    return dropTorch(level, blockPos, center);
                } else {
                    return dropMoney(level, center);
                }
            }
            return false;
        }

        private boolean dropTorch(Level level, BlockPos blockPos, Vec3 center) {
            boolean tundra = this == TUNDRA_POTS.get();
            int amount = tundra ? level.random.nextInt(2, 7) : level.random.nextInt(4, 13);
            Item item;
            if (level.getFluidState(blockPos).is(FluidTags.WATER)) {
                if (tundra) {
                    item = ModItems.STICKY_GLOW_STICK.get();
                } else {
                    item = ModItems.GLOW_STICK.get();
                }
            } else {
                if (tundra) {
                    item = Torches.ICE_TORCH.item.get();
                } else if (this == ANOTHER_CRIMSON_POTS.get()) {
                    item = Torches.CRIMSON_TORCH.item.get();
                } else if (this == JUNGLE_POTS.get()) {
                    item = Torches.JUNGLE_TORCH.item.get();
                } else if (this == CORRUPTION_POTS.get()) {
                    item = Torches.CORRUPT_TORCH.item.get();
                } else if (this == UNDERGROUND_DESERT_POTS.get()) {
                    item = Torches.DESERT_TORCH.item.get();
                } else {
                    item = Items.TORCH;
                }
            }
            ModUtils.createItemEntity(item, amount, center.x, center.y, center.z, level);
            return true;
        }

        private boolean dropAmmo(Level level, Vec3 center) {
            int amount = level.random.nextInt(10, 21);
            Item item = Items.ARROW;
            boolean hardCore = ConfluenceData.get((ServerLevel) level).isHardCore();
            if (level.random.nextBoolean()) {
                item = hardCore ? ModItems.GRENADE.get() : ModItems.SHURIKEN.get();
            } else if (level.dimension() == Confluence.HELL) {
                // 如果位于地狱，它会被狱炎箭替代
            } else if (hardCore) {
                // 被邪箭或银子弹（在包含银的世界中）/ 钨子弹（在包含钨的世界中）（箭或子弹的几率各为 50%）
            }
            ModUtils.createItemEntity(item, amount, center.x, center.y, center.z, level);
            return true;
        }

        private boolean dropHeal(Level level, Vec3 center) {
            Item item;
            if (level.dimension() == Confluence.HELL || ConfluenceData.get((ServerLevel) level).isHardCore()) {
                item = TerraPotions.HEALING_POTION.get();
            } else {
                item = TerraPotions.LESSER_HEALING_POTION.get();
            }
            // 在专家模式，有 1/3 的几率额外掉落 1 个
            ModUtils.createItemEntity(item, 1, center.x, center.y, center.z, level);
            return true;
        }

        private boolean dropBomb(Level level, Vec3 center) {
            Item item;
            if (this == UNDERGROUND_DESERT_POTS.get()) {
                item = ModItems.SCARAB_BOMB.get();
            } else if (level.dimension() == Level.OVERWORLD) {
                item = ModItems.BOMB.get();
            } else {
                return dropRope(level, center);
            }
            // 专家模式 1-7 个
            ModUtils.createItemEntity(item, level.random.nextInt(1, 5), center.x, center.y, center.z, level);
            return true;
        }

        private boolean dropRope(Level level, Vec3 center) {
            if (level.dimension() == Confluence.HELL || ConfluenceData.get((ServerLevel) level).isHardCore()) {
                return dropMoney(level, center);
            } else {
                ModUtils.createItemEntity(ModBlocks.ROPE.get().asItem(), level.random.nextInt(20, 41), center.x, center.y, center.z, level);
                return true;
            }
        }

        private boolean dropMoney(Level level, Vec3 center) {
            if (!ModConfigs.dropsMoney) return false;
            float random = level.random.nextFloat();
            float ratio = 1.0F;
            double y = center.y;
            if (y <= 0.0) {
                ratio = 1.25F;
            } else if (y <= 63.0) {
                ratio = 0.75F;
            } else if (y <= 240.0) {
                ratio = 0.5F;
            } else if (random < 0.05F) {
                ratio = ModUtils.nextFloat(level.random, 1.5F, 2.0F);
            } else if (random < 0.0625F) {
                ratio = ModUtils.nextFloat(level.random, 1.4F, 1.8F);
            } else if (random < 0.0833F) {
                ratio = ModUtils.nextFloat(level.random, 1.2F, 1.4F);
            } else if (random < 0.125F) {
                ratio = ModUtils.nextFloat(level.random, 1.1F, 1.2F);
            } else if (random < 0.25F) {
                ratio = ModUtils.nextFloat(level.random, 1.05F, 1.1F);
            }
            // 专家模式增益
            // 击败增益
            ratio *= moneyRatio;
            int amount = (int) Math.ceil(level.random.nextInt(8, 34) * ratio);
            ModUtils.dropMoney(amount, center.x, y, center.z, level);
            return true;
        }
    }
}
