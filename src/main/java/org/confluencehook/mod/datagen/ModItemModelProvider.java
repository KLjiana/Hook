package org.confluencehook.mod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.confluencehook.mod.ConfluenceHook;
import org.confluencehook.mod.datagen.limit.*;
import org.confluencehook.mod.item.ModItems;

import static org.confluencehook.mod.ConfluenceHook.MODID;

public class ModItemModelProvider extends ItemModelProvider {
    private static final ResourceLocation MISSING_ITEM = new ResourceLocation(MODID, "item/item_icon");
    private static final ResourceLocation MISSING_BLOCK = new ResourceLocation(MODID, "item/blocks_icon");

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
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
                    } else if (block instanceof TrapDoorBlock) {
                        withExistingParent(path, new ResourceLocation(MODID, "block/" + path + "_bottom"));
                    } else {
                        withExistingParent(path, new ResourceLocation(MODID, "block/" + path + (hasInventory(block) ? "_inventory" : "")));
                    }
                } else if (isHandheld(value)) {
                    ItemModelBuilder builder = withExistingParent(path, "item/handheld").texture("layer0", new ResourceLocation(MODID, "item/" + path));
                    if (value instanceof Image32x i32) {
                        i32.preset(builder);
                    } else if (value instanceof Image64x i64) {
                        i64.preset(builder);
                    }
                } else if (value instanceof SpawnEggItem) {
                    withExistingParent(path, "item/template_spawn_egg");
                } else {
                    withExistingParent(path, "item/generated").texture("layer0", new ResourceLocation(MODID, "item/" + path));
                }
            } catch (Exception e) {
                ConfluenceHook.LOGGER.error(e.getMessage());
                withExistingParent(path, isBlockItem ? MISSING_BLOCK : MISSING_ITEM);
            }
        });
    }

    private static boolean hasInventory(Block block) {
        return block instanceof ButtonBlock || block instanceof FenceBlock;
    }

    private static boolean isHandheld(Item item) {
        return item instanceof TieredItem;
    }
}
