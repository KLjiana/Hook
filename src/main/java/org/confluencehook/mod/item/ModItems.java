package org.confluencehook.mod.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.confluencehook.mod.ConfluenceHook;
import org.confluencehook.mod.item.hook.Hooks;


@SuppressWarnings("unused")
public final class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ConfluenceHook.MODID);

    public static void register(IEventBus bus) {
        Hooks.init();
        ITEMS.register(bus);
    }
}
