package org.confluencehook.mod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import org.confluencehook.mod.ConfluenceHook;
import org.confluencehook.mod.item.hook.Hooks;

public class ModChineseProvider extends LanguageProvider {
    public ModChineseProvider(PackOutput output) {
        super(output, ConfluenceHook.MODID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        add("creativetab.confluencehook.hook", "汇流来世 | 抓钩");

        add("key.confluencehook.hook", "使用钩爪");

        add("curios.identifier.hook", "钩爪栏");

        add("painting.confluencehook.magic_harp.title", "MAGIC_HARP");
        add("painting.confluencehook.magic_harp.author", "BiliBili_魔法竖琴waaa，看上去傻傻的...");
        add("painting.confluencehook.amanita.title", "AMANITA");
        add("painting.confluencehook.amanita.author", "BiliBili_蘑菇人怎么还不来，一个蘑菇人镁铝！");
        add("painting.confluencehook.westernat.title", "WESTERNAT");
        add("painting.confluencehook.westernat.author", "BiliBili_Westernat233，MC21世纪以来，最具有印象派主义的白桦树绘画!");
        add("painting.confluencehook.cooobrid.title", "COOOBRID");
        add("painting.confluencehook.cooobrid.author", "BiliBili_事一只一只一只鸽子，咕咕咕咕~");
        add("painting.confluencehook.nakinosi.title", "NAKINOSI");
        add("painting.confluencehook.nakinosi.author", "BiliBili_咕咕咕的屑枕头");
        add("painting.confluencehook.maker.title", "MAKER");
        add("painting.confluencehook.maker.author", "BiliBili_Maker-2333");
        add("painting.confluencehook.serious_observers.title", "SERIOUS_OBSERVERS");
        add("painting.confluencehook.serious_observers.author", "BiliBili_严肃的侦测器，确实挺严肃的");

        add(Hooks.GRAPPLING_HOOK.get(), "抓钩");
    }
}
