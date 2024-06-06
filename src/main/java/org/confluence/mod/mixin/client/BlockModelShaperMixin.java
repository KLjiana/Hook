package org.confluence.mod.mixin.client;

import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.world.level.block.state.BlockState;
import org.confluence.mod.block.functional.EchoBlock;
import org.confluence.mod.block.functional.IMechanical;
import org.confluence.mod.block.functional.StateProperties;
import org.confluence.mod.client.handler.ClientPacketHandler;
import org.confluence.mod.client.handler.InformationHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(BlockModelShaper.class)
public abstract class BlockModelShaperMixin {
    @Shadow
    private Map<BlockState, BakedModel> modelByStateCache;

    @Shadow
    @Final
    private ModelManager modelManager;

    @Inject(method = "getBlockModel", at = @At("HEAD"), cancellable = true)
    private void ifEchoBlock(BlockState blockState, CallbackInfoReturnable<BakedModel> cir) {
        if (blockState.getBlock() instanceof EchoBlock) {
            BlockState visible = blockState.setValue(StateProperties.VISIBLE, ClientPacketHandler.isEchoBlockVisible());
            cir.setReturnValue(modelByStateCache.getOrDefault(visible, modelManager.getMissingModel()));
        } else if (blockState.getBlock() instanceof IMechanical) {
            BlockState visible = blockState.setValue(StateProperties.VISIBLE, InformationHandler.isMechanicalBlockVisible());
            cir.setReturnValue(modelByStateCache.getOrDefault(visible, modelManager.getMissingModel()));
        }
    }
}
