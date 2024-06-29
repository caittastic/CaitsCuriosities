package strelka.gizmos.datagen.loot;

import net.minecraft.world.level.storage.loot.LootTable;
import strelka.gizmos.blocks.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    private final List<Block> knownBlocks = new ArrayList<>();

    public ModBlockLootTables(HolderLookup.Provider provider) {
        super(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags(), provider);
    }

    @Override
    protected void add(Block pBlock, LootTable.Builder pBuilder) {
        super.add(pBlock, pBuilder);
        knownBlocks.add(pBlock);
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.RUBBER_DUCK.get());
        this.dropSelf(ModBlocks.NODE.get());
        this.dropSelf(ModBlocks.BRAIN_JAR.get());
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return knownBlocks;
    }

}

