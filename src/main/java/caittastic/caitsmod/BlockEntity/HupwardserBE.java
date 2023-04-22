package caittastic.caitsmod.BlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class HupwardserBE extends BlockEntity{
  public static final int SLOT_COUNT = 1; //the amount of slots in our inventory, count starts at 1
  static final String INVENTORY_KEY = "inventory";
  //the size is for every number of slots in the inventory
  private final ItemStackHandler itemHandler = new ItemStackHandler(SLOT_COUNT){
    @Override
    protected void onContentsChanged(int slot){
      setChanged();
    }
  };
  private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty(); //no clue what this does

  //constructor
  public HupwardserBE(BlockPos blockPos, BlockState blockState){
    super(ModBlockEntities.HUPWARDSER_BLOCK_ENTITY.get(), blockPos, blockState);
  }

  //code that gets ran every tick, the main logic for our block entity
  public static void tick(Level level, BlockPos pos, BlockState state, HupwardserBE blockEntity){
    int movQty = 4;

    //find directions the block entities will be in
    Direction inpDir = Direction.DOWN;
    Direction outDir = Direction.UP;

    //find block entities
    BlockEntity inpBE = level.getBlockEntity(pos.relative(inpDir));
    BlockEntity outBE = level.getBlockEntity(pos.relative(outDir));
    if((inpBE == null) || (outBE == null))
      return;

    //find itemhandlers
    IItemHandler inpHandler = inpBE.getCapability(
            ForgeCapabilities.ITEM_HANDLER, inpDir.getOpposite()).orElse(null);
    IItemHandler outHandler = outBE.getCapability(
            ForgeCapabilities.ITEM_HANDLER, outDir.getOpposite()).orElse(null);
    if((inpHandler == null) || (outHandler == null))
      return;

    boolean moved = false;
    for(int inpSLotID = inpHandler.getSlots()-1; inpSLotID >= 0; inpSLotID--){
      ItemStack pulledStack = inpHandler.extractItem(inpSLotID, movQty, true);
      if(!pulledStack.isEmpty()){
        for(int outSlotID = 0; outSlotID < outHandler.getSlots(); outSlotID++){
          ItemStack outStack = outHandler.getStackInSlot(outSlotID);
          int outStackCap = outStack.getMaxStackSize();
          int outStackCount = outStack.getCount();
          boolean outSlotFull = outStackCount == outStackCap;
          boolean outAndPullMatch = pulledStack.getItem() == outStack.getItem();

          if((outStack.isEmpty() || outAndPullMatch) && !outSlotFull){
            //do movement
            pulledStack = inpHandler.extractItem(inpSLotID, Math.min(outStackCap - outStackCount, movQty), false);
            ItemStack leftovers = outHandler.insertItem(outSlotID, pulledStack.copy(), false);
            pulledStack.setCount(leftovers.getCount());
            if(pulledStack.isEmpty()){
              moved = true;
              break;
            }
          }
        }
      }
      if(moved)
        break;
    }
  }

  @Nonnull
  @Override //is something to do with helping other mods interact with my block
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side){
    if(cap == ForgeCapabilities.ITEM_HANDLER){
      return lazyItemHandler.cast();
    }
    return super.getCapability(cap, side);
  }

  @Override //idk what this does
  public void onLoad(){
    super.onLoad();
    lazyItemHandler = LazyOptional.of(() -> itemHandler);
  }

  @Override //idk what this does
  public void invalidateCaps(){
    super.invalidateCaps();
    lazyItemHandler.invalidate();
  }

  @Override //saves the inventory and crafting progress data to NBT on saving the game
  protected void saveAdditional(@NotNull CompoundTag tag){
    tag.put(INVENTORY_KEY, itemHandler.serializeNBT());
    super.saveAdditional(tag);
  }

  //loads the inventory and crafting progress data from NBT on saving the game
  @Override
  public void load(CompoundTag nbt){
    super.load(nbt);
    itemHandler.deserializeNBT(nbt.getCompound(INVENTORY_KEY));
  }

  public void drops(){
    SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots()); //creates a simplecontainer containing the amount of slots in our itemhandler
    for(int i = 0; i < itemHandler.getSlots(); i++){ //loops through every item in the itemhandler
      inventory.setItem(i, itemHandler.getStackInSlot(i)); //puts every item in the itemhandler into the simplecontainer
    }

    Containers.dropContents(this.level, this.worldPosition, inventory); //drops the contents of the simplecontainer
  }
}
