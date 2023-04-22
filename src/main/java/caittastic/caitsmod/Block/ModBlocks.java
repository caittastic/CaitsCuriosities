package caittastic.caitsmod.Block;

import caittastic.caitsmod.CaitsMod;
import caittastic.caitsmod.Item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static caittastic.caitsmod.CaitsMod.MOD_ID;

public class ModBlocks{
  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);

  public static final RegistryObject<Block> HUPWARDSER =
          registerBlockWithItem("hupwardser", () ->
                  new HupwardserBlock(BlockBehaviour.Properties.of(Material.STONE).noOcclusion()), CaitsMod.CURIOSITIES_TAB);
  public static final RegistryObject<Block> RUBBER_DUCK =
          registerBlockWithItem("rubber_duck", () ->
                  new RubberDuckBlock(BlockBehaviour.Properties.of(Material.WOOL).strength(0.1f)), CaitsMod.CURIOSITIES_TAB);

  public static final RegistryObject<Block> BRAIN_JAR =
          registerBlockWithItem("brain_jar", () ->
                  new BrainBlock(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK).strength(0.1f)), CaitsMod.CURIOSITIES_TAB);

  public static final RegistryObject<Block> NODE =
          registerBlockWithItem("node", () ->
                  new NodeBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(0.1f)), CaitsMod.CURIOSITIES_TAB);

  private static <T extends Block> RegistryObject<T> registerBlockWithItem(String name, Supplier<T> block, CreativeModeTab tab){
    RegistryObject<T> toReturn = BLOCKS.register(name, block);
    registerBlockItem(name, toReturn, tab);
    return toReturn;
  }

  private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab){
    ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
            new Item.Properties().tab(tab)));
  }

}
