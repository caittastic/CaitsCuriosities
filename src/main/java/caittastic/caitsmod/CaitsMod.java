package caittastic.caitsmod;

import caittastic.caitsmod.blocks.ModBlocks;
import caittastic.caitsmod.blockentities.ModBlockEntities;
import caittastic.caitsmod.data.ModDataComponents;
import caittastic.caitsmod.items.ModCreativeTabs;
import caittastic.caitsmod.items.ModItemProperties;
import caittastic.caitsmod.items.ModItems;
import caittastic.caitsmod.loot.AddLootModifiers;
import caittastic.caitsmod.particles.ModParticles;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(CaitsMod.MOD_ID)
public class CaitsMod {
    //the ID of our mod, in a form that can be accessed anywhere
    public static final String MOD_ID = "caitsmod";

    public CaitsMod(IEventBus modEventBus, ModContainer container) {
        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        ModSoundEvents.SOUND_EVENTS.register(modEventBus);
        ModParticles.PARTICLE_TYPES.register(modEventBus);
        AddLootModifiers.LOOT_MODIFIER_SERIALIZERS.register(modEventBus);
        ModDataComponents.COMPONENTS.register(modEventBus);
        ModCreativeTabs.CREATIVE_TABS.register(modEventBus);

        modEventBus.addListener(this::clientSetup);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ModItemProperties.addCustomItemProperties();
    }

}
