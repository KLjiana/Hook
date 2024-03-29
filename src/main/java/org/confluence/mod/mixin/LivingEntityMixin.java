package org.confluence.mod.mixin;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.confluence.mod.item.curio.combat.ICriticalHit;
import org.confluence.mod.item.curio.movement.IFallResistance;
import org.confluence.mod.item.curio.movement.IJumpBoost;
import org.confluence.mod.util.LivingMixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.concurrent.atomic.AtomicInteger;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements LivingMixin {
    @Unique
    private double c$jumpBoost = 1.0;
    @Unique
    private int c$fallResistance = 0;
    @Unique
    private int c$invulnerableTime = 20;
    @Unique
    private float c$criticalChance = 0.0F;

    @Unique
    @Override
    public void c$freshJumpBoost() {
        AtomicDouble maxBoost = new AtomicDouble();
        CuriosApi.getCuriosInventory((LivingEntity) (Object) this).ifPresent(handler -> {
            IItemHandlerModifiable itemHandlerModifiable = handler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                if (itemHandlerModifiable.getStackInSlot(i).getItem() instanceof IJumpBoost iJumpBoost) {
                    maxBoost.set(Math.max(iJumpBoost.getBoost(), maxBoost.get()));
                }
            }
        });
        this.c$jumpBoost = maxBoost.get();
    }

    @Unique
    @Override
    public void c$freshFallResistance() {
        AtomicInteger fallResistance = new AtomicInteger();
        CuriosApi.getCuriosInventory((LivingEntity) (Object) this).ifPresent(handler -> {
            IItemHandlerModifiable itemHandlerModifiable = handler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                if (itemHandlerModifiable.getStackInSlot(i).getItem() instanceof IFallResistance iFallResistance) {
                    int distance = iFallResistance.getFallResistance();
                    if (distance == -1) {
                        fallResistance.set(-1);
                        return;
                    } else {
                        fallResistance.set(Math.max(distance, fallResistance.get()));
                    }
                }
            }
        });
        this.c$fallResistance = fallResistance.get();
    }

    @Unique
    @Override
    public void c$setInvulnerableTime(int time) {
        this.c$invulnerableTime = time;
    }

    @Unique
    @Override
    public void c$freshCriticalChance() {
        AtomicDouble maxChance = new AtomicDouble();
        CuriosApi.getCuriosInventory((LivingEntity) (Object) this).ifPresent(curiosItemHandler -> {
            IItemHandlerModifiable itemHandlerModifiable = curiosItemHandler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                if (itemHandlerModifiable.getStackInSlot(i).getItem() instanceof ICriticalHit iCriticalHit) {
                    maxChance.set(Math.max(iCriticalHit.getChance(), maxChance.get()));
                }
            }
        });
        this.c$criticalChance = maxChance.floatValue();
    }

    @Unique
    @Override
    public float c$getCriticalChance() {
        return c$criticalChance;
    }

    @Inject(method = "getJumpPower", at = @At("RETURN"), cancellable = true)
    private void multiY(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue((float) (cir.getReturnValue() * c$jumpBoost));
    }

    @Inject(method = "calculateFallDamage", at = @At(value = "RETURN", ordinal = 1), cancellable = true)
    private void fallDamage(float fallDistance, float multiply, CallbackInfoReturnable<Integer> cir) {
        if (c$fallResistance == -1) {
            cir.setReturnValue(0);
        } else {
            cir.setReturnValue(cir.getReturnValue() - c$fallResistance);
        }
    }

    @ModifyConstant(method = "hurt", constant = @Constant(intValue = 20))
    private int invulnerable1(int constant) {
        return c$invulnerableTime;
    }

    @ModifyConstant(method = "handleDamageEvent", constant = @Constant(intValue = 20))
    private int invulnerable2(int constant) {
        return c$invulnerableTime;
    }
}
