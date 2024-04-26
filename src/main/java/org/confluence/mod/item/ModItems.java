package org.confluence.mod.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.armor.Armors;
import org.confluence.mod.item.axe.Axes;
import org.confluence.mod.item.common.*;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.item.food.Foods;
import org.confluence.mod.item.hammer.HammerAxes;
import org.confluence.mod.item.hammer.Hammers;
import org.confluence.mod.item.loot.ClamItem;
import org.confluence.mod.item.mana.ManaWeapons;
import org.confluence.mod.item.pickaxe.Pickaxes;
import org.confluence.mod.item.potion.TerraPotions;
import org.confluence.mod.item.sword.Swords;


@SuppressWarnings("unused")
public final class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Confluence.MODID);

    public static final RegistryObject<MagicMirror> ICE_MIRROR = ITEMS.register("ice_mirror", MagicMirror::new);
    public static final RegistryObject<MagicMirror> MAGIC_MIRROR = ITEMS.register("magic_mirror", MagicMirror::new);
    public static final RegistryObject<BaseItem> ROPE = ITEMS.register("rope", BaseItem::new);
    public static final RegistryObject<BaseItem> ROPE_COIL = ITEMS.register("rope_coil", BaseItem::new);
    public static final RegistryObject<CellPhone> CELL_PHONE = ITEMS.register("cell_phone", CellPhone::new);
    public static final RegistryObject<BaseItem> SHURIKEN = ITEMS.register("shuriken", BaseItem::new);
    public static final RegistryObject<ClamItem> CLAM = ITEMS.register("clam", ClamItem::new);
    public static final RegistryObject<BaseItem> COPPER_COIN = ITEMS.register("copper_coin", BaseItem::new);
    public static final RegistryObject<BaseItem> SILVER_COIN = ITEMS.register("silver_coin", BaseItem::new);
    public static final RegistryObject<BaseItem> GOLDEN_COIN = ITEMS.register("golden_coin", BaseItem::new);
    public static final RegistryObject<BaseItem> PLATINUM_COIN = ITEMS.register("platinum_coin", BaseItem::new);

    public static final RegistryObject<ExpertTestItem> EXPERT_TEST_ITEM = ITEMS.register("expert_test_item", ExpertTestItem::new);
    public static final RegistryObject<MasterTestItem> MASTER_TEST_ITEM = ITEMS.register("master_test_item", MasterTestItem::new);
    public static final RegistryObject<Item> STAR = ITEMS.register("star", () -> new Item(new Item.Properties().rarity(ModRarity.WHITE)));
    public static final RegistryObject<Item> SOUL_CAKE = ITEMS.register("soul_cake", () -> new Item(new Item.Properties().rarity(ModRarity.WHITE)));

    public static void register(IEventBus bus) {
        ModTiers.register();
        Icons.init();
        Materials.init();
        Swords.init();
        Axes.init();
        Pickaxes.init();
        Hammers.init();
        HammerAxes.init();
        Gels.init();
        ManaWeapons.init();
        TerraPotions.init();
        SpawnEggs.init();
        Armors.init();
        CurioItems.init();
        QuestedFishes.init();
        Foods.init();
        ITEMS.register(bus);
    }
}
