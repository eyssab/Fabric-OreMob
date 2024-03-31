package net.eyssab.tutorialmod.entity.client;

import net.eyssab.tutorialmod.entity.custom.BuffedMinecartEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.MathHelper;

// Made with Blockbench 4.9.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class BuffedMinecartModel<T extends BuffedMinecartEntity> extends SinglePartEntityModel<T> {
	private final ModelPart front;

	public BuffedMinecartModel(ModelPart root) {
		this.front = root.getChild("front");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData front = modelPartData.addChild("front", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 9.0F));


		ModelPartData front2 = front.addChild("front2", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -10.0F, 8.0F, 16.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.0F, -9.0F, 0.0F, 3.1416F, 0.0F));

		ModelPartData front3 = front.addChild("front3", ModelPartBuilder.create().uv(1, 1).cuboid(-6.0F, -9.0F, 8.0F, 14.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 1.0F, -11.0F, 0.0F, 3.1416F, 0.0F));

		ModelPartData front4 = front.addChild("front4", ModelPartBuilder.create().uv(1, 1).cuboid(-4.0F, -7.0F, 8.0F, 12.0F, 5.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, 0.0F, -12.0F, 0.0F, 3.1416F, 0.0F));

		ModelPartData front5 = front.addChild("front5", ModelPartBuilder.create().uv(1, 1).cuboid(-2.0F, -5.0F, 8.0F, 10.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, -1.0F, -13.0F, 0.0F, 3.1416F, 0.0F));

		ModelPartData front6 = front.addChild("front6", ModelPartBuilder.create().uv(1, 1).cuboid(0.0F, -3.0F, 8.0F, 8.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(4.0F, -2.0F, -14.0F, 0.0F, 3.1416F, 0.0F));

		ModelPartData front7 = front.addChild("front7", ModelPartBuilder.create().uv(1, 1).cuboid(4.0F, -3.0F, 8.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(6.0F, -2.0F, -15.0F, 0.0F, 3.1416F, 0.0F));

		ModelPartData back2 = front.addChild("back2", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -10.0F, 8.0F, 16.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 1.0F, -9.0F));

		ModelPartData right2 = front.addChild("right2", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -10.0F, 6.0F, 16.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.0F, -9.0F, 0.0F, -1.5708F, 0.0F));

		ModelPartData left2 = front.addChild("left2", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -10.0F, 6.0F, 16.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.0F, -9.0F, 0.0F, 1.5708F, 0.0F));

		ModelPartData base = front.addChild("base", ModelPartBuilder.create().uv(0, 10).cuboid(-10.0F, -8.0F, 0.0F, 20.0F, 16.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.0F, -9.0F, 0.0F, -1.5708F, 1.5708F));

		return TexturedModelData.of(modelData, 64, 32);
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		front.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return front;
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		// Rotate the model 90 degrees around the y-axis
		this.front.yaw = MathHelper.cos(animationProgress * 0.1F) * 0.15F * limbAngle + (float) Math.PI / 2; // 90 degrees in radians
	}
}