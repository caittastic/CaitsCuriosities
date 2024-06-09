package caittastic.caitsmod.blockentities;

import caittastic.caitsmod.blocks.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static caittastic.caitsmod.CaitsMod.MOD_ID;

public class ModBlockEntities{
  public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MOD_ID);

  public static final Supplier<BlockEntityType<BrainBE>> BRAIN =
          BLOCK_ENTITIES.register("brain", () ->
                  BlockEntityType.Builder.of(BrainBE::new,
                          ModBlocks.BRAIN_JAR.get()).build(null));

  public static final Supplier<BlockEntityType<NodeBE>> NODE =
          BLOCK_ENTITIES.register("node", () ->
                  BlockEntityType.Builder.of(NodeBE::new,
                          ModBlocks.NODE.get()).build(null));

}
