package org.confluence.mod;

import com.google.gson.Gson;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.ModTabs;
import org.confluence.mod.misc.ModPaintings;
import org.confluence.mod.network.NetworkHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;

@SuppressWarnings("unused")
@Mod(Confluence.MODID)
public final class Confluence {
    public static final String MODID = "confluence";
    public static final Logger LOGGER = LoggerFactory.getLogger("Confluence");
    public static final Gson GSON = new Gson();

    public static final HashSet<ResourceLocation> REQUIRE_PARENT_DONE = new HashSet<>();
    public static final ResourceKey<Level> HELL = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(MODID, "hell"));

    public Confluence() throws ClassNotFoundException {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(bus);
        ModPaintings.register(bus);
        ModEntities.ENTITIES.register(bus);
        ModTabs.TABS.register(bus);
        NetworkHandler.register();
    }
}
