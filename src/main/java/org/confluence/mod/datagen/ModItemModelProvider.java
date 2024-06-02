package org.confluence.mod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.confluence.mod.Confluence;
import org.confluence.mod.datagen.limit.CustomItemModel;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.common.IconItem;

import static org.confluence.mod.Confluence.MODID;

public class ModItemModelProvider extends ItemModelProvider {
    private static final ResourceLocation MISSING_ITEM = new ResourceLocation(MODID, "item/item_icon");
    private static final ResourceLocation MISSING_BLOCK = new ResourceLocation(MODID, "item/blocks_icon");

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (IconItem.Icons icons : IconItem.Icons.values()) {
            String path = icons.name().toLowerCase();
            withExistingParent(path, "item/generated").texture("layer0", new ResourceLocation(MODID, "item/" + path));
        }

        ModItems.ITEMS.getEntries().forEach(item -> {
            Item value = item.get();

            String path = item.getId().getPath().toLowerCase();
            boolean isBlockItem = false;
            try {
                if (value instanceof BlockItem blockItem) {
                    isBlockItem = true;
                    Block block = blockItem.getBlock();
                    if (block instanceof CustomItemModel) return;
                    if (block instanceof DoorBlock) {
                        withExistingParent(path, "item/generated").texture("layer0", new ResourceLocation(MODID, "item/" + path));
                    }
                } else if (value instanceof SpawnEggItem) {
                    withExistingParent(path, "item/template_spawn_egg");
                } else {
                    withExistingParent(path, "item/generated").texture("layer0", new ResourceLocation(MODID, "item/" + path));
                }
            } catch (Exception e) {
                Confluence.LOGGER.error(e.getMessage());
                withExistingParent(path, isBlockItem ? MISSING_BLOCK : MISSING_ITEM);
            }
        });
    }

}
