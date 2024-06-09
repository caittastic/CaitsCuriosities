package caittastic.caitsmod.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class InjectIntoChestModifier extends LootModifier {
  public static final MapCodec<InjectIntoChestModifier> CODEC =
          RecordCodecBuilder.mapCodec(instance ->
                  codecStart(instance).and(
                          BuiltInRegistries.ITEM.byNameCodec()
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
    //if(context.getRandom().nextFloat() >= 0.5f)
      generatedLoot.add(new ItemStack(this.item, 1));
    return generatedLoot;
  }

  @Override
  public MapCodec<? extends IGlobalLootModifier> codec(){
    return CODEC;
  }
}