package net.eyssab.scariercaves;

import net.eyssab.scariercaves.block.ModBlocks;
import net.eyssab.scariercaves.item.ModItemGroups;
import net.eyssab.scariercaves.item.ModItems;
import net.eyssab.scariercaves.util.ModLootTableModifiers;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScarierCaves implements ModInitializer {
	public static final String MOD_ID = "scariercaves";
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