package caittastic.caitsmod.data;

import caittastic.caitsmod.CaitsMod;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModDataComponents {
    public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(CaitsMod.MOD_ID);

    public static final Supplier<DataComponentType<DestructionCatalystComponent>> DESTRUCTION_CATALYST = COMPONENTS.registerComponentType("destruction_catalyst",
            builder -> builder.persistent(DestructionCatalystComponent.MAP_CODEC.codec()).networkSynchronized(DestructionCatalystComponent.STREAM_CODEC));

    public static final Supplier<DataComponentType<ResonatingWandComponent>> RESONATING_WAND = COMPONENTS.registerComponentType("resonating_wand",
            builder -> builder.persistent(ResonatingWandComponent.MAP_CODEC.codec()).networkSynchronized(ResonatingWandComponent.STREAM_CODEC));

    public static final Supplier<DataComponentType<Boolean>> CARD_FOILED = COMPONENTS.registerComponentType("card_foiled",
            builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));

}
