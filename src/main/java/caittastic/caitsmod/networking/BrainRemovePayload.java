package caittastic.caitsmod.networking;

import caittastic.caitsmod.CaitsMod;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

// Server to client
public record BrainRemovePayload(BlockPos blockPos) implements CustomPacketPayload {
  public static final Type<BrainRemovePayload> TYPE = new Type<>(new ResourceLocation(CaitsMod.MOD_ID, "brain_remove_payload"));
  public static final StreamCodec<RegistryFriendlyByteBuf, BrainRemovePayload> STREAM_CODEC = StreamCodec.composite(
          BlockPos.STREAM_CODEC,
          BrainRemovePayload::blockPos,
          BrainRemovePayload::new);

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }
}
