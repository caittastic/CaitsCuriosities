package caittastic.caitsmod.Item;

import caittastic.caitsmod.Block.ModBlocks;
import caittastic.caitsmod.BlockEntity.BrainBE;
import caittastic.caitsmod.BlockEntity.NodeBE;
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
    BlockPos clickedPos = context.getClickedPos();
    Player player = context.getPlayer();
    CompoundTag nbt = getTag(stack);

    if(level.getBlockState(clickedPos).is(ModBlocks.NODE.get())){
      if(level.getBlockEntity(clickedPos) instanceof NodeBE nodeBE){
        if(nodeBE.hasLinkedBrain()){
          if(player.isCrouching()){
            nodeBE.removeBrain();
            level.playSound(player, clickedPos, ModSoundEvents.POOF.get(), SoundSource.PLAYERS, 0.5f, 0.25f + level.random.nextFloat());
            player.displayClientMessage(Component.translatable("tuning_wand.attunement_removed"), true);
          } else{
            level.playSound(player, clickedPos, ModSoundEvents.ITEM_TUNE.get(), SoundSource.PLAYERS, 0.5f, 0.25f + level.random.nextFloat());
            player.displayClientMessage(Component.translatable("tuning_wand.attunement_failed"), true);
          }
        } else{
          level.playSound(player, clickedPos, ModSoundEvents.ITEM_TUNE.get(), SoundSource.PLAYERS, 0.5f, 0.5f + level.random.nextFloat());
          player.displayClientMessage(Component.translatable("tuning_wand.attuned"), true);
          nbt.putBoolean("is_attuned", true);
          nbt.putInt("x", clickedPos.getX());
          nbt.putInt("y", clickedPos.getY());
          nbt.putInt("z", clickedPos.getZ());
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
      }

    } else if(nbt.getBoolean("is_attuned") && level.getBlockState(clickedPos).is(ModBlocks.BRAIN_JAR.get())){
      if(level.getBlockEntity(clickedPos) instanceof BrainBE brainBE){
        //display the attunement message
        player.displayClientMessage(Component.translatable("tuning_wand.unattuned"), true);
        //tell the item that it isnt storing an attunement
        nbt.putBoolean("is_attuned", false);
        //play the attuning sound
        level.playSound(player, clickedPos, SoundEvents.BONE_BLOCK_BREAK, SoundSource.PLAYERS, 0.5f, 1f);
        //put stored pos into the brain block
        BlockPos storedPos = new BlockPos(nbt.getInt("x"), nbt.getInt("y"), nbt.getInt("z"));
        brainBE.addPos(storedPos);
        if(level.getBlockEntity(storedPos) instanceof NodeBE nodeBE)
          nodeBE.linkBrain(storedPos);
      }
      return InteractionResult.sidedSuccess(level.isClientSide);
    }
    return InteractionResult.CONSUME;
  }

  @Nullable
  private CompoundTag getTag(ItemStack stack){
    if(stack.getTag() == null)
      stack.setTag(new CompoundTag());
    return stack.getTag();
  }
}
