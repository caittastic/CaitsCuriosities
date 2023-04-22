package caittastic.caitsmod.datagen.models;

import caittastic.caitsmod.Block.ModBlocks;
import caittastic.caitsmod.CaitsMod;
import caittastic.caitsmod.Item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModels extends ItemModelProvider{

  public ModItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper){
    super(generator, CaitsMod.MOD_ID, existingFileHelper);
  }

  @Override
  protected void registerModels(){

    registerWithExistingParent(ModBlocks.RUBBER_DUCK);
    registerWithExistingParent(ModBlocks.HUPWARDSER);
    registerWithExistingParent(ModBlocks.NODE);
    registerWithExistingParent(ModBlocks.BRAIN_JAR);

    registerFlatItemModel(ModItems.TUNING_WAND);

    registerFlatItemModel(ModItems.OVERWORLD_PACK);
    for(int i = 1; i <= ModItems.overworldCards.length; i++){
      RegistryObject<Item> item = ModItems.OVERWORLD_MAP.get(i);
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

  private void registerFlatItemModel(RegistryObject<Item> item){
    ResourceLocation resourceLocation = new ResourceLocation(
            CaitsMod.MOD_ID,
            "item/" + item.getId().getPath());
    singleTexture(
            item.getId().getPath(),
            mcLoc("item/generated"),
            "layer0", resourceLocation);
  }

  private void registerWithExistingParent(RegistryObject<Block> block){
    withExistingParent(block.getId().getPath(), modLoc("block/" + block.getId().getPath()));
  }

}
