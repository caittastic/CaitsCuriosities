package strelka.gizmos.datagen;

import strelka.gizmos.Gizmos;
import strelka.gizmos.datagen.models.ModItemModels;
import net.minecraft.data.DataGenerator;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = Gizmos.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators{
  @SubscribeEvent
  public static void gatherData(GatherDataEvent event){
    ExistingFileHelper helper = event.getExistingFileHelper();
    DataGenerator generator = event.getGenerator();

    /*     server     */
    boolean isIncludeServer = event.includeServer();
    generator.addProvider(isIncludeServer, new ModRecipes(generator, event.getLookupProvider()));

    /*     client     */
    boolean isIncludeClient = event.includeClient();
    generator.addProvider(isIncludeClient, new ModItemModels(generator, helper));
    generator.addProvider(isIncludeClient, new ModEnUsLangProvider(generator, "en_us"));
  }
}
