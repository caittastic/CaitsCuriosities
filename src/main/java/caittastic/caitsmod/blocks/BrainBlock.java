package caittastic.caitsmod.blocks;

import caittastic.caitsmod.blockentities.BrainBE;
import caittastic.caitsmod.blockentities.ModBlockEntities;
import caittastic.caitsmod.blockentities.NodeBE;
import caittastic.caitsmod.networking.BrainRemovePayload;
import caittastic.caitsmod.particles.ModParticles;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import static java.lang.Math.sqrt;

public class BrainBlock extends BaseEntityBlock {
  ArrayList<BlockPos> targetPositions = new ArrayList<>();

  public BrainBlock(Properties pProperties){
    super(pProperties);
  }

  @Override
  protected MapCodec<? extends BaseEntityBlock> codec() {
    return simpleCodec(BrainBlock::new);
  }

  @Override
  public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean pIsMoving){
    if(level.getBlockEntity(pos) instanceof BrainBE brainBE){
      for(BlockPos nodePos: brainBE.getNodePositions())
        if(level.getBlockEntity(nodePos) instanceof NodeBE nodeBE){
          nodeBE.removeBrain();
          PacketDistributor.sendToAllPlayers(new BrainRemovePayload(nodePos));
        }
    }
    super.onRemove(state, level, pos, newState, pIsMoving);
  }

  @Override
  protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level level, BlockPos pos, Player player, InteractionHand pHand, BlockHitResult pHitResult) {
    if(player.getItemInHand(pHand).isEmpty()){
      if(level.getBlockEntity(pos) instanceof BrainBE entity){
        if(level.isClientSide()){
          player.playSound(SoundEvents.BONE_BLOCK_PLACE, 0.5f, 0.5f);
          player.playSound(SoundEvents.ZOMBIE_AMBIENT, 0.5f, 1.5f + level.random.nextFloat());
        } else{
          this.targetPositions.addAll(entity.getNodePositions());
          player.sendSystemMessage(Component.literal("" + entity.getNodePositions()));
        }
        return ItemInteractionResult.SUCCESS;
      }
    }
    return super.useItemOn(pStack, pState, level, pos, player, pHand, pHitResult);
  }

  @Override
  public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext){
    return Block.box(3, 0, 3, 13, 14, 13);
  }

  @Override
  public void animateTick(BlockState pState, Level level, BlockPos ourPos, RandomSource pRandom){
    double ourX = ourPos.getX() + pRandom.nextDouble();
    double ourY = ourPos.getY() + pRandom.nextDouble();
    double ourZ = ourPos.getZ() + pRandom.nextDouble();


    //portal ambient
    level.addParticle(ParticleTypes.PORTAL,
            ourX,
            ourY,
            ourZ,
            (pRandom.nextDouble() - 0.5D) * 0.5D,
            (pRandom.nextDouble() - 0.5D) * 0.5D,
            (pRandom.nextDouble() - 0.5D) * 0.5D);

    //node locator beam
    ourX = ourPos.getX() + 0.5f;
    ourY = ourPos.getY() + 0.5f;
    ourZ = ourPos.getZ() + 0.5f;
    double speed = 0.2d;

    if(!targetPositions.isEmpty()){
      for(int i = 0; i < targetPositions.size(); i++){
        BlockPos targetPos = targetPositions.get(i);

        double xDif = ourX - (targetPos.getX() + 0.5d);
        double yDif = ourY - (targetPos.getY() + 0.5d);
        double zDif = ourZ - (targetPos.getZ() + 0.5d);

        double hypo = sqrt(xDif * xDif + yDif * yDif + zDif * zDif);

        level.addParticle(ModParticles.LOCATOR_PARTICLE.get(),
                ourX,
                ourY,
                ourZ,
                -((xDif / hypo) * speed),
                -((yDif / hypo) * speed),
                -((zDif / hypo) * speed));

        targetPositions.remove(i);
      }
    }

    super.animateTick(pState, level, ourPos, pRandom);
  }

  @Override
  public RenderShape getRenderShape(BlockState pState){
    return RenderShape.MODEL;
  }

  @Override
  public boolean propagatesSkylightDown(BlockState pState, BlockGetter pReader, BlockPos pPos){
    return true;
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState){
    return new BrainBE(pPos, pState);
  }

  @Nullable
  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState pState, BlockEntityType<T> pBlockEntityType){
    return createTickerHelper(pBlockEntityType, ModBlockEntities.BRAIN.get(), BrainBE::tick);
  }
}
