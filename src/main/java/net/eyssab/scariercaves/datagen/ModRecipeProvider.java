package net.eyssab.scariercaves.datagen;

import net.eyssab.scariercaves.block.ModBlocks;
import net.eyssab.scariercaves.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;

import java.util.List;

public class ModRecipeProvider extends FabricRecipeProvider {
    private static final List<ItemConvertible> RUBY_SMELTABLES = List.of(ModItems.RAW_RUBY,
            ModBlocks.RUBY_ORE, ModBlocks.DEEPSLATE_RUBY_ORE, ModBlocks.NETHER_RUBY_ORE, ModBlocks.END_STONE_RUBY_ORE);
    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        offerSmelting(exporter, RUBY_SMELTABLES, RecipeCategory.MISC, ModItems.RUBY, 0.7f, 200, "ruby");
        offerBlasting(exporter, RUBY_SMELTABLES, RecipeCategory.MISC, ModItems.RUBY, 0.7f, 100, "ruby");
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.RUBY, RecipeCategory.DECORATIONS
        , ModBlocks.RUBY_BLOCK);
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModItems.RAW_RUBY, 1)
                .pattern("SSS")
                .pattern("SCS")
                .pattern(("SSS"))
                .input('S', Items.STONE)
                .input('C', ModItems.RUBY)
                .criterion(FabricRecipeProvider.hasItem(ModItems.RUBY), FabricRecipeProvider.conditionsFromItem(ModItems.RUBY))
                .criterion(FabricRecipeProvider.hasItem(Items.STONE), FabricRecipeProvider.conditionsFromItem(Items.STONE))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.RUBY_PICKAXE, 1)
                .pattern("SSS")
                .pattern(" C ")
                .pattern((" C "))
                .input('S', ModItems.RUBY)
                .input('C', Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(ModItems.RUBY), FabricRecipeProvider.conditionsFromItem(ModItems.RUBY))
                .criterion(FabricRecipeProvider.hasItem(Items.STICK), FabricRecipeProvider.conditionsFromItem(Items.STICK))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.ELYTRA, 1)
                .pattern("SCS")
                .pattern("SCS")
                .pattern(("SCS"))
                .input('S', ModItems.ELYTRA_SHARD)
                .input('C', Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(ModItems.RUBY), FabricRecipeProvider.conditionsFromItem(ModItems.RUBY))
                .criterion(FabricRecipeProvider.hasItem(Items.STICK), FabricRecipeProvider.conditionsFromItem(Items.STICK))
                .offerTo(exporter);
    }
}
