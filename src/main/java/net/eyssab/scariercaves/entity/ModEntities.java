package net.eyssab.scariercaves.entity;

import net.eyssab.scariercaves.ScarierCaves;
import net.eyssab.scariercaves.entity.custom.BuffedMinecartEntity;
import net.eyssab.scariercaves.entity.custom.OreFienEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<BuffedMinecartEntity> BUFFED_MINECART = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(ScarierCaves.MOD_ID, "buffed_minecart"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, BuffedMinecartEntity::new)
                    .dimensions(EntityDimensions.fixed(1f, 1f)).build());

    public static final EntityType<OreFienEntity> ORE_FIEN = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(ScarierCaves.MOD_ID, "ore_fien"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, OreFienEntity::new).fireImmune()
                    .dimensions(EntityDimensions.fixed(0.4f, 0.8f)).build());
}