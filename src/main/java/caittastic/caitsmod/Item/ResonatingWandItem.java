package caittastic.caitsmod.Item;

import caittastic.caitsmod.Block.ModBlocks;
import caittastic.caitsmod.BlockEntity.BrainBE;
import caittastic.caitsmod.ModSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ResonatingWandItem extends Item{
  public ResonatingWandItem(Properties pProperties){
    super(pProperties);
  }

  @Override
  public void appendHoverText(ItemStack stack, @Nullable Level pLevel, List<Component> tooltip, TooltipFlag pIsAdvanced){
    CompoundTag nbt = getTag(stack);
    if(nbt.getBoolean("is_attuned")){
      MutableComponent component = Component.translatable("tooltip.attuned")
              .append(" x:").append(Integer.toString(nbt.getInt("x")))
              .append(" y:").append(Integer.toString(nbt.getInt("y")))
              .append(" z:").append(Integer.toString(nbt.getInt("z")));
      tooltip.add(component.withStyle(ChatFormatting.ITALIC, ChatFormatting.DARK_GRAY));
    }
    super.appendHoverText(stack, pLevel, tooltip, pIsAdvanced);
  }

  @Override
  public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context){
    Level level = context.getLevel();
    BlockPos pos = context.getClickedPos();
    Player player = context.getPlayer();
    CompoundTag nbt = getTag(stack);

    if(level.getBlockState(pos).is(ModBlocks.NODE.get())){
      level.playSound(player, pos, ModSoundEvents.ITEM_TUNE.get(), SoundSource.PLAYERS, 0.5f, 0.5f + level.random.nextFloat());
      attuneToPosition(pos, player, nbt);
      return InteractionResult.sidedSuccess(level.isClientSide);
    } else if(nbt.getBoolean("is_attuned") && level.getBlockState(pos).is(ModBlocks.BRAIN_JAR.get())){
      level.playSound(player, pos, SoundEvents.BONE_BLOCK_BREAK, SoundSource.PLAYERS, 0.5f, 1f);
      depositPosition(level, pos, player, nbt);
      return InteractionResult.sidedSuccess(level.isClientSide);
    }
    return InteractionResult.CONSUME;
  }

  private void depositPosition(Level level, BlockPos pos, Player player, CompoundTag nbt){
    player.displayClientMessage(Component.translatable("tuning_wand.unattuned"), true);
    nbt.putBoolean("is_attuned", false);
    BlockPos storedPos = new BlockPos(nbt.getInt("x"), nbt.getInt("y"), nbt.getInt("z"));
    if(level.getBlockEntity(pos) instanceof BrainBE entity)
      entity.addPos(storedPos);
  }

  private void attuneToPosition(BlockPos pos, Player player, CompoundTag nbt){
    nbt.putBoolean("is_attuned", true);
    player.displayClientMessage(Component.translatable("tuning_wand.attuned"), true);
    nbt.putInt("x", pos.getX());
    nbt.putInt("y", pos.getY());
    nbt.putInt("z", pos.getZ());
  }

  @Nullable
  private CompoundTag getTag(ItemStack stack){
    if(stack.getTag() == null)
      stack.setTag(new CompoundTag());
    return stack.getTag();
  }
}
