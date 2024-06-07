package org.confluencehook.mod.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.confluencehook.mod.ConfluenceHook;
import org.confluencehook.mod.client.model.entity.hook.BaseHookModel;
import org.confluencehook.mod.client.renderer.entity.hook.*;
import org.confluencehook.mod.entity.ModEntities;

@SuppressWarnings("deprecation")
@Mod.EventBusSubscriber(modid = ConfluenceHook.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ModClient {
    @SubscribeEvent
    public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BaseHookModel.LAYER_LOCATION, BaseHookModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.BASE_HOOK.get(), BaseHookRenderer::new);
        /* todo 静止钩 */
    }
}
