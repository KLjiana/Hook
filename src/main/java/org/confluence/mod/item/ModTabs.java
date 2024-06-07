package org.confluence.mod.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.hook.Hooks;

@SuppressWarnings("unused")
public final class ModTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Confluence.MODID);
    // 工具
    public static final RegistryObject<CreativeModeTab> HOOK = TABS.register("hook",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(Hooks.GRAPPLING_HOOK.get()))
            .title(Component.translatable("creativetab.confluence.hook"))
            .displayItems((parameters, output) -> {
                for (Hooks hooks : Hooks.values()) output.accept(hooks.get());
            })
            .build());
}