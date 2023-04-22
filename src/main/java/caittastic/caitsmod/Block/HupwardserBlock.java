package caittastic.caitsmod.Block;

import caittastic.caitsmod.BlockEntity.HupwardserBE;
import caittastic.caitsmod.BlockEntity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class HupwardserBlock extends BaseEntityBlock {
   public HupwardserBlock(Properties pProperties) {
      super(pProperties);
   }

   //stops our block from being invisable
   @Override
   public RenderShape getRenderShape(BlockState pState) {
      return RenderShape.MODEL;
   }

   @Override
   public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
      return Block.box(4, 0, 4, 12, 16, 12);
   }

   @Override
   public boolean propagatesSkylightDown(BlockState pState, BlockGetter pReader, BlockPos pPos) {
      return true;
   }

   //makes sure when the block is broken the inventory drops with it
   @Override
   public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
      if(pState.getBlock() != pNewState.getBlock()){
         BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
         if(blockEntity instanceof HupwardserBE){
            ((HupwardserBE) blockEntity).drops();
         }
      }
      super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
   }

   //just, like, creates the new block entity when a new blockstate is created
   @Nullable
   @Override
   public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
      return new HupwardserBE(pPos, pState);
   }

   //ticks the block entity?
   @Nullable
   @Override
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
      return createTickerHelper(pBlockEntityType, ModBlockEntities.HUPWARDSER_BLOCK_ENTITY.get(), HupwardserBE::tick);
   }
}
