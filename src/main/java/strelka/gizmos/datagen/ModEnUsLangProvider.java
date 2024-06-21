package strelka.gizmos.datagen;

import net.minecraft.data.DataGenerator;
import net.neoforged.neoforge.common.data.LanguageProvider;
import strelka.gizmos.Gizmos;
import strelka.gizmos.blocks.ModBlocks;
import strelka.gizmos.items.ModItems;

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

    add(ModBlocks.EXAMPLITE_BLOCK.get(), "Examplite Block");
    add(ModBlocks.EXAMPLITE_ORE.get(), "Examplite Ore");
    add(ModBlocks.DEEPSLATE_EXAMPLITE_ORE.get(), "Deepslate Examplite Ore");
    add(ModBlocks.NETHER_EXAMPLITE_ORE.get(), " Nether Examplite Ore");
    add(ModBlocks.END_EXAMPLITE_ORE.get(), "End Examplite Ore");
    add(ModBlocks.RAW_EXAMPLITE_BLOCK.get(), "Raw Examplite Block");
    add(ModItems.RAW_EXAMPLITE.get(), "Examplite Ingot");
    add(ModItems.EXAMPLITE_INGOT.get(), "Raw Examplite");
    add(ModItems.EXAMPLITE_AXE.get(), "Examplite Axe");
    add(ModItems.EXAMPLITE_PICKAXE.get(), "Examplite Pickaxe");
    add(ModItems.EXAMPLITE_SHOVEL.get(), "Examplite Shovel");
    add(ModItems.EXAMPLITE_SWORD.get(), "Examplite Sword");
    add(ModItems.EXAMPLITE_HOE.get(), "Examplite Hoe");
    add(ModItems.EXAMPLITE_HELMET.get(), "Examplite Helmet");
    add(ModItems.EXAMPLITE_CHESTPLATE.get(), "Examplite Chestplate");
    add(ModItems.EXAMPLITE_LEGGINGS.get(), "Examplite Leggings");
    add(ModItems.EXAMPLITE_BOOTS.get(), "Examplite Boots");
    add(ModBlocks.ERRORWOOD_LOG.get(), "Errorwood Log");
    add(ModBlocks.ERRORWOOD_PLANKS.get(), "Errorwood Planks");
    add(ModBlocks.ERRORWOOD_STAIRS.get(), "Errorwood Stairs");
    add(ModBlocks.ERRORWOOD_SLAB.get(), "Errorwood Slab");
    add(ModBlocks.ERRORWOOD_DOOR.get(), "Errorwood Door");
    add(ModBlocks.ERRORWOOD_TRAPDOOR.get(), "Errorwood Trapdoor");
    add(ModBlocks.ERRORWOOD_FENCE.get(), "Errorwood Fence");
    add(ModBlocks.ERRORWOOD_FENCE_GATE.get(), "Errorwood Fence Gate");
    add(ModBlocks.ERRORWOOD_BUTTON.get(), "Errorwood Button");
    add(ModBlocks.ERRORWOOD_PRESSURE_PLATE.get(), "Errorwood Pressure Plate");
    add(ModBlocks.SPEEDY_BLOCK.get(), "Speedy Block");
    add(ModItems.DOWSING_ROD.get(), "Dowsing Rod");
    add("item.tutorialtastic.dowsing_rod.no_valuables", "sorry, no valuables here");
    add("tooltip.tutorialtastic.dowsing_rod.tooltip.noshift", "right click to find valuables");
    add("tooltip.tutorialtastic.dowsing_rod.tooltip.shift", "hehe youre holding shift");
    add(ModItems.ETERNAL_FUEL.get(), "Eternalish Fuel");
    add(ModItems.PORRIDGE.get(), "Porridge");
  }

  private void addTooltip(String key, String value){
    add("tooltip." + Gizmos.MOD_ID + "." + key, value);
  }
}
