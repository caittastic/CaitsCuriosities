package caittastic.caitsmod.datagen.loot;

import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModLootTableProvider extends LootTableProvider{
  private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>>
          lootTables = List.of(Pair.of(ModBlockLootTables::new, LootContextParamSets.BLOCK));

  public ModLootTableProvider(DataGenerator pGenerator){
    super(pGenerator);
  }


  @Override
  protected @NotNull List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables(){
    return lootTables;
  }

  @Override
  protected void validate(Map<ResourceLocation, LootTable> map, @NotNull ValidationContext validationtracker){
    map.forEach((id, table) -> LootTables.validate(validationtracker, id, table));
  }
}
