package org.hook.mod.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.hook.mod.Hook;
import org.hook.mod.client.model.entity.hook.BaseHookModel;
import org.hook.mod.client.renderer.entity.hook.*;
import org.hook.mod.entity.ModEntities;

@SuppressWarnings("deprecation")
@Mod.EventBusSubscriber(modid = Hook.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ModClient {
    @SubscribeEvent
    public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BaseHookModel.LAYER_LOCATION, BaseHookModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.BASE_HOOK.get(), BaseHookRenderer::new);
    }
}
