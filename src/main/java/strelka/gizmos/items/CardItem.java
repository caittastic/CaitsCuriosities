package strelka.gizmos.items;

import strelka.gizmos.data.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

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
    public Component getName(ItemStack stack){
        if(stack.hasFoil())
            return Component.translatable(this.getDescriptionId(stack)).append(" ⭐").withStyle(ChatFormatting.ITALIC, ChatFormatting.GOLD);
        return super.getName(stack);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.CARD_FOILED, false);
    }
}
