package org.hook.mod;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.hook.mod.entity.ModEntities;
import org.hook.mod.item.ModItems;
import org.hook.mod.item.ModTabs;
import org.hook.mod.network.NetworkHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SuppressWarnings("unused")
@Mod(Hook.MODID)
public final class Hook {
    public static final String MODID = "hook";
    public static final Logger LOGGER = LoggerFactory.getLogger("Hook");

    public Hook() throws ClassNotFoundException {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(bus);
        ModEntities.ENTITIES.register(bus);
        ModTabs.TABS.register(bus);
        NetworkHandler.register();
    }
}
