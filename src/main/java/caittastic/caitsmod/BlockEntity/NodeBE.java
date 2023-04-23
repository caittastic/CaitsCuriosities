package caittastic.caitsmod.BlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class NodeBE extends BlockEntity{
  BlockPos brainPos;

  public NodeBE(BlockPos pPos, BlockState pBlockState){
    super(ModBlockEntities.NODE.get(), pPos, pBlockState);
    this.brainPos = null;
  }

  @Override
  protected void saveAdditional(CompoundTag nbt){
    super.saveAdditional(nbt);
    if(this.hasLinkedBrain()){
      nbt.putInt("brain_x", this.brainPos.getX());
      nbt.putInt("brain_y", this.brainPos.getY());
      nbt.putInt("brain_z", this.brainPos.getZ());
    }
  }

  @Override
  public void load(CompoundTag nbt){
    super.load(nbt);
    if(this.hasLinkedBrain())
      this.brainPos = new BlockPos(nbt.getInt("brain_x"), nbt.getInt("brain_y"), nbt.getInt("brain_z"));
  }
  public boolean hasLinkedBrain(){
    return this.brainPos != null;
  }
  public void linkBrain(BlockPos pos){
    this.brainPos = pos;
  }
  public void removeBrain(){
    this.brainPos = null;
  }

}
