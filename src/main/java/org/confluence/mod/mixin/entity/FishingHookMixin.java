package org.confluence.mod.mixin.entity;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.storage.loot.LootParams;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.capability.ability.AbilityProvider;
import org.confluence.mod.capability.ability.PlayerAbility;
import org.confluence.mod.item.fishing.Baits;
import org.confluence.mod.misc.ModLootTables;
import org.confluence.mod.misc.ModTags;
import org.confluence.mod.mixin.accessor.LootParamsAccessor;
import org.confluence.mod.util.IFishingHook;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.Optional;

@Mixin(FishingHook.class)
public abstract class FishingHookMixin implements IFishingHook {
    @Unique
    private static final EntityDataAccessor<Boolean> DATA_LAVA = SynchedEntityData.defineId(FishingHook.class, EntityDataSerializers.BOOLEAN);

    @Shadow
    @Nullable
    public abstract Player getPlayerOwner();

    @Shadow
    @Final
    private int luck;
    @Unique
    private ItemStack c$bait = null;

    @Unique
    @Override
    public void c$setIsLavaHook() {
        c$getSelf().getEntityData().set(DATA_LAVA, true);
    }

    @Unique
    @Override
    public boolean c$isLavaHook() {
        return c$getSelf().getEntityData().get(DATA_LAVA);
    }

    @Unique
    @Override
    public @Nullable ItemStack c$getBait() {
        return c$bait;
    }

    @ModifyArg(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z"))
    private TagKey<Fluid> isLavaTag(TagKey<Fluid> pTag) {
        if (c$isLavaHook()) return ModTags.FISHING_ABLE;
        return ModTags.NOT_LAVA;
    }

    @Redirect(method = "catchingFish", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"))
    private boolean isLavaBlock(BlockState instance, Block block) {
        if (c$isLavaHook()) return instance.is(block) || instance.is(Blocks.LAVA) || instance.is(ModBlocks.HONEY.get()) || instance.is(ModBlocks.SHIMMER.get());
        return instance.is(block) || instance.is(ModBlocks.HONEY.get()) || instance.is(ModBlocks.SHIMMER.get());
    }

    @ModifyArg(method = "getOpenWaterTypeForBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z"))
    private TagKey<Fluid> fluidType(TagKey<Fluid> pTag) {
        if (c$isLavaHook()) return ModTags.FISHING_ABLE;
        return ModTags.NOT_LAVA;
    }

    @ModifyArg(method = "catchingFish", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I", ordinal = 0), index = 0)
    private ParticleOptions smokeParticle(ParticleOptions pType) {
        if (c$isInLava()) return ParticleTypes.SMOKE;
        return pType;
    }

    @ModifyArg(method = "catchingFish", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I", ordinal = 1), index = 0)
    private ParticleOptions flameParticle(ParticleOptions pType) {
        if (c$isInLava()) return ParticleTypes.FLAME;
        return pType;
    }

    @ModifyArg(method = "catchingFish", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I", ordinal = 2), index = 0)
    private ParticleOptions flameParticle2(ParticleOptions pType) {
        if (c$isInLava()) return ParticleTypes.FLAME;
        return pType;
    }

    @ModifyArg(method = "catchingFish", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I", ordinal = 3), index = 0)
    private ParticleOptions smokeParticle2(ParticleOptions pType) {
        if (c$isInLava()) return ParticleTypes.SMOKE;
        return pType;
    }

    @ModifyArg(method = "catchingFish", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I", ordinal = 4), index = 0)
    private ParticleOptions flameParticle3(ParticleOptions pType) {
        if (c$isInLava()) return ParticleTypes.FLAME;
        return pType;
    }

    @ModifyArg(method = "catchingFish", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I", ordinal = 5), index = 0)
    private ParticleOptions lavaParticle(ParticleOptions pType) {
        if (c$isInLava()) return ParticleTypes.LAVA;
        return pType;
    }

    @ModifyArg(method = "retrieve", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/loot/LootDataManager;getLootTable(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/world/level/storage/loot/LootTable;"))
    private ResourceLocation loot(ResourceLocation par1) {
        if (c$isInLava()) return ModLootTables.FISHING_LAVA;
        return par1;
    }

    @ModifyArg(method = "retrieve", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/loot/LootTable;getRandomItems(Lnet/minecraft/world/level/storage/loot/LootParams;)Lit/unimi/dsi/fastutil/objects/ObjectArrayList;"))
    private LootParams modifyLuck(LootParams pParams) {
        Player owner = getPlayerOwner();
        AtomicDouble fishing = new AtomicDouble(luck);
        if (owner != null) {
            Optional<PlayerAbility> optional = owner.getCapability(AbilityProvider.CAPABILITY).resolve();
            if (optional.isPresent()) {
                fishing.addAndGet(optional.get().getFishingPower(owner));
            } else {
                fishing.addAndGet(owner.getLuck());
            }
            Inventory inventory = owner.getInventory();
            float bonus = 1.0F;
            for (ItemStack itemStack : inventory.offhand) {
                if (itemStack.getItem() instanceof Baits.IBait iBait) {
                    this.c$bait = itemStack;
                    bonus += iBait.getBaitBonus();
                    break;
                }
                this.c$bait = null;
            }
            if (c$bait == null) {
                for (ItemStack itemStack : inventory.items) {
                    if (itemStack.getItem() instanceof Baits.IBait iBait) {
                        this.c$bait = itemStack;
                        bonus += iBait.getBaitBonus();
                        break;
                    }
                }
            }
            if (c$bait != null) fishing.set(fishing.get() * bonus);
        }
        ((LootParamsAccessor) pParams).setLuck(fishing.floatValue());
        return pParams;
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void define(CallbackInfo ci) {
        c$getSelf().getEntityData().define(DATA_LAVA, false);
    }

    @ModifyArg(method = "retrieve", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/loot/LootDataManager;getLootTable(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/world/level/storage/loot/LootTable;"))
    private ResourceLocation modifyLoot(ResourceLocation par1) {
        if (c$getSelf().getType() == EntityType.FISHING_BOBBER) return par1;
        return ModLootTables.FISH;
    }

    @Unique
    private FishingHook c$getSelf() {
        return (FishingHook) (Object) this;
    }

    @Unique
    private boolean c$isInLava() {
        FishingHook self = c$getSelf();
        return self.level().getFluidState(self.blockPosition()).is(FluidTags.LAVA);
    }
}
