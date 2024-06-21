package strelka.gizmos.blocks;

import strelka.gizmos.items.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static strelka.gizmos.Gizmos.MOD_ID;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);

    public static final DeferredBlock<RubberDuckBlock> RUBBER_DUCK =
            registerBlockWithItem("rubber_duck", () ->
                    new RubberDuckBlock(BlockBehaviour.Properties.of().strength(0.1f)));

    public static final DeferredBlock<BrainBlock> BRAIN_JAR =
            registerBlockWithItem("brain_jar", () ->
                    new BrainBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BONE_BLOCK).strength(0.1f)));

    public static final DeferredBlock<NodeBlock> NODE =
            registerBlockWithItem("node", () ->
                    new NodeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(0.1f)));

    private static <T extends Block> DeferredBlock<T> registerBlockWithItem(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, Supplier<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

}
