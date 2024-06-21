package strelka.gizmos.blockentities;

import strelka.gizmos.blocks.ModBlocks;
import strelka.gizmos.blocks.NodeBlock;
import strelka.gizmos.blocks.NodeType;
import strelka.gizmos.nodes.Node;
import strelka.gizmos.particles.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.HashSet;
import java.util.Set;

public class BrainBE extends BlockEntity {
    private final Set<BlockPos> nodePositions;
    private final Set<Node> pullNodes;
    private final Set<Node> pushNodes;
    private int cooldown;

    public BrainBE(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.BRAIN.get(), pPos, pBlockState);
        this.pullNodes = new HashSet<>();
        this.pushNodes = new HashSet<>();
        this.nodePositions = new HashSet<>();
    }

    public static void tick(Level level, BlockPos blockPos, BlockState state, BrainBE brainBE) {
        if (level.isClientSide) return;

        int maxDelay = 8;
        int movQty = 4;
        //only process every maxDelay ticks
        brainBE.cooldown++;
        if (brainBE.cooldown >= maxDelay - 1) {
            brainBE.cooldown = 0;

            //clear the node lists
            brainBE.pullNodes.clear();
            brainBE.pushNodes.clear();

            /* remove empty nodes from the node list, and build the pull and push node lists */
            brainBE.removeBlanks();
            //iterate for each node position
            for (BlockPos nodePos : brainBE.nodePositions) {
                BlockState nodeState = level.getBlockState(nodePos);
                Direction interactDir = nodeState.getValue(NodeBlock.ATTACHED_FACE);
                BlockEntity interactBE = level.getBlockEntity(nodePos.relative(interactDir.getOpposite()));
                //if an itemHandler for a position exists, add it for its matching type list
                if (interactBE != null) {
                    IItemHandler interactHandler = level.getCapability(Capabilities.ItemHandler.BLOCK, nodePos.relative(interactDir.getOpposite()), interactDir);
                    if (interactHandler != null) {
                        NodeType nodeType = nodeState.getValue(NodeBlock.TYPE);
                        if (nodeType == NodeType.PUSH)
                            brainBE.pushNodes.add(new Node(nodePos, interactHandler));
                        else
                            brainBE.pullNodes.add(new Node(nodePos, interactHandler));
                    }
                }
            }

            /* iterate through every pull handler, and try to insert into each push node */
            //if the pull nodes list isn't empty and push nodes list isn't empty, do movement
            if (!brainBE.pullNodes.isEmpty() && !brainBE.pushNodes.isEmpty()) {
                //if the network has pushed on this attempt
                boolean hasPushed = false;

                //list of positions to spawn a movement poof
                Set<BlockPos> pullPoofPositions = new HashSet<>();
                Set<BlockPos> pushPoofPositions = new HashSet<>();

                //find all the itemHandlers that can be pulled from
                for (Node pullNode : brainBE.pullNodes) {
                    boolean pulledFrom = false;
                    IItemHandler pullHandler = pullNode.connectedItemHandler;
                    BlockPos pullPosition = pullNode.nodeBlockPosition;

                    //for each slot in the pull itemHandler
                    for (int pullSlot = pullHandler.getSlots() - 1; pullSlot >= 0; pullSlot--) {
                        if (pulledFrom) break; //if things have already been pulled this attempt, stop trying to pull
                        //if havent successfully pushed this attempt
                        if (!hasPushed) {
                            //for each push node
                            for (Node pushNode : brainBE.pushNodes) {
                                IItemHandler pushHandler = pushNode.connectedItemHandler;
                                BlockPos pushPosition = pushNode.nodeBlockPosition;
                                //if have successfulyl pushed, break
                                if (hasPushed) break;
                                //for each slot in the push itemHandler
                                for (int pushSlot = 0; pushSlot < pushHandler.getSlots(); pushSlot++) {
                                    ItemStack pulledStack = pullHandler.extractItem(pullSlot, movQty, true); //simulate extracting the target pulling stack
                                    ItemStack outputStack = pushHandler.getStackInSlot(pushSlot); //the stack things will be outputted to

                                    int outStackMaxSize = outputStack.getMaxStackSize(); //get the maximum stack size of the output stack
                                    int outStackSize = outputStack.getCount(); //get the current stack size of the output stack
                                    int outRemainingSize = outStackMaxSize - outStackSize; //get the remaining amount of items to fill up the output stack

                                    boolean outSlotFull = outStackSize == outStackMaxSize; //if the output slot is full
                                    boolean outAndPullMatch = pulledStack.getItem() == outputStack.getItem(); //if the output stack and the pulled stack are the same item

                                    //only try to move if the pulled stack can go into the output stack
                                    if (!pulledStack.isEmpty() && (outputStack.isEmpty() || outAndPullMatch && !outSlotFull)) {
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
                    if (pulledFrom && !pullPoofPositions.isEmpty() && !pushPoofPositions.isEmpty()) {
                        for (int i = 0; i < 5; ++i) {
                            makePoofs((ServerLevel) level, pullPoofPositions);
                            makePoofs((ServerLevel) level, pushPoofPositions);
                        }
                    }
                }
            }
        }
    }

    private static void makePoofs(ServerLevel level, Set<BlockPos> pullPoofPositions) {
        for (BlockPos poofPos : pullPoofPositions) {
            level.sendParticles(
                    ModParticles.TELEPORT_POOF_PARTICLE.get(),
                    poofPos.getX() + 0.25 + (level.random.nextDouble() / 2),
                    poofPos.getY() + 0.5,
                    poofPos.getZ() + 0.25 + (level.random.nextDouble() / 2),
                    1,
                    0.0D,
                    0.0D,
                    0.0D,
                    0.0D);
        }
    }

    public Set<BlockPos> getNodePositions() {
        removeBlanks();
        return this.nodePositions;
    }

    private void removeBlanks() {
        this.nodePositions.removeIf(pos -> !level.getBlockState(pos).is(ModBlocks.NODE.get()));
    }

    public void addPos(BlockPos pos) {
        nodePositions.add(pos);
    }


    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        CompoundTag nodesTag = new CompoundTag();
        int i = 0;
        for (BlockPos nodePos : this.nodePositions) {
            nodesTag.putLong(String.valueOf(i), nodePos.asLong());
            i++;
        }
        pTag.putInt("nodes_amount", nodePositions.size());
        pTag.put("nodes", nodesTag);
        pTag.putInt("delay", cooldown);
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider pRegistries) {
        super.loadAdditional(nbt, pRegistries);
        CompoundTag tag = nbt.getCompound("nodes");
        int length = nbt.getInt("nodes_amount");
        for (int i = 0; i < length; i++) {
            long posTag = tag.getLong(String.valueOf(i));
            this.nodePositions.add(BlockPos.of(posTag));
        }
        this.cooldown = nbt.getInt("delay");
    }
}
