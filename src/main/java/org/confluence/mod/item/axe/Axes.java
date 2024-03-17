package org.confluence.mod.item.axe;

import net.minecraft.world.item.AxeItem;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.ConfluenceItems;
import org.confluence.mod.item.ConfluenceTiers;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum Axes implements EnumRegister<AxeItem> {
    COPPER_AXE("copper_axe", () -> new BaseAxeItem(ConfluenceTiers.COPPER, 2, 1)),
    TIN_AXE("tin_axe", () -> new BaseAxeItem(ConfluenceTiers.TIN, 2, 1)),
    LEAD_AXE("lead_axe", () -> new BaseAxeItem(ConfluenceTiers.LEAD, 3, 1)),
    SILVER_AXE("silver_axe", () -> new BaseAxeItem(ConfluenceTiers.SILVER, 3, 1)),
    WOLFRAM_AXE("wolfram_axe", () -> new BaseAxeItem(ConfluenceTiers.TUNGSTEN, 4, 1)),
    PLATINUM_AXE("platinum_axe", () -> new BaseAxeItem(ConfluenceTiers.PLATINUM, 5, 1));

    private final RegistryObject<AxeItem> value;

    Axes(String id, Supplier<AxeItem> axe) {
        this.value = ConfluenceItems.ITEMS.register(id, axe);
    }

    @Override
    public RegistryObject<AxeItem> getValue() {
        return value;
    }

    public static void init() {
        Confluence.LOGGER.info("Registering axes");
    }
}
