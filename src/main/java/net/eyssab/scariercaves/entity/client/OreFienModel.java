package net.eyssab.scariercaves.entity.client;

import net.eyssab.scariercaves.entity.custom.OreFienEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

// Made with Blockbench 4.9.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class OreFienModel<T extends OreFienEntity> extends SinglePartEntityModel<T> {
	private final ModelPart body;
	public OreFienModel(ModelPart root) {
		this.body = root.getChild("body");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 10).cuboid(-2.5F, -4.0F, -1.0F, 5.0F, 5.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 10).cuboid(-2.5F, 1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 10).cuboid(-0.5F, 1.0F, -1.0F, 3.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-3.5F, -7.0F, -2.5F, 7.0F, 7.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -4.0F, 0.0F));

		ModelPartData rightArm = body.addChild("rightArm", ModelPartBuilder.create().uv(23, 0).cuboid(-2.25F, -0.5F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(-0.1F)), ModelTransform.pivot(-1.75F, -3.75F, 0.0F));

		ModelPartData rightItem = rightArm.addChild("rightItem", ModelPartBuilder.create(), ModelTransform.pivot(-0.25F, 2.75F, 0.0F));

		ModelPartData leftArm = body.addChild("leftArm", ModelPartBuilder.create().uv(23, 6).cuboid(0.25F, -0.5F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(-0.1F)), ModelTransform.pivot(1.75F, -3.75F, 0.0F));

		ModelPartData leftWing = body.addChild("leftWing", ModelPartBuilder.create().uv(16, 22).mirrored().cuboid(0.0F, 0.0F, 0.0F, 8.0F, 5.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.5F, -3.0F, 1.0F));

		ModelPartData rightWing = body.addChild("rightWing", ModelPartBuilder.create().uv(16, 22).cuboid(-8.0F, 0.0F, 0.0F, 8.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.5F, -3.0F, 1.0F));
		return TexturedModelData.of(modelData, 64, 32);
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return body;
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}
}