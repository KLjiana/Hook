package org.confluence.mod.entity.fishing;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractFishingHook extends FishingHook {
    public AbstractFishingHook(EntityType<? extends FishingHook> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public AbstractFishingHook(EntityType<? extends FishingHook> pEntityType, Level pLevel, int pLuck, int pLureSpeed) {
        super(pEntityType, pLevel, pLuck, pLureSpeed);
    }

    protected void setup(Player pPlayer) {
        setOwner(pPlayer);
        float f = pPlayer.getXRot();
        float f1 = pPlayer.getYRot();
        float rad = Mth.PI / 180.0F;
        float f2 = Mth.cos(-f1 * rad - Mth.PI);
        float f3 = Mth.sin(-f1 * rad - Mth.PI);
        float f4 = -Mth.cos(-f * rad);
        float f5 = Mth.sin(-f * rad);
        double d0 = pPlayer.getX() - f3 * 0.3;
        double d1 = pPlayer.getEyeY();
        double d2 = pPlayer.getZ() - f2 * 0.3;
        moveTo(d0, d1, d2, f1, f);
        Vec3 vec3 = new Vec3(-f3, Mth.clamp(-f5 / f4, -5.0F, 5.0F), -f2);
        double d3 = 0.6 / vec3.length();
        vec3 = vec3.multiply(d3 + random.triangle(0.5, 0.0103365), d3 + random.triangle(0.5, 0.0103365), d3 + random.triangle(0.5, 0.0103365));
        setDeltaMovement(vec3);
        setYRot((float) (Mth.atan2(vec3.x, vec3.z) * rad));
        setXRot((float) (Mth.atan2(vec3.y, vec3.horizontalDistance()) * rad));
        this.yRotO = getYRot();
        this.xRotO = getXRot();
    }
}
