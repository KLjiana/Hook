package org.confluencehook.mod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import org.confluencehook.mod.ConfluenceHook;
import org.confluencehook.mod.datagen.limit.CustomName;
import org.confluencehook.mod.entity.ModEntities;
import org.confluencehook.mod.item.ModItems;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ModEnglishProvider extends LanguageProvider {
    public ModEnglishProvider(PackOutput output) {
        super(output, ConfluenceHook.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("creativetab.confluencehook.hook", "ConfluenceHook | Hook");

        add("key.confluencehook.hook", "Throwing Hook");

        add("painting.confluencehook.magic_harp.title", "MAGIC_HARP");
        add("painting.confluencehook.magic_harp.author", "BiliBili_魔法竖琴waaa，Looks silly...");
        add("painting.confluencehook.amanita.title", "AMANITA");
        add("painting.confluencehook.amanita.author", "BiliBili_蘑菇人怎么还不来，A mushroom lady !");
        add("painting.confluencehook.westernat.title", "WESTERNAT");
        add("painting.confluencehook.westernat.author", "BiliBili_Westernat233，MC 21st century, the most impressionist birch painting!");
        add("painting.confluencehook.cooobrid.title", "COOOBRID");
        add("painting.confluencehook.cooobrid.author", "BiliBili_事一只一只一只鸽子，咕咕咕咕~");
        add("painting.confluencehook.nakinosi.title", "NAKINOSI");
        add("painting.confluencehook.nakinosi.author", "BiliBili_咕咕咕的屑枕头");
        add("painting.confluencehook.maker.title", "MAKER");
        add("painting.confluencehook.maker.author", "BiliBili_Maker-2333");
        add("painting.confluencehook.serious_observers.title", "SERIOUS_OBSERVERS");
        add("painting.confluencehook.serious_observers.author", "BiliBili_严肃的侦测器，Quite serious indeed");

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
