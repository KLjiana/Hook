package org.hook.mod.misc;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.Curios;

public final class ModTags {
    public static class Items {
        public static final TagKey<Item> HOOK = curios("hook");

        private static TagKey<Item> curios(String id) {
            return ItemTags.create(new ResourceLocation(Curios.MODID, id));
        }
    }
}
