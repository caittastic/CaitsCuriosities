package caittastic.caitsmod.Item;

import caittastic.caitsmod.CaitsMod;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CardItem extends Item{
  MutableComponent tooltipComponent;
  int cardNumber;

  public CardItem(Properties properties, MutableComponent tooltipComponent, int cardNumber){
    super(properties);
    this.tooltipComponent = tooltipComponent;
    this.cardNumber = cardNumber;
  }

  public static void setFoil(ItemStack card){
    CompoundTag nbt = card.getOrCreateTag();
    nbt.putBoolean("foiled", true);
    card.setTag(nbt);
  }

  @Override
  public void appendHoverText(ItemStack stack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced){
    pTooltipComponents.add(this.tooltipComponent);
    MutableComponent cardNumComponent = (Component.literal(""));
    if(isFoil(stack))
      cardNumComponent.append(Component.translatable("card_shiny_indicator." + CaitsMod.MOD_ID));
    cardNumComponent.append(Component.translatable("tooltip." + CaitsMod.MOD_ID + ".overworld_card_set").append("§8§o #" + cardNumber));
    pTooltipComponents.add(cardNumComponent);
    super.appendHoverText(stack, pLevel, pTooltipComponents, pIsAdvanced);
  }

  @Override
  public boolean isFoil(ItemStack stack){
    return stack.hasTag() && stack.getTag().contains("foiled") && stack.getTag().getBoolean("foiled");
  }
}
