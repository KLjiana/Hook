package org.confluence.mod.client.model.curio;

import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.curio.BaseCurioItem;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public class GeckoCurioModel<T extends BaseCurioItem & GeoAnimatable> extends GeoModel<T> {
    private final ResourceLocation model;
    private final ResourceLocation texture;
    private final ResourceLocation animation;

    public GeckoCurioModel(String location) {
        this.model = new ResourceLocation(Confluence.MODID, "geo/curio/" + location + ".geo.json");
        this.texture = new ResourceLocation(Confluence.MODID, "textures/curio/" + location + ".png");
        this.animation = new ResourceLocation(Confluence.MODID, "animations/curio/" + location + ".animation.json");
    }

    @Override
    public ResourceLocation getModelResource(T animatable) {
        return model;
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        return texture;
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return animation;
    }
}
