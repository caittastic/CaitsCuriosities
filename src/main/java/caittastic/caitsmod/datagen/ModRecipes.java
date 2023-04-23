package caittastic.caitsmod.datagen;

import caittastic.caitsmod.Block.ModBlocks;
import caittastic.caitsmod.Item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ModRecipes extends RecipeProvider{
  public ModRecipes(DataGenerator generator){super(generator);}

  @Override
  protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer){
    ShapedRecipeBuilder.shaped(ModBlocks.RUBBER_DUCK.get(), 1)
            .pattern("FEF")
            .pattern(" H ")
            .define('F', Tags.Items.FEATHERS)
            .define('E', Tags.Items.EGGS)
            .define('H', Items.HAY_BLOCK)
            .unlockedBy("has_feahters", has(Tags.Items.FEATHERS))
            .save(consumer);

    ShapedRecipeBuilder.shaped(ModItems.DESTRUCTION_CATALYST.get(), 1)
            .pattern("tft")
            .pattern("non")
            .pattern("t t")
            .define('t', Blocks.TNT)
            .define('n', Tags.Items.INGOTS_NETHERITE)
            .define('o', Tags.Items.OBSIDIAN)
            .define('f', Items.FLINT_AND_STEEL)
            .unlockedBy("has_tnt", has(Blocks.TNT))
            .save(consumer);

    ShapedRecipeBuilder.shaped(ModItems.TUNING_WAND.get(), 1)
            .pattern(" i ")
            .pattern(" ai")
            .pattern("s  ")
            .define('i', Tags.Items.INGOTS_IRON)
            .define('a', Tags.Items.GEMS_AMETHYST)
            .define('s', Tags.Items.RODS_WOODEN)
            .unlockedBy("has_brain", has(ModItems.ROTTEN_BRAIN.get()))
            .save(consumer);

    ShapedRecipeBuilder.shaped(ModBlocks.BRAIN_JAR.get(), 1)
            .pattern("sss")
            .pattern("gbg")
            .pattern("gag")
            .define('s', ItemTags.WOODEN_SLABS)
            .define('g', Tags.Items.GLASS)
            .define('b', ModItems.ROTTEN_BRAIN.get())
            .define('a', Tags.Items.STORAGE_BLOCKS_AMETHYST)
            .unlockedBy("has_brain", has(ModItems.ROTTEN_BRAIN.get()))
            .save(consumer);

    ShapedRecipeBuilder.shaped(ModBlocks.NODE.get(), 2)
            .pattern("www")
            .pattern("geg")
            .pattern("www")
            .define('w', Items.DARK_OAK_PLANKS)
            .define('g', Tags.Items.INGOTS_GOLD)
            .define('e', Tags.Items.ENDER_PEARLS)
            .unlockedBy("has_brain", has(ModItems.ROTTEN_BRAIN.get()))
            .save(consumer);


  }
}

