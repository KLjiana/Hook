package org.confluence.mod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.common.Torches;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.item.hook.Hooks;
import org.confluence.mod.misc.ModTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, CompletableFuture<TagLookup<Block>> b, @Nullable ExistingFileHelper helper) {
        super(output, provider, b, Confluence.MODID, helper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        CurioItems.acceptTag(tag(ModTags.Items.CURIO));
        Hooks.acceptTag(tag(ModTags.Items.HOOK));
        tag(ModTags.Items.PROVIDE_MANA).add(ModItems.STAR.get(), ModItems.SOUL_CAKE.get(), ModItems.SUGAR_PLUM.get());
        tag(ModTags.Items.PROVIDE_LIFE).add(ModItems.HEART.get(), ModItems.CANDY_APPLE.get(), ModItems.CANDY_CANE.get());
        tag(ModTags.Items.COIN).add(ModItems.COPPER_COIN.get(), ModItems.SILVER_COIN.get(), ModItems.GOLDEN_COIN.get(), ModItems.PLATINUM_COIN.get());
        IntrinsicTagAppender<Item> torch = tag(ModTags.Items.TORCH);
        torch.add(Items.TORCH, Items.SOUL_TORCH);
        for (Torches torches : Torches.values()) torch.add(torches.item.get());
        tag(ModTags.Items.BOTTOMLESS).add(
            ModItems.BOTTOMLESS_WATER_BUCKET.get(),
            ModItems.BOTTOMLESS_LAVA_BUCKET.get(),
            ModItems.BOTTOMLESS_HONEY_BUCKET.get(),
            ModItems.BOTTOMLESS_SHIMMER_BUCKET.get()
        );
        tag(ItemTags.MUSIC_DISCS).add(ModItems.ALPHA.get());
    }
}
