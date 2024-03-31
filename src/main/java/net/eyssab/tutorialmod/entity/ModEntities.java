package net.eyssab.tutorialmod.entity;

import net.eyssab.tutorialmod.TutorialMod;
import net.eyssab.tutorialmod.entity.custom.BuffedMinecartEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<BuffedMinecartEntity> BUFFED_MINECART = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(TutorialMod.MOD_ID, "buffed_minecart"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, BuffedMinecartEntity::new)
                    .dimensions(EntityDimensions.fixed(1f, 1f)).build());
}

