package caittastic.caitsmod.init;

import caittastic.caitsmod.init.block.HupwardserBlock;
import caittastic.caitsmod.init.block.HupwardserBlockEntity;
import caittastic.caitsmod.init.block.RubberDuckBlock;
import caittastic.caitsmod.init.item.DestructionCatalyst;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

import static caittastic.caitsmod.CaitsMod.MOD_ID;

public class ModRegistries{
  /* -------------------------------- registering tabs -------------------------------- */
  public static final CreativeModeTab CURIOSITIES_TAB = new CreativeModeTab("curiositiesTab"){
    @Override
    public @NotNull ItemStack makeIcon(){return new ItemStack(RUBBER_DUCK.get());}
  };

  /* ------------------------------- registering items -------------------------------- */
  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
  public static final RegistryObject<Item> DESTRUCTION_CATALYST =
          ITEMS.register("destruction_catalyst", () ->
                  new DestructionCatalyst(new Item.Properties().tab(CURIOSITIES_TAB).durability(1024)));

  /* ------------------------------- registering blocks ------------------------------- */
  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
  public static final RegistryObject<Block> HUPWARDSER =
          registerBlockWithItem("hupwardser", () ->
                  new HupwardserBlock(BlockBehaviour.Properties.of(Material.STONE).noOcclusion()), CURIOSITIES_TAB);
  public static final RegistryObject<Block> RUBBER_DUCK =
          registerBlockWithItem("rubber_duck", () ->
                  new RubberDuckBlock(BlockBehaviour.Properties.of(Material.WOOL).strength(0.1f)), CURIOSITIES_TAB);

  /* --------------------------- registering block entities --------------------------- */
  public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MOD_ID);
  public static final RegistryObject<BlockEntityType<HupwardserBlockEntity>> HUPWARDSER_BLOCK_ENTITY =
          BLOCK_ENTITIES.register("hupwardser_block_entity", () ->
                  BlockEntityType.Builder.of(HupwardserBlockEntity::new, HUPWARDSER.get()).build(null));

  /* ---------------------------------- sound events ---------------------------------- */
  public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MOD_ID);
  public static final RegistryObject<SoundEvent> DESTRUCTION_CATALYST_USE = registerSoundEvent("destruction_catalyst_use");
  public static final RegistryObject<SoundEvent> ITEM_CHARGE = registerSoundEvent("item_charge");  /* registering items */

  /* ------------------------------- registry utilities ------------------------------- */
  private static <T extends Block> RegistryObject<T> registerBlockWithItem(String name, Supplier<T> block, CreativeModeTab tab){
    RegistryObject<T> toReturn = BLOCKS.register(name, block);
    registerBlockItem(name, toReturn, tab);
    return toReturn;
  }

  private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab){
    ITEMS.register(name, () -> new BlockItem(block.get(),
            new Item.Properties().tab(tab)));
  }

  private static RegistryObject<SoundEvent> registerSoundEvent(String name){
    return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(MOD_ID, name)));
  }






}
