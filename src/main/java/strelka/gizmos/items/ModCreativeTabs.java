package strelka.gizmos.items;

import strelka.gizmos.Gizmos;
import strelka.gizmos.blocks.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Gizmos.MOD_ID);

    static {
        CREATIVE_TABS.register("curiosities_tab", () -> CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.curiosities_tab"))
                .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                .icon(() -> new ItemStack(ModBlocks.RUBBER_DUCK))
                .displayItems((parameters, output) -> {
                    output.accept(ModItems.DESTRUCTION_CATALYST);
                    output.accept(ModItems.TUNING_WAND);
                    output.accept(ModItems.ROTTEN_BRAIN);
                    output.accept(ModBlocks.RUBBER_DUCK);
                    output.accept(ModBlocks.BRAIN_JAR);
                    output.accept(ModBlocks.NODE);
                    output.accept(ModItems.OVERWORLD_PACK);
                    for (DeferredItem<?> item : ModItems.OVERWORLD_MAP.values()) {
                        output.accept(item);
                    }
                }).build());
    }
}
