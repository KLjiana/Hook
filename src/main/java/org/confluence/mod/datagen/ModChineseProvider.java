package org.confluence.mod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.hook.Hooks;

import static org.confluence.mod.item.ModItems.*;

public class ModChineseProvider extends LanguageProvider {
    public ModChineseProvider(PackOutput output) {
        super(output, Confluence.MODID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        add("creativetab.confluence.tools", "汇流来世 | 抓钩栏");

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

        //钩爪
        add(Hooks.GRAPPLING_HOOK.get(), "抓钩");
        add(Hooks.RUBY_HOOK.get(), "红玉钩");
        add(Hooks.AMBER_HOOK.get(), "琥珀钩");
        add(Hooks.TOPAZ_HOOK.get(), "黄玉钩");
        add(Hooks.EMERALD_HOOK.get(), "翡翠钩");
        add(Hooks.SAPPHIRE_HOOK.get(), "蓝玉钩");
        add(Hooks.DIAMOND_HOOK.get(), "钻石钩");
        add(Hooks.AMETHYST_HOOK.get(), "紫晶钩");
        add(Hooks.WEB_SLINGER.get(), "蛛丝吊索");
        add(Hooks.SKELETRON_HAND.get(), "骷髅王之手");
        add(Hooks.SLIME_HOOK.get(), "史莱姆钩");
        add(Hooks.FISH_HOOK.get(), "鱼钩");
        add(Hooks.IVY_WHIP.get(), "常春藤钩");
        add(Hooks.BAT_HOOK.get(), "蝙蝠钩");
        add(Hooks.CANDY_CANE_HOOK.get(), "拐杖糖钩");
        add(Hooks.CHRISTMAS_HOOK.get(), "圣诞挂钩");
        add(Hooks.DUAL_HOOK.get(), "双钩");
        add(Hooks.HOOK_OF_DISSONANCE.get(), "失谐钩");
        add(Hooks.THORN_HOOK.get(), "棘刺钩");
        add(Hooks.ILLUMINANT_HOOK.get(), "荧光钩");
        add(Hooks.WORM_HOOK.get(), "蠕虫钩");
        add(Hooks.LUNAR_HOOK.get(), "月钩");
        add(Hooks.SPOOKY_HOOK.get(), "阴森钩");
        add(Hooks.TENDON_HOOK.get(), "肌腱钩");

    }
}
