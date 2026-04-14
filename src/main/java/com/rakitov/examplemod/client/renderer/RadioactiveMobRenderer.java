package com.rakitov.examplemod.client.renderer;

import com.rakitov.examplemod.entity.RadioactiveMobEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.ResourceLocation;

public class RadioactiveMobRenderer extends HumanoidMobRenderer<RadioactiveMobEntity, HumanoidRenderState, HumanoidModel<HumanoidRenderState>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.withDefaultNamespace("textures/entity/zombie/zombie.png");

    public RadioactiveMobRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.ZOMBIE)), 0.5F);
    }

    @Override
    public HumanoidRenderState createRenderState() {
        return new HumanoidRenderState();
    }

    @Override
    public ResourceLocation getTextureLocation(HumanoidRenderState renderState) {
        return TEXTURE;
    }
}
