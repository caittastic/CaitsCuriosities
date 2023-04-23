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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;

import java.util.HashSet;
import java.util.Set;

public class BrainBE extends BlockEntity{
  private Set<BlockPos> nodePositions = new HashSet<>();

  private Set<Node> pullNodes = new HashSet<>();
  private Set<Node> pushNodes = new HashSet<>();
  private int cooldown;

  public BrainBE(BlockPos pPos, BlockState pBlockState){
    super(ModBlockEntities.BRAIN.get(), pPos, pBlockState);
  }

  public static void tick(Level level, BlockPos blockPos, BlockState state, BrainBE e){
    if(level.isClientSide) return;

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
      e.removeBlanks();
      //iterate for each node position
      for(BlockPos nodePos: e.nodePositions){
        BlockState nodeState = level.getBlockState(nodePos);
        Direction interactDir = nodeState.getValue(NodeBlock.ATTACHED_FACE);
        BlockEntity interactBE = level.getBlockEntity(nodePos.relative(interactDir.getOpposite()));
        //if an itemHandler for a position exists, add it for its matching type list
        if(interactBE != null){
          IItemHandler interactHandler = interactBE.getCapability(ForgeCapabilities.ITEM_HANDLER, interactDir).orElse(null);
          if(interactHandler != null){
            NodeType nodeType = nodeState.getValue(NodeBlock.TYPE);
            if(nodeType == NodeType.PUSH)
              e.pushNodes.add(new Node(nodePos, interactHandler));
            else
              e.pullNodes.add(new Node(nodePos, interactHandler));
          }
        }
      }

      /* iterate through every pull handler, and try to insert into each push node */
      //if the pull nodes list isn't empty and push nodes list isn't empty, do movement
      if(!e.pullNodes.isEmpty() && !e.pushNodes.isEmpty()){
        //if the network has pushed on this attempt
        boolean hasPushed = false;

        //list of positions to spawn a movement poof
        Set<BlockPos> pullPoofPositions = new HashSet<>();
        Set<BlockPos> pushPoofPositions = new HashSet<>();

        //find all the itemHandlers that can be pulled from
        for(Node pullNode: e.pullNodes){
          boolean pulledFrom = false;
          IItemHandler pullHandler = pullNode.connectedItemHandler;
          BlockPos pullPosition = pullNode.nodeBlockPosition;

          //for each slot in the pull itemHandler
          for(int pullSlot = pullHandler.getSlots() - 1; pullSlot >= 0; pullSlot--){
            if(pulledFrom) break; //if things have already been pulled this attempt, stop trying to pull
            //if havent successfully pushed this attempt
            if(!hasPushed){
              //for each push node
              for(Node pushNode: e.pushNodes){
                IItemHandler pushHandler = pushNode.connectedItemHandler;
                BlockPos pushPosition = pushNode.nodeBlockPosition;
                //if have successfulyl pushed, break
                if(hasPushed) break;
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
                    hasPushed = true;
                    pullPoofPositions.add(pullPosition);
                    pushPoofPositions.add(pushPosition);
                    break;
                  }
                }
              }
            }
          }
          if(pulledFrom && !pullPoofPositions.isEmpty() && !pushPoofPositions.isEmpty()){
            for(int i = 0; i < 5; ++i){
              makePoofs((ServerLevel)level, pullPoofPositions);
              makePoofs((ServerLevel)level, pushPoofPositions);
            }
          }
        }
      }
    }
  }

  private static void makePoofs(ServerLevel level, Set<BlockPos> pullPoofPositions){
    for(BlockPos poofPos: pullPoofPositions){
      ServerLevel serverLevel = level;
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
    }
  }

  public Set<BlockPos> getNodePositions(){
    removeBlanks();
    return this.nodePositions;
  }

  private void removeBlanks(){
    this.nodePositions.removeIf(pos -> !level.getBlockState(pos).is(ModBlocks.NODE.get()));
  }

  public void addPos(BlockPos pos){
    nodePositions.add(pos);
  }

  @Override
  protected void saveAdditional(CompoundTag nbt){
    super.saveAdditional(nbt);
    ListTag nodesTag = new ListTag();
    for(BlockPos nodePos: this.nodePositions)
      nodesTag.add(NbtUtils.writeBlockPos(nodePos));
    nbt.put("nodes", nodesTag);
    nbt.putInt("delay", cooldown);
  }

  @Override
  public void load(CompoundTag nbt){
    super.load(nbt);
    this.nodePositions = new HashSet<>();
    ListTag nodesTag = nbt.getList("nodes", 10);
    for(int i = 0; i < nodesTag.size(); ++i){
      CompoundTag posTag = nodesTag.getCompound(i);
      BlockPos pos = NbtUtils.readBlockPos(posTag);
      this.nodePositions.add(pos);
    }
    this.cooldown = nbt.getInt("delay");
  }
}
