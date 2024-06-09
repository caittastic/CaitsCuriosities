package caittastic.caitsmod.items;

import caittastic.caitsmod.CaitsMod;
import caittastic.caitsmod.data.ModDataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CardItem extends Item {
    MutableComponent tooltipComponent;
    int cardNumber;

    public CardItem(Properties properties, MutableComponent tooltipComponent, int cardNumber) {
        super(properties);
        this.tooltipComponent = tooltipComponent;
        this.cardNumber = cardNumber;
    }

    public static void setFoil(ItemStack card) {
        card.set(ModDataComponents.CARD_FOILED, true);
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.CARD_FOILED, false);
    }
}
