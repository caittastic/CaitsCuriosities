package caittastic.caitsmod.datagen.loot;

import caittastic.caitsmod.Block.ModBlocks;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockLootTables extends BlockLoot{
  @Override
  protected void addTables(){
    simpleDropSelf(ModBlocks.HUPWARDSER);
    simpleDropSelf(ModBlocks.RUBBER_DUCK);
  }

  private void simpleDropSelf(RegistryObject<Block> self){this.dropSelf(self.get());}

  @Override
  protected Iterable<Block> getKnownBlocks(){
    return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
  }
}

