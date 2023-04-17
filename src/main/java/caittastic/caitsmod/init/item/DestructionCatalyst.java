package caittastic.caitsmod.init.item;

import caittastic.caitsmod.init.ModRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

//block valid to break check is fricked up
public class DestructionCatalyst extends Item{
  public DestructionCatalyst(Properties properties){
    super(properties);
  }

  @Override
  public boolean isDamageable(ItemStack stack){
    return true;
  }

  @Override
  public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced){
    pTooltipComponents.add(Component.translatable("tooltip.caitsmod.DestructionCatalyst.tooltip"));
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand pUsedHand){
    //variables!
    ItemStack stack = player.getItemInHand(pUsedHand);
    BlockPos pos = player.blockPosition();
    boolean shiftKeyDown = player.isShiftKeyDown();
    boolean isServerSide = !world.isClientSide;
    //if world is server side, and item does not have tag, give it a tag
    //if the player is crouching, check the charge value
    //on the server, cycle the charge value updwards, and print the charge in chat
    //on server and client, play the charging sound effect
    if(shiftKeyDown){
      switch(getCharge(stack)){
        case 1:
          if(isServerSide){
            setCharge(stack, 4);
            player.sendSystemMessage(Component.translatable("Charge: " + getCharge(stack)));
          }
          world.playSound(player, pos, ModRegistries.ITEM_CHARGE.get(), SoundSource.PLAYERS, 0.25F, 1);
          break;
        case 4:
          if(isServerSide){
            setCharge(stack, 9);
            player.sendSystemMessage(Component.translatable("Charge: " + getCharge(stack)));
            //player.sendMessage(new TextComponent("Charge: "+ getCharge(stack)), Util.NIL_UUID);
          }
          world.playSound(player, pos, ModRegistries.ITEM_CHARGE.get(), SoundSource.PLAYERS, 0.25F, 1.125f);
          break;
        case 9:
          if(isServerSide){
            setCharge(stack, 16);
            player.sendSystemMessage(Component.translatable("Charge: " + getCharge(stack)));
            //player.sendMessage(new TextComponent("Charge: " + getCharge(stack)), Util.NIL_UUID);
          }
          world.playSound(player, pos, ModRegistries.ITEM_CHARGE.get(), SoundSource.PLAYERS, 0.25F, 1.25f);
          break;
        case 16:
          if(isServerSide){
            setCharge(stack, 1);
            player.sendSystemMessage(Component.translatable("Charge: " + getCharge(stack)));
            //player.sendMessage(new TextComponent("Charge: " + getCharge(stack)), Util.NIL_UUID);
          }
          world.playSound(player, pos, ModRegistries.ITEM_CHARGE.get(), SoundSource.PLAYERS, 0.25F, 1.375f);
          break;
      }
    }
    return super.use(world, player, pUsedHand);
  }

  @Override
  public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context){
    //variables
    Level world = context.getLevel();
    context.getPlayer().getDirection();
    //if the player is not crouching
    //preform the super break on the server side
    //play the breaking sound effect on client and server
    if(!context.getPlayer().isShiftKeyDown()){
      if(!world.isClientSide){
        superBreaker(context, getCharge(stack), stack);

      }
      world.playSound(context.getPlayer(), context.getClickedPos(), ModRegistries.DESTRUCTION_CATALYST_USE.get(), SoundSource.BLOCKS, 1.0F, 1);
    }
    return super.onItemUseFirst(stack, context);
  }

  //  helpful :)
  private int getCharge(ItemStack stack){ //gets the value of charge tag
    ensureCharge(stack); //ennsures that the charge tag exists
    return stack.getTag().getInt("charge"); //returns the value set in the charge tag
  }

  private void setCharge(ItemStack stack, int value){ //changes the value in the charge tag
    ensureCharge(stack); //ensures that the charge tag exists
    stack.getTag().putInt("charge", value); //sets the value 'value' into the charge tag
  }

  private void ensureCharge(ItemStack stack){ //ensures that the charge tag exists
    if(!stack.hasTag()){ //does the not stack have any tag
      stack.setTag(new CompoundTag()); //make a new tag
    }
    assert stack.getTag() != null;
    if(!stack.getTag().contains("charge")){
      stack.getTag().putInt("charge", 1); //put the int 1 with the key "charge" into the tag
      stack.setTag(stack.getTag()); //puts the tag onto the item
    }
  }

  private void superBreaker(UseOnContext context, int charge, ItemStack stack){
    //variables
    Level world = context.getLevel();
    BlockPos blockPos = context.getClickedPos();
    BlockPos newBlockPos;
    int[] iterateWidthHeight = {-1, 0, 1};
    Direction clickedFace = context.getClickedFace();
    List<ItemStack> drops = new LinkedList<>();
    //goes through a 3 x 3 x charge depth loop
    for(int depth = 0; depth <= (charge - 1); depth++){
      for(int width: iterateWidthHeight){
        for(int height: iterateWidthHeight){

          newBlockPos = offsetBlock(blockPos, clickedFace, width, height, depth); //finds a block to break
          drops.addAll(breakAndAddLoTooList(newBlockPos, context, stack)); // breaks the block at the location and adds them to a list of loot


        }

      }
      stack.hurtAndBreak(1, context.getPlayer(), (player) -> {
        player.broadcastBreakEvent(EquipmentSlot.MAINHAND);
      });
    }


    compressStacksAndGiveToPlayer(drops, world, blockPos); //gives the list of loot to the player
  }

  private BlockPos offsetBlock(BlockPos blockToOffset, Direction clickedDirection, int width, int height, int depth){
    int negativeDepth = depth * -1;
    BlockPos newBlockPos = blockToOffset;
    switch(clickedDirection){
      case SOUTH:
        newBlockPos = blockToOffset.offset(width, height, negativeDepth);
        break;
      case NORTH:
        newBlockPos = blockToOffset.offset(width, height, depth);
        break;
      case EAST:
        newBlockPos = blockToOffset.offset(negativeDepth, width, height);
        break;
      case WEST:
        newBlockPos = blockToOffset.offset(depth, width, height);
        break;
      case UP:
        newBlockPos = blockToOffset.offset(width, negativeDepth, height);
        break;
      case DOWN:
        newBlockPos = blockToOffset.offset(width, depth, height);
        break;
    }
    return newBlockPos;
  }

  private boolean blockValidToBreak(BlockPos clickedBlockPos, UseOnContext context, Level world){
    ServerPlayer player = (ServerPlayer)context.getPlayer();
    BlockState clickedBlock = world.getBlockState(clickedBlockPos);
    boolean b = clickedBlock.getDestroySpeed(world, context.getClickedPos()) > 0f;
    boolean b1 = clickedBlock.getDestroySpeed(world, context.getClickedPos()) <= 50;
    return b && b1;
  }


  private boolean isUnknownBreakableCheck(BlockPos clickedBlockPos, ServerPlayer player){
    return ForgeHooks.onBlockBreakEvent(player.getCommandSenderWorld(), player.gameMode.getGameModeForPlayer(), player, clickedBlockPos) != -1;
  }

  private boolean isBlockProtected(Player player, BlockPos pos){
    if(ServerLifecycleHooks.getCurrentServer().isUnderSpawnProtection((ServerLevel)player.getCommandSenderWorld(), pos, player)){
      return true;
    }
    return Arrays.stream(Direction.values()).allMatch(e -> player.mayUseItemAt(pos, e, ItemStack.EMPTY));
  }

  private List<ItemStack> breakAndAddLoTooList(BlockPos bopo, UseOnContext context, ItemStack stack){
    List<ItemStack> drops = new LinkedList<>();
    Level world = context.getLevel();
    BlockState newClickedBlock = world.getBlockState(bopo);

    if(blockValidToBreak(bopo, context, world)){
      world.destroyBlock(bopo, false);
      List<ItemStack> dropList = Block.getDrops(newClickedBlock, (ServerLevel)world, bopo, world.getBlockEntity(bopo), context.getPlayer(), context.getItemInHand());

      return dropList;
    }
    return drops;
  }

  private void compressStacksAndGiveToPlayer(List<ItemStack> drops, Level world, BlockPos bloPo){
    for(int i = 0; i < drops.size(); i++){
      ItemStack stackOne = drops.get(i);
      if(!stackOne.isEmpty()){
        for(int j = i + 1; j < drops.size(); j++){
          ItemStack stackTwo = drops.get(j);
          if(ItemHandlerHelper.canItemStacksStack(stackOne, stackTwo)){
            stackOne.grow(stackTwo.getCount());
            drops.set(j, ItemStack.EMPTY);
          }
        }
      }
    }
    drops.removeIf(ItemStack::isEmpty);
    int itemCreateX = bloPo.getX();
    int itemCreateY = bloPo.getY();
    int itemCreateZ = bloPo.getZ();
    for(ItemStack item: drops){
      world.addFreshEntity(new ItemEntity(world, itemCreateX, itemCreateY, itemCreateZ, item));
    }
  }
}



