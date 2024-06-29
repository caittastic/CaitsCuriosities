package strelka.gizmos.blocks;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import strelka.gizmos.items.ModItems;

import java.util.function.Supplier;

import static strelka.gizmos.Gizmos.MOD_ID;

public class ModBlocks{
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);
    public static final DeferredBlock<RubberDuckBlock> RUBBER_DUCK = register("rubber_duck", () -> new RubberDuckBlock(BlockBehaviour.Properties.of().strength(0.1f)));
    public static final DeferredBlock<BrainBlock> BRAIN_JAR = register("brain_jar", () -> new BrainBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BONE_BLOCK).strength(0.1f)));
    public static final DeferredBlock<NodeBlock> NODE = register("node", () -> new NodeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(0.1f)));

    public static final DeferredBlock<Block> EXAMPLITE_BLOCK = register("examplite_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> RAW_EXAMPLITE_BLOCK = register("raw_examplite_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.RAW_IRON_BLOCK).strength(5f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> EXAMPLITE_ORE = register("examplite_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE).strength(3f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> DEEPSLATE_EXAMPLITE_ORE = register("deepslate_examplite_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_IRON_ORE).strength(3f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> NETHER_EXAMPLITE_ORE = register("nether_examplite_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE).strength(3f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> END_EXAMPLITE_ORE = register("end_examplite_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE).strength(3f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> SPEEDY_BLOCK = register("speedy_block", () -> new SpeedyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_WOOL).strength(3f)));
    public static final DeferredBlock<Block> ERRORWOOD_LOG = register("errorwood_log", () -> new FlamableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)));
    public static final DeferredBlock<Block> ERRORWOOD_PLANKS = register("errorwood_planks", () -> new FlamableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final DeferredBlock<Block> ERRORWOOD_STAIRS = register("errorwood_stairs", () -> new StairBlock(ERRORWOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(ERRORWOOD_PLANKS.get())));
    public static final DeferredBlock<Block> ERRORWOOD_FENCE_GATE = register("errorwood_fence_gate", () -> new FenceGateBlock(WoodType.OAK, BlockBehaviour.Properties.of().mapColor(ERRORWOOD_PLANKS.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).ignitedByLava()));
    public static final DeferredBlock<Block> ERRORWOOD_PRESSURE_PLATE = register("errorwood_pressure_plate", () -> new PressurePlateBlock(BlockSetType.OAK, BlockBehaviour.Properties.of().mapColor(ERRORWOOD_PLANKS.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(0.5F).ignitedByLava().pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> ERRORWOOD_DOOR = register("errorwood_door", () -> new DoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.of().mapColor(ERRORWOOD_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().ignitedByLava().pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> ERRORWOOD_TRAPDOOR = register("errorwood_trapdoor", () -> new TrapDoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.of().mapColor(ERRORWOOD_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().isValidSpawn((s, g, p, e) -> false).ignitedByLava()));
    public static final DeferredBlock<Block> ERRORWOOD_SLAB = register("errorwood_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB)));
    public static final DeferredBlock<Block> ERRORWOOD_FENCE = register("errorwood_fence", () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE)));
    public static final DeferredBlock<Block> ERRORWOOD_BUTTON = register("errorwood_button", () -> new ButtonBlock(BlockSetType.OAK, 30, BlockBehaviour.Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY)));

    private static <T extends Block> DeferredBlock<T> register(String name, Supplier<T> block){
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, Supplier<T> block){
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

}
