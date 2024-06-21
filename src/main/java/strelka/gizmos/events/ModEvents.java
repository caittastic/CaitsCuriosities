package strelka.gizmos.events;

import strelka.gizmos.Gizmos;
import strelka.gizmos.networking.BrainRemovePayload;
import strelka.gizmos.networking.PayloadActions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = Gizmos.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(Gizmos.MOD_ID);
        registrar.playToClient(BrainRemovePayload.TYPE, BrainRemovePayload.STREAM_CODEC, PayloadActions::brainRemoveSync);
    }
}
