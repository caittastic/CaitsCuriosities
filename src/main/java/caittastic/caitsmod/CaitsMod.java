package caittastic.caitsmod;

import caittastic.caitsmod.Block.ModBlocks;
import caittastic.caitsmod.BlockEntity.ModBlockEntities;
import caittastic.caitsmod.Item.ModItemProperties;
import caittastic.caitsmod.Item.ModItems;
import caittastic.caitsmod.particles.ModParticles;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CaitsMod.MOD_ID)
public class CaitsMod{
  //the ID of our mod, in a form that can be accessed anywhere
  public static final String MOD_ID = "caitsmod";

  public CaitsMod(){
    //registers items
    IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

    ModItems.ITEMS.register(bus);
    ModBlocks.BLOCKS.register(bus);
    ModBlockEntities.BLOCK_ENTITIES.register(bus);
    ModSoundEvents.SOUND_EVENTS.register(bus);
    ModParticles.PARTICLE_TYPES.register(bus);

    MinecraftForge.EVENT_BUS.register(this);

    bus.addListener(this::clientSetup);
  }

  /* -------------------------------- registering tabs -------------------------------- */
  public static final CreativeModeTab CURIOSITIES_TAB = new CreativeModeTab("curiosities_tab"){
    @Override
    public @NotNull
    ItemStack makeIcon(){return new ItemStack(ModBlocks.RUBBER_DUCK.get());}
  };

  private void clientSetup(final FMLClientSetupEvent event){
    ModItemProperties.addCustomItemProperties();
  }

}
