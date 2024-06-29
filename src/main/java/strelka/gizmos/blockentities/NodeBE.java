package strelka.gizmos.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class NodeBE extends BlockEntity {
    private BlockPos brainPos;

    public NodeBE(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.NODE.get(), pPos, pBlockState);
        this.brainPos = null;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        if (this.hasLinkedBrain()) {
            pTag.putLong("brain_pos", this.brainPos.asLong());
        }
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        if (this.hasLinkedBrain())
            this.brainPos = BlockPos.of(pTag.getLong("brain_pos"));
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
