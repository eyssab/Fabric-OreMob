package net.eyssab.tutorialmod;

import net.eyssab.tutorialmod.block.ModBlocks;
import net.eyssab.tutorialmod.entity.custom.BuffedMinecartEntity;
import net.eyssab.tutorialmod.item.ModItemGroups;
import net.eyssab.tutorialmod.item.ModItems;
import net.eyssab.tutorialmod.util.ModLootTableModifiers;
import net.eyssab.tutorialmod.entity.ModEntities;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TutorialMod implements ModInitializer {
	public static final String MOD_ID = "tutorialmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModItems.registerFuelItems();
		ModItemGroups.registerItemGroups();
		ModBlocks.registerModBlocks();
		ModLootTableModifiers.modifyLootTables();
	}
}