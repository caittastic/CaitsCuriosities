package caittastic.caitsmod.datagen.models;

import caittastic.caitsmod.blocks.ModBlocks;
import caittastic.caitsmod.CaitsMod;
import caittastic.caitsmod.items.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public class ModItemModels extends ItemModelProvider {

  public ModItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper){
    super(generator.getPackOutput(), CaitsMod.MOD_ID, existingFileHelper);
  }

  @Override
  protected void registerModels(){
    registerWithExistingParent(ModBlocks.RUBBER_DUCK);
    registerWithExistingParent(ModBlocks.NODE);
    registerWithExistingParent(ModBlocks.BRAIN_JAR);

    registerFlatItemModel(ModItems.TUNING_WAND);
    registerFlatItemModel(ModItems.ROTTEN_BRAIN);

    registerFlatItemModel(ModItems.OVERWORLD_PACK);

    for(int i = 1; i <= ModItems.OVERWORLD_CARDS.length; i++){
      DeferredItem<?> item = ModItems.OVERWORLD_MAP.get(i);
      ResourceLocation back = new ResourceLocation(CaitsMod.MOD_ID,
              "item/overworld_set/" + "overworld_card_back");
      ResourceLocation resourceLocation = new ResourceLocation(CaitsMod.MOD_ID,
              "item/overworld_set/" + item.getId().getPath());
      singleTexture(
              item.getId().getPath(),
              new ResourceLocation(CaitsMod.MOD_ID, "item/card"),
              "front", resourceLocation).texture("back", back);
    }
  }

  private void registerFlatItemModel(DeferredItem<?> item){
    ResourceLocation resourceLocation = new ResourceLocation(
            CaitsMod.MOD_ID,
            "item/" + item.getId().getPath());
    singleTexture(
            item.getId().getPath(),
            mcLoc("item/generated"),
            "layer0", resourceLocation);
  }

  private void registerWithExistingParent(DeferredBlock<?> block){
    withExistingParent(block.getId().getPath(), modLoc("block/" + block.getId().getPath()));
  }

}
