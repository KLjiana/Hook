package org.confluence.mod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.hook.Hooks;

public class ModChineseProvider extends LanguageProvider {
    public ModChineseProvider(PackOutput output) {
        super(output, Confluence.MODID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        add("creativetab.confluence.hook", "汇流来世 | 工具");

        add("key.confluence.hook", "使用钩爪");

        add("curios.identifier.hook", "钩爪栏");

        add("painting.confluence.magic_harp.title", "MAGIC_HARP");
        add("painting.confluence.magic_harp.author", "BiliBili_魔法竖琴waaa，看上去傻傻的...");
        add("painting.confluence.amanita.title", "AMANITA");
        add("painting.confluence.amanita.author", "BiliBili_蘑菇人怎么还不来，一个蘑菇人镁铝！");
        add("painting.confluence.westernat.title", "WESTERNAT");
        add("painting.confluence.westernat.author", "BiliBili_Westernat233，MC21世纪以来，最具有印象派主义的白桦树绘画!");
        add("painting.confluence.cooobrid.title", "COOOBRID");
        add("painting.confluence.cooobrid.author", "BiliBili_事一只一只一只鸽子，咕咕咕咕~");
        add("painting.confluence.nakinosi.title", "NAKINOSI");
        add("painting.confluence.nakinosi.author", "BiliBili_咕咕咕的屑枕头");
        add("painting.confluence.maker.title", "MAKER");
        add("painting.confluence.maker.author", "BiliBili_Maker-2333");
        add("painting.confluence.serious_observers.title", "SERIOUS_OBSERVERS");
        add("painting.confluence.serious_observers.author", "BiliBili_严肃的侦测器，确实挺严肃的");

        add(Hooks.GRAPPLING_HOOK.get(), "抓钩");
    }
}
