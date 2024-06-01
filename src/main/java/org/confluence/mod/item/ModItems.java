package org.confluence.mod.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.hook.Hooks;


@SuppressWarnings("unused")
public final class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Confluence.MODID);
    public static final RegistryObject<Item> COPPER_COIN = ITEMS.register("copper_coin", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).fireResistant()));
    public static final RegistryObject<Item> SILVER_COIN = ITEMS.register("silver_coin", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).fireResistant()));
    public static final RegistryObject<Item> GOLDEN_COIN = ITEMS.register("golden_coin", () -> new Item(new Item.Properties().rarity(Rarity.RARE).fireResistant()));
    public static final RegistryObject<Item> PLATINUM_COIN = ITEMS.register("platinum_coin", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).fireResistant()));
    public static final RegistryObject<Item> STAR = ITEMS.register("star", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SOUL_CAKE = ITEMS.register("soul_cake", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SUGAR_PLUM = ITEMS.register("sugar_plum", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> HEART = ITEMS.register("heart", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CANDY_APPLE = ITEMS.register("candy_apple", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CANDY_CANE = ITEMS.register("candy_cane", () -> new Item(new Item.Properties()));

    public static void register(IEventBus bus) {
        Hooks.init();
        ITEMS.register(bus);
    }
}
