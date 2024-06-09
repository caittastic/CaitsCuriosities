package caittastic.caitsmod.events;

import caittastic.caitsmod.CaitsMod;
import caittastic.caitsmod.networking.BrainRemovePayload;
import caittastic.caitsmod.networking.PayloadActions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = CaitsMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(CaitsMod.MOD_ID);
        registrar.playToClient(BrainRemovePayload.TYPE, BrainRemovePayload.STREAM_CODEC, PayloadActions::brainRemoveSync);
    }
}
