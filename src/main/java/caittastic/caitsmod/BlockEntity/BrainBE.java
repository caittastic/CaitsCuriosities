package caittastic.caitsmod.BlockEntity;

import caittastic.caitsmod.Block.ModBlocks;
import caittastic.caitsmod.Block.NodeBlock;
import caittastic.caitsmod.Block.NodeType;
import caittastic.caitsmod.NodeNetworkUtils.Node;
import caittastic.caitsmod.particles.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BrainBE extends BlockEntity{
  private ArrayList<BlockPos> nodePositions = new ArrayList<>();

  private ArrayList<Node> pullNodes = new ArrayList<>();
  private ArrayList<Node> pushNodes = new ArrayList<>();
  private int cooldown;

  public BrainBE(BlockPos pPos, BlockState pBlockState){
    super(ModBlockEntities.BRAIN.get(), pPos, pBlockState);
  }

  public static void tick(Level level, BlockPos blockPos, BlockState state, BrainBE e){
    if(level.isClientSide)
      return;

    int maxDelay = 8;
    int movQty = 4;
    //only process every maxDelay ticks
    e.cooldown++;
    if(e.cooldown >= maxDelay - 1){
      e.cooldown = 0;

      //clear the node lists
      e.pullNodes.clear();
      e.pushNodes.clear();

      /* remove empty nodes from the node list, and build the pull and push node lists */
      //iterate for each node position
      for(BlockPos nodePos: e.nodePositions){
        //if a position isnt a node, remove it. else add it to its matching list
        BlockState nodeState = level.getBlockState(nodePos);
        if(!nodeState.is(ModBlocks.NODE.get())){
          e.nodePositions.remove(nodePos);
        } else{
          Direction interactDir = nodeState.getValue(NodeBlock.ATTACHED_FACE);
          BlockEntity interactBE = level.getBlockEntity(nodePos.relative(interactDir.getOpposite()));
          if(interactBE != null){
            IItemHandler interactHandler = interactBE.getCapability(ForgeCapabilities.ITEM_HANDLER, interactDir).orElse(null);
            if(interactHandler != null){
              NodeType nodeType = nodeState.getValue(NodeBlock.TYPE);
              if(nodeType == NodeType.PUSH){
                e.pushNodes.add(new Node(nodePos, interactHandler));
              } else if(nodeType == NodeType.PULL){
                e.pullNodes.add(new Node(nodePos, interactHandler));
              }
            }
          }
        }
      }

      /* iterate through every pull handler, and try to insert into each push node */

      //list of positions to spawn a movement poof
      ArrayList<BlockPos> pullPoofPositions = new ArrayList<>();
      ArrayList<BlockPos> pushPoofPositions = new ArrayList<>();

      //if the pull nodes list isn't empty and push nodes list isn't empty, do movement
      if(!e.pullNodes.isEmpty() && !e.pushNodes.isEmpty()){
        //find all the itemHandlers that can be pulled from
        for(Node pullNode: e.pullNodes){
          boolean pulledFrom = false;
          IItemHandler pullHandler = pullNode.connectedItemHandler;
          IItemHandler pushHandler = e.pushNodes.get(0).connectedItemHandler;
          BlockPos pullPosition = pullNode.nodeBlockPosition;
          BlockPos pushPosition = e.pushNodes.get(0).nodeBlockPosition;

          //for each slot in the pull itemHandler
          for(int pullSlot = pullHandler.getSlots() - 1; pullSlot >= 0; pullSlot--){
            if(pulledFrom) break; //if things have already been pulled this attempt, stop trying to pull
            //for each slot in the push itemHandler
            for(int pushSlot = 0; pushSlot < pushHandler.getSlots(); pushSlot++){
              ItemStack pulledStack = pullHandler.extractItem(pullSlot, movQty, true); //simulate extracting the target pulling stack
              ItemStack outputStack = pushHandler.getStackInSlot(pushSlot); //the stack things will be outputted to

              int outStackMaxSize = outputStack.getMaxStackSize(); //get the maximum stack size of the output stack
              int outStackSize = outputStack.getCount(); //get the current stack size of the output stack
              int outRemainingSize = outStackMaxSize - outStackSize; //get the remaining amount of items to fill up the output stack

              boolean outSlotFull = outStackSize == outStackMaxSize; //if the output slot is full
              boolean outAndPullMatch = pulledStack.getItem() == outputStack.getItem(); //if the output stack and the pulled stack are the same item

              //only try to move if the pulled stack can go into the output stack
              if(!pulledStack.isEmpty() && (outputStack.isEmpty() || outAndPullMatch && !outSlotFull)){
                int qtyToMove = Math.min(outRemainingSize, movQty); //the amount that should be pulled from the pull stack
                pulledStack = pullHandler.extractItem(pullSlot, qtyToMove, false); //actually extract from the stack

                ItemStack leftovers = pushHandler.insertItem(pushSlot, pulledStack.copy(), false); //insert into pull handler and get the leftover items
                pulledStack.setCount(leftovers.getCount()); //set the pulled stack count to the amount of left over items
                pulledFrom = true;
                pullPoofPositions.add(pullPosition);
                pushPoofPositions.add(pushPosition);
                break;
              }
            }
          }
          if(pulledFrom && !pullPoofPositions.isEmpty() && !pushPoofPositions.isEmpty())
            spawnPoofs(level, pullPoofPositions, pushPoofPositions);
        }
      }
    }
  }

  private static void spawnPoofs(Level level, ArrayList<BlockPos> pullPoofPositions, ArrayList<BlockPos> pushPoofPositions){
    //remove duplicates from the poof lists
    Set<BlockPos> pullSet = new HashSet<>(pullPoofPositions);
    pullPoofPositions.clear();
    pullPoofPositions.addAll(pullSet);

    Set<BlockPos> pushSet = new HashSet<>(pushPoofPositions);
    pushPoofPositions.clear();
    pushPoofPositions.addAll(pushSet);
    for(int i = 0; i < 5; ++i){
      for(BlockPos poofPos: pullPoofPositions)
        spawnPoof(level, poofPos);
      for(BlockPos poofPos: pushPoofPositions)
        spawnPoof(level, poofPos);
    }
  }

  private static void spawnPoof(Level level, BlockPos poofPos){
    ServerLevel serverLevel = (ServerLevel)level;
    serverLevel.sendParticles(
            ModParticles.TELEPORT_POOF_PARTICLE.get(),
            poofPos.getX() + 0.25 + (serverLevel.random.nextDouble() / 2),
            poofPos.getY() + 0.5,
            poofPos.getZ() + 0.25 + (serverLevel.random.nextDouble() / 2),
            1,
            0.0D,
            0.0D,
            0.0D,
            0.0D);
    //level.playSound(null, poofPos, SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.BLOCKS, 0.01f, 1);
  }

  public ArrayList getNodePositions(){
    removeBlanks();
    return this.nodePositions;
  }

  private void removeBlanks(){
    //remove all positions that dont have a node block at them
    for(int i = 0; i < this.nodePositions.size(); ++i){
      if(!level.getBlockState(this.nodePositions.get(i)).is(ModBlocks.NODE.get()))
        this.nodePositions.remove(i);
    }
  }

  public void addPos(BlockPos pos){
    nodePositions.add(pos);
  }

  @Override
  protected void saveAdditional(CompoundTag nbt){
    super.saveAdditional(nbt);
    ListTag nodesTag = new ListTag();
    for(int i = 0; i < this.nodePositions.size(); ++i){
      BlockPos nodePos = this.nodePositions.get(i);
      nodesTag.add(NbtUtils.writeBlockPos(nodePos));
    }
    nbt.put("nodes", nodesTag);
    nbt.putInt("delay", cooldown);
  }

  @Override
  public void load(CompoundTag nbt){
    super.load(nbt);
    this.nodePositions = new ArrayList<>();
    ListTag nodesTag = nbt.getList("nodes", 10);
    for(int i = 0; i < nodesTag.size(); ++i){
      CompoundTag posTag = nodesTag.getCompound(i);
      BlockPos pos = NbtUtils.readBlockPos(posTag);
      this.nodePositions.add(pos);
    }
    this.cooldown = nbt.getInt("delay");
  }
}
