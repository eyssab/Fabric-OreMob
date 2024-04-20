package net.eyssab.scariercaves;

import net.eyssab.scariercaves.block.ModBlocks;
import net.eyssab.scariercaves.entity.ModEntities;
import net.eyssab.scariercaves.entity.client.*;
import net.eyssab.scariercaves.entity.custom.OreFienEntity;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.client.render.RenderLayer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.world.Heightmap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.eyssab.scariercaves.ScarierCaves.MOD_ID;

public class ScarierCavesClient implements ClientModInitializer{

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final EntityType<OreFienEntity> ORE_FIEN = ModEntities.ORE_FIEN;

    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RUBY_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RUBY_TRAPDOOR, RenderLayer.getCutout());

        EntityRendererRegistry.register(ModEntities.BUFFED_MINECART, BuffedMinecartRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.BUFFED_MINECART, BuffedMinecartModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.ORE_FIEN, OreFienRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.ORE_FIEN, OreFienModel::getTexturedModelData);
        FabricDefaultAttributeRegistry.register(ModEntities.ORE_FIEN, OreFienEntity.createOreFienAttributes());

        BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, ORE_FIEN, 10, 1, 1);
        SpawnRestriction.register(ORE_FIEN, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, OreFienEntity::canSpawn);

        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            // Check if the spawned entity is a ZombieEntity
            if (entity instanceof OreFienEntity) {
                OreFienEntity orefien = (OreFienEntity) entity;

                // Perform actions specific to the spawned zombie
                LOGGER.info("A ZombieEntity has spawned at position: " + entity.getBlockPos());
            }
        });
    }
}
