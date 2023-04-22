package caittastic.caitsmod.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class InjectIntoChestModifier extends LootModifier{
  public static final Codec<InjectIntoChestModifier> CODEC =
          RecordCodecBuilder.create(instance ->
                  codecStart(instance).and(
                          ForgeRegistries.ITEMS.getCodec()
                                  .fieldOf("item").forGetter(m -> m.item)
                  ).apply(instance, InjectIntoChestModifier::new)
          );

  private final Item item;

  protected InjectIntoChestModifier(LootItemCondition[] conditionsIn, Item item){
    super(conditionsIn);
    this.item = item;
  }

  @Override
  protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context){
    if(context.getRandom().nextFloat() >= 0.5f)
      generatedLoot.add(new ItemStack(this.item, 1));
    return generatedLoot;
  }

  @Override
  public Codec<? extends IGlobalLootModifier> codec(){
    return CODEC;
  }
}