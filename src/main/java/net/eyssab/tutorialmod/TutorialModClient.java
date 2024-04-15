package net.eyssab.tutorialmod;

import net.eyssab.tutorialmod.block.ModBlocks;
import net.eyssab.tutorialmod.entity.ModEntities;
import net.eyssab.tutorialmod.entity.client.*;
import net.eyssab.tutorialmod.entity.custom.OreFienEntity;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.client.render.RenderLayer;

public class TutorialModClient implements ClientModInitializer{
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RUBY_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RUBY_TRAPDOOR, RenderLayer.getCutout());

        EntityRendererRegistry.register(ModEntities.BUFFED_MINECART, BuffedMinecartRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.BUFFED_MINECART, BuffedMinecartModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.ORE_FIEN, OreFienRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.ORE_FIEN, OreFienModel::getTexturedModelData);
        FabricDefaultAttributeRegistry.register(ModEntities.ORE_FIEN, OreFienEntity.createOreFienAttributes());
    }
}
