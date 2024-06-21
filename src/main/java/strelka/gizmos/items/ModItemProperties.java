package strelka.gizmos.items;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class ModItemProperties {
    public static void addCustomItemProperties() {
        makeCatalyst();
    }

    private static void makeCatalyst() {
        ItemProperties.register(ModItems.DESTRUCTION_CATALYST.get(), ResourceLocation.parse("charge"),
                (itemStack, clientLevel, livingEntity, i) -> DestructionCatalystItem.getCharge(itemStack));
    }
}
