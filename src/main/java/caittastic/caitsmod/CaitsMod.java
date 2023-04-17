package caittastic.caitsmod;

import caittastic.caitsmod.init.ModRegistries;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CaitsMod.MOD_ID)
public class CaitsMod{
  //the ID of our mod, in a form that can be accessed anywhere
  public static final String MOD_ID = "caitsmod";

  public CaitsMod(){
    //registers items
    IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

    ModRegistries.ITEMS.register(bus);
    ModRegistries.BLOCKS.register(bus);
    ModRegistries.BLOCK_ENTITIES.register(bus);
    ModRegistries.SOUND_EVENTS.register(bus);

    // Register ourselves for server and other game events we are interested in
    MinecraftForge.EVENT_BUS.register(this);
  }

}
