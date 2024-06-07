package org.confluence.mod.misc;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import top.theillusivec4.curios.Curios;

import static org.confluence.mod.Confluence.MODID;

public final class ModTags {
    public static class Items {
        public static final TagKey<Item> HOOK = curios("hook");

        private static TagKey<Item> curios(String id) {
            return ItemTags.create(new ResourceLocation(Curios.MODID, id));
        }
    }
}
