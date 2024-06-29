package strelka.gizmos;

import net.minecraft.SharedConstants;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import strelka.gizmos.blockentities.ModBlockEntities;
import strelka.gizmos.blocks.ModBlocks;
import strelka.gizmos.data.ModDataComponents;
import strelka.gizmos.items.ModItemProperties;
import strelka.gizmos.items.ModItems;
import strelka.gizmos.loot.AddLootModifiers;
import strelka.gizmos.particles.ModParticles;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Gizmos.MOD_ID)
public class Gizmos{
    //the ID of our mod, in a form that can be accessed anywhere
    public static final String MOD_ID = "gizmos";

    /* Food Properties */
    public static final FoodProperties PORRIDGE = (new FoodProperties.Builder()).nutrition(6).saturationModifier(0.9F).effect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0), 0.3F).build();

    public Gizmos(IEventBus modEventBus, ModContainer container){
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

    private void clientSetup(final FMLClientSetupEvent event){
        ModItemProperties.addCustomItemProperties();
    }

    public static boolean enableExperimentalFeatures() {
        return SharedConstants.IS_RUNNING_IN_IDE;
    }
}
