package org.confluence.mod.item.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.confluence.mod.client.renderer.item.armor.RaincoatArmorRenderer;
import org.confluence.mod.datagen.limit.NormalGeoItem;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class RaincoatArmorItem extends ArmorItem implements NormalGeoItem {
    private final AnimatableInstanceCache CACHE = GeckoLibUtil.createInstanceCache(this);

    public RaincoatArmorItem(Type type) {
        super(new ArmorMaterial() {
            @Override
            public int getDurabilityForType(@NotNull Type armorType) {
                return switch (armorType) {
                    case HELMET -> 55;
                    case CHESTPLATE -> 80;
                    case LEGGINGS -> 75;
                    case BOOTS -> 65;
                };
            }

            @Override
            public int getDefenseForType(@NotNull Type armorType) {
                return switch (armorType) {
                    default -> 1;
                    case CHESTPLATE -> 4;
                    case LEGGINGS -> 0;
                };
            }

            @Override
            public int getEnchantmentValue() {
                return 15;
            }

            @Override
            public @NotNull SoundEvent getEquipSound() {
                return SoundEvents.ARMOR_EQUIP_LEATHER;
            }

            @Override
            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(Items.OAK_PLANKS);
            }

            @Override
            public @NotNull String getName() {
                return "planks";
            }

            @Override
            public float getToughness() {
                return 0.0F;
            }

            @Override
            public float getKnockbackResistance() {
                return 0.0F;
            }
        }, type, new Properties());
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private RaincoatArmorRenderer renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null) {
                    this.renderer = new RaincoatArmorRenderer();
                }
                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);

                return this.renderer;
            }
        });
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return CACHE;
    }
}
