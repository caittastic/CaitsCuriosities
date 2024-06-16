package caittastic.caitsmod.networking;

import caittastic.caitsmod.blockentities.NodeBE;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class PayloadActions {
    public static void brainRemoveSync(BrainRemovePayload payload, IPayloadContext ctx) {
        if (Minecraft.getInstance().level.getBlockEntity(payload.blockPos()) instanceof NodeBE entity) {
            entity.removeBrain();
        }

    }
}
