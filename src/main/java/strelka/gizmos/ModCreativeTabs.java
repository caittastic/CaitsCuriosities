package strelka.gizmos;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import strelka.gizmos.blocks.ModBlocks;
import strelka.gizmos.items.ModItems;

public class ModCreativeTabs{
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Gizmos.MOD_ID);

    static{
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
                for(DeferredItem<?> item: ModItems.OVERWORLD_MAP.values()){
                    output.accept(item);
                }
                output.accept(ModBlocks.EXAMPLITE_BLOCK);
                output.accept(ModBlocks.RAW_EXAMPLITE_BLOCK);
                output.accept(ModBlocks.EXAMPLITE_ORE);
                output.accept(ModBlocks.DEEPSLATE_EXAMPLITE_ORE);
                output.accept(ModBlocks.NETHER_EXAMPLITE_ORE);
                output.accept(ModBlocks.END_EXAMPLITE_ORE);
                output.accept(ModBlocks.SPEEDY_BLOCK);
                output.accept(ModBlocks.ERRORWOOD_LOG);
                output.accept(ModBlocks.ERRORWOOD_PLANKS);
                output.accept(ModBlocks.ERRORWOOD_STAIRS);
                output.accept(ModBlocks.ERRORWOOD_SLAB);
                output.accept(ModBlocks.ERRORWOOD_FENCE);
                output.accept(ModBlocks.ERRORWOOD_FENCE_GATE);
                output.accept(ModBlocks.ERRORWOOD_BUTTON);
                output.accept(ModBlocks.ERRORWOOD_PRESSURE_PLATE);
                output.accept(ModBlocks.ERRORWOOD_DOOR);
                output.accept(ModBlocks.ERRORWOOD_TRAPDOOR);
                output.accept(ModItems.EXAMPLITE_INGOT);
                output.accept(ModItems.RAW_EXAMPLITE);
                output.accept(ModItems.ETERNAL_FUEL);
                output.accept(ModItems.DOWSING_ROD);
                output.accept(ModItems.EXAMPLITE_SWORD);
                output.accept(ModItems.EXAMPLITE_PICKAXE);
                output.accept(ModItems.EXAMPLITE_AXE);
                output.accept(ModItems.EXAMPLITE_SHOVEL);
                output.accept(ModItems.EXAMPLITE_HOE);
                output.accept(ModItems.EXAMPLITE_HELMET);
                output.accept(ModItems.EXAMPLITE_CHESTPLATE);
                output.accept(ModItems.EXAMPLITE_LEGGINGS);
                output.accept(ModItems.EXAMPLITE_BOOTS);
                output.accept(ModItems.PORRIDGE);
            }).build());
    }
}
