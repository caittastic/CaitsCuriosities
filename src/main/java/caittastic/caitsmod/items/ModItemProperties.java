package caittastic.caitsmod.items;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ModItemProperties{
  public static void addCustomItemProperties(){
    makeCatalyst(ModItems.DESTRUCTION_CATALYST.get());
  }

  private static void makeCatalyst(Item item){
    ItemProperties.register(ModItems.DESTRUCTION_CATALYST.get(), new ResourceLocation("charge"), (itemStack, clientLevel, livingEntity, i) -> {
      return DestructionCatalystItem.getCharge(itemStack);
    });
  }
}
