package caittastic.caitsmod.datagen;

import caittastic.caitsmod.CaitsMod;
import caittastic.caitsmod.datagen.models.ModItemModels;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CaitsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators{
  @SubscribeEvent
  public static void gatherData(GatherDataEvent event){
    ExistingFileHelper helper = event.getExistingFileHelper();
    DataGenerator generator = event.getGenerator();

    /*     server     */
    boolean isIncludeServer = event.includeServer();
    generator.addProvider(isIncludeServer, new ModRecipes(generator));

    /*     client     */
    boolean isIncludeClient = event.includeClient();
    generator.addProvider(isIncludeClient, new ModItemModels(generator, helper));
    generator.addProvider(isIncludeClient, new ModEnUsLangProvider(generator, "en_us"));
  }
}
