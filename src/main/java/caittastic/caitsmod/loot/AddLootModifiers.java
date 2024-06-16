package caittastic.caitsmod.loot;

import caittastic.caitsmod.CaitsMod;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class AddLootModifiers{
  public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS =
          DeferredRegister.create(NeoForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS, CaitsMod.MOD_ID);

  public static final Supplier<MapCodec<? extends IGlobalLootModifier>> LOOT_TABLE_INJECT =
          LOOT_MODIFIER_SERIALIZERS.register("loot_table_inject", () -> InjectIntoChestModifier.CODEC);

}
