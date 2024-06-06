package org.confluence.mod.block.natural.spreadable;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.NotNull;

public class SpreadingBlock extends Block implements ISpreadable {
    private final Type type;

    public SpreadingBlock(Type type, Properties properties) {
        super(properties.randomTicks());
        this.type = type;
        registerDefaultState(stateDefinition.any().setValue(STILL_ALIVE, true));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STILL_ALIVE);
    }

    @Override
    public Type getType() {
        return type;
    }

    public void randomTick(@NotNull BlockState blockState, @NotNull ServerLevel serverLevel, @NotNull BlockPos blockPos, @NotNull RandomSource randomSource) {
        if (!serverLevel.isAreaLoaded(blockPos, 3)) return;
        spread(blockState, serverLevel, blockPos, randomSource);
    }
}
