package strelka.gizmos.items;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DowingRodItem extends Item {
    public DowingRodItem(Properties pProperties) {
        super(pProperties);
    }




    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if(pContext.getLevel().isClientSide) {
            boolean foundBlock = false;
            BlockPos positionClicked = pContext.getClickedPos();
            Player player = pContext.getPlayer();

            for(int i = 0; i <= positionClicked.getY() + 64; i++) {
                Block checkedBlock = pContext.getLevel().getBlockState(positionClicked.below(i)).getBlock();
                if (isValuableBlock(checkedBlock)){
                    outputValuableCoordinates(positionClicked, player, checkedBlock);
                    foundBlock = true;
                    break;
                }

            }

            if(!foundBlock){
                player.sendMessage(new TranslatableComponent("item.tutorialtastic.dowsing_rod.no_valuables"), player.getUUID());
            }


        }

        pContext.getItemInHand().hurtAndBreak(1,pContext.getPlayer(),
                (player) -> player.broadcastBreakEvent(player.getUsedItemHand()));
        return super.useOn(pContext);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if(Screen.hasShiftDown()){
            pTooltipComponents.add(new TranslatableComponent("tooltip.tutorialtastic.dowsing_rod.tooltip.shift"));
        } else {
            pTooltipComponents.add(new TranslatableComponent("tooltip.tutorialtastic.dowsing_rod.tooltip.noshift"));
        }
    }

    private void outputValuableCoordinates(BlockPos blockPos, Player player, Block blockBelow) {
        player.sendMessage(new TextComponent(
                "Found " +
                        blockBelow.asItem().getRegistryName().toString() +
                        " at " + "(" +
                        blockPos.getX() + ", " +
                        blockPos.getY() + "," +
                        blockPos.getZ() + ")"), player.getUUID());
    }

    private boolean isValuableBlock(Block block) {
        BlockState blockState = block.defaultBlockState();
        return blockState.is(Tags.Blocks.ORES);
    }
}
