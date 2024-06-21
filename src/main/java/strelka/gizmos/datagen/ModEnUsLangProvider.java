package strelka.gizmos.datagen;

import strelka.gizmos.Gizmos;
import strelka.gizmos.blocks.ModBlocks;
import strelka.gizmos.items.ModItems;
import net.minecraft.data.DataGenerator;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModEnUsLangProvider extends LanguageProvider {
  public ModEnUsLangProvider(DataGenerator gen, String locale){
    super(gen.getPackOutput(), Gizmos.MOD_ID, locale);
  }

  @Override
  protected void addTranslations(){
    add(ModItems.OVERWORLD_PACK.get(), "Card Pack");
    add(ModItems.DESTRUCTION_CATALYST.get(), "Destruction Catalyst");
    add(ModBlocks.RUBBER_DUCK.get(), "Rubber Ducky");
    for(int i = 0; i < ModItems.OVERWORLD_CARDS.length; i++){
      int cardNumber = i + 1;
      add(ModItems.OVERWORLD_MAP.get(cardNumber).get(), ModItems.OVERWORLD_CARDS[i][0]);
      add("tooltip." + Gizmos.MOD_ID + ".overworld_card_" + cardNumber, "§7§o" + ModItems.OVERWORLD_CARDS[i][1]);
    }



    add("card_shiny_indicator." + Gizmos.MOD_ID, "§5§o♢ ");

    addTooltip("overworld_card_set", "§8§oOverworld Card");
    addTooltip("overworld_pack_tooltip", "§7§oOverworld Set Pack");
    addTooltip("cart_count_3_tooltip", "§8§oContains 3 Cards");
    addTooltip("DestructionCatalyst", "Shift-use to charge, use to fire!");

    add("itemGroup.curiosities_tab", "Zoey's Gizmos");

    add("tuning_wand.attuned", "Attuned to node");
    add("tuning_wand.unattuned", "Deposited frequency of node");
    add("tuning_wand.attunement_failed", "Node is already attuned to Brain");
    add("tuning_wand.attunement_removed", "Removed nodes attunement");
    add("tooltip.attuned", "Node position:");
    add(ModBlocks.NODE.get(), "Node");
    add(ModBlocks.BRAIN_JAR.get(), "Brain In A Jar");
    add(ModItems.TUNING_WAND.get(), "Tuning Wand");
    add(ModItems.ROTTEN_BRAIN.get(), "Rotten Brain");
  }

  private void addTooltip(String key, String value){
    add("tooltip." + Gizmos.MOD_ID + "." + key, value);
  }
}
