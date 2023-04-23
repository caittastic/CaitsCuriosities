package caittastic.caitsmod.datagen;

import caittastic.caitsmod.Block.ModBlocks;
import caittastic.caitsmod.CaitsMod;
import caittastic.caitsmod.Item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class ModEnUsLangProvider extends LanguageProvider{
  public ModEnUsLangProvider(DataGenerator gen, String locale){
    super(gen, CaitsMod.MOD_ID, locale);
  }

  @Override
  protected void addTranslations(){
    add(ModItems.OVERWORLD_PACK.get(), "Card Pack");
    add(ModItems.DESTRUCTION_CATALYST.get(), "Destruction Catalyst");
    add(ModBlocks.RUBBER_DUCK.get(), "Rubber Ducky");
    for(int i = 0; i < ModItems.overworldCards.length; i++){
      int cardNumber = i + 1;
      add(ModItems.OVERWORLD_MAP.get(cardNumber).get(), ModItems.overworldCards[i][0]);
      add("tooltip." + CaitsMod.MOD_ID + ".overworld_card_" + cardNumber, "§7§o" + ModItems.overworldCards[i][1]);
    }

    add("itemGroup.curiositiesTab", "Cait's Curiosities");

    add("card_shiny_indicator." + CaitsMod.MOD_ID, "§5§o♢ ");

    addTooltip("overworld_card_set", "§8§oOverworld Card");
    addTooltip("overworld_pack_tooltip", "§7§oOverworld Set Pack");
    addTooltip("cart_count_3_tooltip", "§8§oContains 3 Cards");
    addTooltip("DestructionCatalyst", "Shift-use to charge, use to fire!");

    add("itemGroup.curiosities_tab", "Cait's Curiosities");

    add("tuning_wand.attuned", "Attuned to node");
    add("tuning_wand.unattuned", "Deposited frequency of node");
    add(ModBlocks.NODE.get(), "Node");
    add(ModBlocks.BRAIN_JAR.get(), "Brain In A Jar");
    add(ModItems.TUNING_WAND.get(), "Tuning Wand");
    add("tooltip.attuned", "Node position:");
  }

  private void addTooltip(String key, String value){
    add("tooltip." + CaitsMod.MOD_ID + "." + key, value);
  }
}
