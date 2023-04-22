package caittastic.caitsmod.Item;

import caittastic.caitsmod.ModSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DestructionCatalyst extends Item{
  static String chargeTag = "charge";
  public DestructionCatalyst(Properties properties){super(properties);}

  public static int getCharge(ItemStack stack) {
    int charge = 0;
    switch(stack.getTag().getInt(chargeTag)){
      case 1 -> charge = 1;
      case 4 -> charge = 4;
      case 9 -> charge = 9;
      case 16 -> charge = 16;
    }
    return charge;
  }



  @Override
  public void appendHoverText(ItemStack stack, @Nullable Level pLevel, List<Component> tooltip, TooltipFlag pIsAdvanced){
    switch(stack.getTag().getInt(chargeTag)){
      case 1 -> tooltip.add(Component.literal("\uD83D\uDD25").withStyle(ChatFormatting.ITALIC, ChatFormatting.DARK_RED));
      case 4 -> tooltip.add(Component.literal("\uD83D\uDD25\uD83D\uDD25").withStyle(ChatFormatting.ITALIC, ChatFormatting.RED));
      case 9 -> tooltip.add(Component.literal("\uD83D\uDD25\uD83D\uDD25\uD83D\uDD25").withStyle(ChatFormatting.ITALIC, ChatFormatting.GOLD));
      case 16 -> tooltip.add(Component.literal("\uD83D\uDD25\uD83D\uDD25\uD83D\uDD25\uD83D\uDD25").withStyle(ChatFormatting.ITALIC, ChatFormatting.YELLOW));
    }
    super.appendHoverText(stack, pLevel, tooltip, pIsAdvanced);
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand){
    ItemStack stack = player.getItemInHand(hand);
    if(!level.isClientSide() && player.isCrouching()){
      switch(stack.getTag().getInt(chargeTag)){
        case 1 -> {
          stack.getTag().putInt(chargeTag, 4);
          level.playSound(null, player.blockPosition(), ModSoundEvents.ITEM_CHARGE.get(), SoundSource.PLAYERS, 0.25F, 1.125f);
        }
        case 4 -> {
          stack.getTag().putInt(chargeTag, 9);
          level.playSound(null, player.blockPosition(), ModSoundEvents.ITEM_CHARGE.get(), SoundSource.PLAYERS, 0.25F, 1.5f);
        }
        case 9 -> {
          stack.getTag().putInt(chargeTag, 16);
          level.playSound(null, player.blockPosition(), ModSoundEvents.ITEM_CHARGE.get(), SoundSource.PLAYERS, 0.25F, 1.625f);
        }
        default -> {
          stack.getTag().putInt(chargeTag, 1);
          level.playSound(null, player.blockPosition(), ModSoundEvents.ITEM_CHARGE.get(), SoundSource.PLAYERS, 0.25F, 1);
        }
      }
    }
    return super.use(level, player, hand);
  }

  @Override
  public void inventoryTick(ItemStack stack, Level level, Entity entity, int pSlotId, boolean pIsSelected){
    CompoundTag nbt = stack.getTag();
    BlockPos startPos = new BlockPos(nbt.getDouble("x"), nbt.getDouble("y"), nbt.getDouble("z"));
    if(nbt.getBoolean("mining")){
      level.addParticle(ParticleTypes.POOF,
              startPos.getX() + level.random.nextDouble(), startPos.getY() + level.random.nextDouble(), startPos.getZ() + level.random.nextDouble(),
              0, 0, 0);
    }

    if(!level.isClientSide && nbt.getBoolean("mining") && nbt.getInt("cooldown") <= 0){
      nbt.putInt("cooldown", 4);
      int depth = nbt.getInt("depth");
      nbt.putInt("depth", depth + 1);


      List<ItemStack> miningDrops = new LinkedList<>();

      for(int i = -1; i <= 1; i++){
        for(int j = -1; j <= 1; j++){

          int[] offsets = {0, 0, 0}; //xyz
          switch(decodeDirectionFromString(nbt.getString("direction"))){
            case SOUTH -> offsets = new int[]{i, j, depth};
            case NORTH -> offsets = new int[]{i, j, -depth};
            case EAST -> offsets = new int[]{depth, i, j};
            case WEST -> offsets = new int[]{-depth, i, j};
            case UP -> offsets = new int[]{i, depth, j};
            case DOWN -> offsets = new int[]{i, -depth, j};
          }
          BlockPos targetPos = startPos.offset(offsets[0], offsets[1], offsets[2]);

          if (isBlockValidToBreak( targetPos,  level, (Player)entity)){
          miningDrops.addAll(Block.getDrops(level.getBlockState(targetPos), (ServerLevel)level, targetPos, level.getBlockEntity(targetPos), entity, stack));
          level.destroyBlock(targetPos, false);
          }
        }
      }

      //makes a loop that goes over every item in the list, and drops them in the position
      for(ItemStack itemToSpawn: miningDrops){
        level.addFreshEntity(new ItemEntity(level,
                startPos.getX() + 0.5f,
                startPos.getY() + 0.5f,
                startPos.getZ() + 0.5f,
                itemToSpawn));
      }


      if(depth + 1 >= nbt.getInt(chargeTag)){
        nbt.putInt("depth", 0);
        nbt.putBoolean("mining", false);
      }
    }
    nbt.putInt("cooldown", Math.max(nbt.getInt("cooldown") - 1, 0));
    super.inventoryTick(stack, level, entity, pSlotId, pIsSelected);
  }

  @Override
  public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context){
    CompoundTag nbt = stack.getTag();

    if(!context.getPlayer().isShiftKeyDown() && !nbt.getBoolean("mining")){ //if the player is not shifting, play break sound
      context.getLevel().playSound(context.getPlayer(), context.getClickedPos(), ModSoundEvents.ITEM_USE.get(), SoundSource.BLOCKS, 1.0F, 1);
    }

    if(!context.getLevel().isClientSide() && !context.getPlayer().isCrouching() && !nbt.getBoolean("mining")){

      nbt.putString("direction", encodeDirectionAsString(context.getClickedFace().getOpposite()));
      nbt.putDouble("x", context.getClickedPos().getX());
      nbt.putDouble("y", context.getClickedPos().getY());
      nbt.putDouble("z", context.getClickedPos().getZ());
      nbt.putBoolean("mining", true);
    }
    return super.onItemUseFirst(stack, context);
  }

  //encodes direction into a string
  private String encodeDirectionAsString(Direction direction){
    switch(direction){
      case SOUTH -> {return "south";}
      case NORTH -> {return "north";}
      case EAST -> {return "east";}
      case WEST -> {return "west";}
      case UP -> {return "up";}
      case DOWN -> {return "down";}
    }
    return null;
  }

  //decodes direction from a string
  private Direction decodeDirectionFromString(String string){
    switch(string){
      case "south" -> {return Direction.SOUTH;}
      case "north" -> {return Direction.NORTH;}
      case "east" -> {return Direction.EAST;}
      case "west" -> {return Direction.WEST;}
      case "up" -> {return Direction.UP;}
      case "down" -> {return Direction.DOWN;}
    }
    return null;
  }

  //is a block valid to break
  private boolean isBlockValidToBreak(BlockPos blockToCheck, Level level, Player playerToBreakBlock) {
    float destroySpeedOfCheckedBlock = level.getBlockState(blockToCheck).getDestroySpeed(level, blockToCheck);
    ServerPlayer serverPlayerToBreak = (ServerPlayer) playerToBreakBlock;
    boolean underProtection = false;
    if (!ServerLifecycleHooks.getCurrentServer().isUnderSpawnProtection((ServerLevel)playerToBreakBlock.getCommandSenderWorld(), blockToCheck, playerToBreakBlock)){
      underProtection = Arrays.stream(Direction.values()).allMatch(e -> playerToBreakBlock.mayUseItemAt(blockToCheck, e, ItemStack.EMPTY));
    }
    return destroySpeedOfCheckedBlock > 0f &&
            destroySpeedOfCheckedBlock <= 50 &&
            underProtection &&
            ForgeHooks.onBlockBreakEvent(serverPlayerToBreak.getCommandSenderWorld(), serverPlayerToBreak.gameMode.getGameModeForPlayer(), serverPlayerToBreak, blockToCheck) != -1;
  }

}



