package caittastic.caitsmod.loot;

import caittastic.caitsmod.CaitsMod;
import com.mojang.serialization.Codec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AddLootModifiers{
  public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS =
          DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, CaitsMod.MOD_ID);

  public static final RegistryObject<Codec<? extends IGlobalLootModifier>> LOOT_TABLE_INJECT =
          LOOT_MODIFIER_SERIALIZERS.register("loot_table_inject", () -> InjectIntoChestModifier.CODEC);

}
