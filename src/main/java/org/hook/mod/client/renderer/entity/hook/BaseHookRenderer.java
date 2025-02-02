package org.hook.mod.client.renderer.entity.hook;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.hook.mod.entity.hook.BaseHookEntity;
import org.jetbrains.annotations.NotNull;

import static org.hook.mod.Hook.MODID;

public class BaseHookRenderer extends AbstractHookRenderer<BaseHookEntity> {
    private static final ResourceLocation[] TEXTURE = new ResourceLocation[]{
        new ResourceLocation(MODID, "textures/entity/hook/grappling_hook.png"),
        new ResourceLocation(MODID, "textures/entity/hook/amethyst_hook.png"),
        new ResourceLocation(MODID, "textures/entity/hook/topaz_hook.png"),
        new ResourceLocation(MODID, "textures/entity/hook/sapphire_hook.png"),
        new ResourceLocation(MODID, "textures/entity/hook/emerald_hook.png"),
        new ResourceLocation(MODID, "textures/entity/hook/ruby_hook.png"),
        new ResourceLocation(MODID, "textures/entity/hook/amber_hook.png"),
        new ResourceLocation(MODID, "textures/entity/hook/diamond_hook.png")
    };

    public BaseHookRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BaseHookEntity pEntity) {
        return TEXTURE[pEntity.getVariant().getId()];
    }

    @Override
    public BlockState getChain(BaseHookEntity entity) {
        return Blocks.CHAIN.defaultBlockState();
    }
}
