package org.confluence.mod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraftforge.common.data.LanguageProvider;
import org.confluence.mod.Confluence;
import org.confluence.mod.datagen.limit.CustomName;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.item.ModItems;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ModEnglishProvider extends LanguageProvider {
    public ModEnglishProvider(PackOutput output) {
        super(output, Confluence.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("creativetab.confluence.hook", "Confluence | Hook");

        add("key.confluence.hook", "Throwing Hook");

        add("painting.confluence.magic_harp.title", "MAGIC_HARP");
        add("painting.confluence.magic_harp.author", "BiliBili_魔法竖琴waaa，Looks silly...");
        add("painting.confluence.amanita.title", "AMANITA");
        add("painting.confluence.amanita.author", "BiliBili_蘑菇人怎么还不来，A mushroom lady !");
        add("painting.confluence.westernat.title", "WESTERNAT");
        add("painting.confluence.westernat.author", "BiliBili_Westernat233，MC 21st century, the most impressionist birch painting!");
        add("painting.confluence.cooobrid.title", "COOOBRID");
        add("painting.confluence.cooobrid.author", "BiliBili_事一只一只一只鸽子，咕咕咕咕~");
        add("painting.confluence.nakinosi.title", "NAKINOSI");
        add("painting.confluence.nakinosi.author", "BiliBili_咕咕咕的屑枕头");
        add("painting.confluence.maker.title", "MAKER");
        add("painting.confluence.maker.author", "BiliBili_Maker-2333");
        add("painting.confluence.serious_observers.title", "SERIOUS_OBSERVERS");
        add("painting.confluence.serious_observers.author", "BiliBili_严肃的侦测器，Quite serious indeed");

        add("advancements.start.title", "Old World, New Journey!");
        add("advancements.start.descr", "The afterlife of convergence and exchange");
        add("advancements.copper_short_sword.title", "Take up arms");
        add("advancements.copper_short_sword.descr", "You've got your first weapon, wield it as you face monsters!");
        add("advancements.copper_pickaxe.title", "Go deep underground");
        add("advancements.copper_pickaxe.descr", "You've got your first pickaxe to break hard stone!");
        add("advancements.copper_axe.title", "Collect resources");
        add("advancements.copper_axe.descr", "You've got your first axe, and you've got this important resource, logs!");
        add("advancements.falling_star.title", "Seek the Fallen Light");
        add("advancements.falling_star.descr", "When it meets, the night sky is marked with a special kind of star");
        add("advancements.mana_star.title", "Gather the power of the stars");
        add("advancements.mana_star.descr", "The stars will increase your magical powers");

        add("prefix.confluence.quick", "Quick");
        add("prefix.confluence.hasty", "Hasty");
        add("prefix.confluence.deadly", "Deadly");

        add("prefix.confluence.tooltip.plus", "+%s%% %s");
        add("prefix.confluence.tooltip.take", "-%s%% %s");
        add("prefix.confluence.tooltip.add", "+%s %s");
        add("prefix.confluence.tooltip.attack_damage", "Attack Damage");
        add("prefix.confluence.tooltip.attack_speed", "Attack Speed");
        add("prefix.confluence.tooltip.critical_chance", "Critical Chance");
        add("prefix.confluence.tooltip.knock_back", "Knock Back");
        add("prefix.confluence.tooltip.velocity", "Velocity");
        add("prefix.confluence.tooltip.mana_cost", "Mana Cost");
        add("prefix.confluence.tooltip.armor", "Armor");
        add("prefix.confluence.tooltip.additional_mana", "Additional Mana");
        add("prefix.confluence.tooltip.movement_speed", "Movement Speed");

        ModItems.ITEMS.getEntries().forEach(item -> {
            Item item1 = item.get();
            if (item1 instanceof BlockItem) return;
            if (item1 instanceof CustomName customName) add(item1, customName.getGenName());
            else add(item1, toTitleCase(item.getId().getPath()));
        });
        ModEntities.ENTITIES.getEntries().forEach(entity -> add(entity.get(), toTitleCase(entity.getId().getPath())));
    }

    private static String toTitleCase(String raw) {
        return Arrays.stream(raw.split("_"))
            .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase())
            .collect(Collectors.joining(" "));
    }
}
