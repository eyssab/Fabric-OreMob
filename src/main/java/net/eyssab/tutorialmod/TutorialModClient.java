package net.eyssab.tutorialmod;

import net.eyssab.tutorialmod.block.ModBlocks;
import net.eyssab.tutorialmod.entity.ModEntities;
import net.eyssab.tutorialmod.entity.client.*;
import net.eyssab.tutorialmod.entity.custom.OreFienEntity;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.EntityDimensions;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.eyssab.tutorialmod.TutorialMod.MOD_ID;

public class TutorialModClient implements ClientModInitializer{

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

        BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER,
                ORE_FIEN, 100, 1, 3);

        SpawnRestriction.register(ORE_FIEN, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);

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
