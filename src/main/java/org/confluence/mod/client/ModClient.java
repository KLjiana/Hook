package org.confluence.mod.client;

import net.minecraft.world.level.ColorResolver;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.model.entity.hook.BaseHookModel;
import org.confluence.mod.client.model.entity.hook.SkeletronHandModel;
import org.confluence.mod.client.model.entity.hook.WebSlingerModel;
import org.confluence.mod.client.renderer.entity.hook.*;
import org.confluence.mod.entity.ModEntities;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ModClient {
    @SubscribeEvent
    public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BaseHookModel.LAYER_LOCATION, BaseHookModel::createBodyLayer);
        event.registerLayerDefinition(WebSlingerModel.LAYER_LOCATION, WebSlingerModel::createBodyLayer);
        event.registerLayerDefinition(SkeletronHandModel.LAYER_LOCATION, SkeletronHandModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.SLIME_HOOK.get(), SlimeHookRenderer::new);
        event.registerEntityRenderer(ModEntities.FISH_HOOK.get(), FishHookRenderer::new);
        event.registerEntityRenderer(ModEntities.IVY_WHIP.get(), IvyWhipRenderer::new);
        event.registerEntityRenderer(ModEntities.BAT_HOOK.get(), BatHookRenderer::new);
        event.registerEntityRenderer(ModEntities.CANDY_CANE_HOOK.get(), CandyCaneHookRenderer::new);
        event.registerEntityRenderer(ModEntities.DUAL_HOOK.get(), DualHookRenderer::new);
        event.registerEntityRenderer(ModEntities.HOOK_OF_DISSONANCE.get(), HookOfDissonanceRenderer::new);
        event.registerEntityRenderer(ModEntities.THORN_HOOK.get(), ThornHookRenderer::new);
        event.registerEntityRenderer(ModEntities.MIMIC_HOOK.get(), MimicHookRenderer::new);
        event.registerEntityRenderer(ModEntities.ANTI_GRAVITY_HOOK.get(), AntiGravityHookRenderer::new);
        event.registerEntityRenderer(ModEntities.SPOOKY_HOOK.get(), SpookyHookRenderer::new);
        event.registerEntityRenderer(ModEntities.CHRISTMAS_HOOK.get(), ChristmasHookRenderer::new);
        /* todo 静止钩 */
    }

    public static final ColorResolver HALLOW_WATER_RESOLVER = (biome, x, z) -> -1554953;

    @SubscribeEvent
    public static void registerColorResolvers(RegisterColorHandlersEvent.ColorResolvers event) {
        event.register(HALLOW_WATER_RESOLVER);
    }

}
