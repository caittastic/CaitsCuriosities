package strelka.gizmos.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class NodeBE extends BlockEntity {
    BlockPos brainPos;

    public NodeBE(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.NODE.get(), pPos, pBlockState);
        this.brainPos = null;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        if (this.hasLinkedBrain()) {
            pTag.putInt("brain_x", this.brainPos.getX());
            pTag.putInt("brain_y", this.brainPos.getY());
            pTag.putInt("brain_z", this.brainPos.getZ());
        }
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        if (this.hasLinkedBrain())
            this.brainPos = new BlockPos(pTag.getInt("brain_x"), pTag.getInt("brain_y"), pTag.getInt("brain_z"));
    }

    public boolean hasLinkedBrain() {
        return this.brainPos != null;
    }

    public void linkBrain(BlockPos pos) {
        this.brainPos = pos;
    }

    public void removeBrain() {
        this.brainPos = null;
    }

}
