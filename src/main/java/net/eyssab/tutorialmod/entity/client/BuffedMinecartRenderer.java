package net.eyssab.tutorialmod.entity.client;

import net.eyssab.tutorialmod.TutorialMod;
import net.eyssab.tutorialmod.entity.ModEntities;
import net.eyssab.tutorialmod.entity.custom.BuffedMinecartEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MinecartEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class BuffedMinecartRenderer extends MinecartEntityRenderer<BuffedMinecartEntity> {
    private static final Identifier TEXTURE = new Identifier(TutorialMod.MOD_ID, "textures/entity/buffed_minecart.png");

    public BuffedMinecartRenderer(EntityRendererFactory.Context context) {
        super(context, ModModelLayers.BUFFED_MINECART);
    }

    @Override
    public Identifier getTexture(BuffedMinecartEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(BuffedMinecartEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
