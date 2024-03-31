package net.eyssab.tutorialmod;

import net.eyssab.tutorialmod.block.ModBlocks;
import net.eyssab.tutorialmod.entity.ModEntities;
import net.eyssab.tutorialmod.entity.client.BuffedMinecartModel;
import net.eyssab.tutorialmod.entity.client.BuffedMinecartRenderer;
import net.eyssab.tutorialmod.entity.client.ModModelLayers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.RenderLayer;

public class TutorialModClient implements ClientModInitializer{
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RUBY_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RUBY_TRAPDOOR, RenderLayer.getCutout());

        EntityRendererRegistry.register(ModEntities.BUFFED_MINECART, BuffedMinecartRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.BUFFED_MINECART, BuffedMinecartModel::getTexturedModelData);
    }
}
