package caittastic.caitsmod.Item;

import caittastic.caitsmod.CaitsMod;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class ModItemProperties{
  public static void addCustomItemProperties(){
    makeCatalyst(ModItems.DESTRUCTION_CATALYST.get());
  }

  private static void makeCatalyst(Item item){
    ItemProperties.register(ModItems.DESTRUCTION_CATALYST.get(), new ResourceLocation("charge"), (itemStack, clientLevel, livingEntity, i) -> {
      return DestructionCatalyst.getCharge(itemStack);
    });
  }
}
