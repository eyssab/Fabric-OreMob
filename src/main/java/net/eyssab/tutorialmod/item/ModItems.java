package net.eyssab.tutorialmod.item;

import net.eyssab.tutorialmod.TutorialMod;
import net.eyssab.tutorialmod.item.custom.MetalDetectorItem;
import net.eyssab.tutorialmod.item.custom.VoidWandItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item RUBY = registerItem("ruby", new Item(new FabricItemSettings()));
    public static final Item TOMATO = registerItem("tomato", new Item(new FabricItemSettings().food(ModFoodComponents.TOMATO)));

    public static final Item COAL_BRIQUETTE = registerItem("coal_briquette", new
            Item(new FabricItemSettings()));
    public static final Item RAW_RUBY = registerItem("raw_ruby", new Item(new FabricItemSettings()));

    public static final Item METAL_DETECTOR = registerItem("metal_detector", new MetalDetectorItem(new FabricItemSettings().maxDamage(64)));
    public static final Item RUBY_STAFF = registerItem("ruby_staff",
            new Item(new FabricItemSettings().maxCount(1)));
    public static final Item VOID_WAND = registerItem("void_wand",
            new VoidWandItem(new FabricItemSettings().maxCount(1)));

    private static void addItemstoIngredientItemGroup(FabricItemGroupEntries entries) {
        entries.add(RUBY);
        entries.add(RAW_RUBY);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(TutorialMod.MOD_ID, name), item);
    }
    public static void registerFuelItems() {
        FuelRegistry.INSTANCE.add(ModItems.COAL_BRIQUETTE, 200);
    }

    public static void registerModItems() {
        TutorialMod.LOGGER.info("Registering Mod Items for: " + TutorialMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemstoIngredientItemGroup);
    }
}
