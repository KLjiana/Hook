package org.hook.mod.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.hook.mod.Hook;
import org.hook.mod.item.hook.Hooks;


@SuppressWarnings("unused")
public final class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Hook.MODID);

    public static void register(IEventBus bus) {
        Hooks.init();
        ITEMS.register(bus);
    }
}
