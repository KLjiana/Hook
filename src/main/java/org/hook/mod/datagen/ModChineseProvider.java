package org.hook.mod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import org.hook.mod.Hook;
import org.hook.mod.item.hook.Hooks;

public class ModChineseProvider extends LanguageProvider {
    public ModChineseProvider(PackOutput output) {
        super(output, Hook.MODID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        add("creativetab.hook.hook", "抓钩");
        add("key.hook.hook", "使用钩爪");
        add("curios.identifier.hook", "钩爪栏");
        add(Hooks.GRAPPLING_HOOK.get(), "抓钩");
    }
}
