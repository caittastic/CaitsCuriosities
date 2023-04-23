package caittastic.caitsmod.BlockEntity;

import caittastic.caitsmod.Block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static caittastic.caitsmod.CaitsMod.MOD_ID;

public class ModBlockEntities{
  public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MOD_ID);

  public static final RegistryObject<BlockEntityType<BrainBE>> BRAIN =
          BLOCK_ENTITIES.register("brain_block_entity", () ->
                  BlockEntityType.Builder.of(BrainBE::new,
                          ModBlocks.BRAIN_JAR.get()).build(null));

  public static final RegistryObject<BlockEntityType<NodeBE>> NODE =
          BLOCK_ENTITIES.register("node_block_entity", () ->
                  BlockEntityType.Builder.of(NodeBE::new,
                          ModBlocks.NODE.get()).build(null));

}
