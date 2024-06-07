package org.hook.mod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import org.hook.mod.Hook;
import org.hook.mod.item.hook.Hooks;

public class ModEnglishProvider extends LanguageProvider {
    public ModEnglishProvider(PackOutput output) {
        super(output, Hook.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("creativetab.hook.hook", "Hook");
        add("key.hook.hook", "Throwing Hook");
        add("curios.identifier.hook", "Hook");
        add(Hooks.GRAPPLING_HOOK.get(), "Hook");

    }
}
