package org.hook.mod;

import com.google.gson.Gson;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.hook.mod.entity.ModEntities;
import org.hook.mod.item.ModItems;
import org.hook.mod.item.ModTabs;
import org.hook.mod.network.NetworkHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;

/*TODO
 *  f丢钩子然后拉过去
 * 如果按加速键的话拉的更快
 * 然后wsad的话会给那个方向的一个动量，以钩子为中心绕
 * 然后再按一次f的话钩子收回
 * 空格会有一个向上的力
 */

@SuppressWarnings("unused")
@Mod(Hook.MODID)
public final class Hook {
    public static final String MODID = "hook";
    public static final Logger LOGGER = LoggerFactory.getLogger("Hook");
    public static final Gson GSON = new Gson();

    public static final HashSet<ResourceLocation> REQUIRE_PARENT_DONE = new HashSet<>();
    public static final ResourceKey<Level> HELL = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(MODID, "hell"));

    public Hook() throws ClassNotFoundException {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(bus);
        ModEntities.ENTITIES.register(bus);
        ModTabs.TABS.register(bus);
        NetworkHandler.register();
    }
}
