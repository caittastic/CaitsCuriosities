package strelka.gizmos.items;

import strelka.gizmos.blocks.ModBlocks;
import strelka.gizmos.blockentities.BrainBE;
import strelka.gizmos.blockentities.NodeBE;
import strelka.gizmos.ModSoundEvents;
import strelka.gizmos.data.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
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

import java.util.List;

public class ResonatingWandItem extends Item {
    public ResonatingWandItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> tooltip, TooltipFlag pTooltipFlag) {
        if (pStack.get(ModDataComponents.RESONATING_WAND).attuned()) {
            BlockPos blockPos = pStack.get(ModDataComponents.RESONATING_WAND).blockPos();
            MutableComponent component = Component.translatable("tooltip.attuned")
                    .append(" x:").append(Integer.toString(blockPos.getX()))
                    .append(" y:").append(Integer.toString(blockPos.getY()))
                    .append(" z:").append(Integer.toString(blockPos.getZ()));
            tooltip.add(component.withStyle(ChatFormatting.ITALIC, ChatFormatting.DARK_GRAY));
        }
        super.appendHoverText(pStack, pContext, tooltip, pTooltipFlag);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        Player player = context.getPlayer();

        if (level.getBlockState(clickedPos).is(ModBlocks.NODE.get())) {
            if (level.getBlockEntity(clickedPos) instanceof NodeBE nodeBE) {
                if (nodeBE.hasLinkedBrain()) {
                    if (player.isCrouching()) {
                        nodeBE.removeBrain();
                        level.playSound(player, clickedPos, ModSoundEvents.POOF.get(), SoundSource.PLAYERS, 0.5f, 0.25f + level.random.nextFloat());
                        player.displayClientMessage(Component.translatable("tuning_wand.attunement_removed"), true);
                    } else {
                        level.playSound(player, clickedPos, ModSoundEvents.ITEM_TUNE.get(), SoundSource.PLAYERS, 0.5f, 0.25f + level.random.nextFloat());
                        player.displayClientMessage(Component.translatable("tuning_wand.attunement_failed"), true);
                    }
                } else {
                    level.playSound(player, clickedPos, ModSoundEvents.ITEM_TUNE.get(), SoundSource.PLAYERS, 0.5f, 0.5f + level.random.nextFloat());
                    player.displayClientMessage(Component.translatable("tuning_wand.attuned"), true);

                    stack.set(ModDataComponents.RESONATING_WAND, stack.get(ModDataComponents.RESONATING_WAND)
                            .setAttuned(true));
                    stack.set(ModDataComponents.RESONATING_WAND, stack.get(ModDataComponents.RESONATING_WAND)
                            .setBlockPos(clickedPos));
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }

        } else if (stack.get(ModDataComponents.RESONATING_WAND).attuned() && level.getBlockState(clickedPos).is(ModBlocks.BRAIN_JAR.get())) {
            if (level.getBlockEntity(clickedPos) instanceof BrainBE brainBE) {
                //display the attunement message
                player.displayClientMessage(Component.translatable("tuning_wand.unattuned"), true);
                //tell the item that it isnt storing an attunement
                stack.set(ModDataComponents.RESONATING_WAND, stack.get(ModDataComponents.RESONATING_WAND)
                        .setAttuned(false));
                //play the attuning sound
                level.playSound(player, clickedPos, SoundEvents.BONE_BLOCK_BREAK, SoundSource.PLAYERS, 0.5f, 1f);
                //put stored pos into the brain block
                BlockPos storedPos = stack.get(ModDataComponents.RESONATING_WAND).blockPos();
                brainBE.addPos(storedPos);
                if (level.getBlockEntity(storedPos) instanceof NodeBE nodeBE)
                    nodeBE.linkBrain(storedPos);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.CONSUME;
    }
}
