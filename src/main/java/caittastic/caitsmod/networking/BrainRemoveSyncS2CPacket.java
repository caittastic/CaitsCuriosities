package caittastic.caitsmod.networking;

import caittastic.caitsmod.BlockEntity.NodeBE;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BrainRemoveSyncS2CPacket{
  private final BlockPos pos;

  public BrainRemoveSyncS2CPacket(BlockPos pos){
    this.pos = pos;
  }

  public BrainRemoveSyncS2CPacket(FriendlyByteBuf buf){
    this.pos = buf.readBlockPos();
  }

  public void toBytes(FriendlyByteBuf buf){
    buf.writeBlockPos(pos);
  }

  public boolean handle(Supplier<NetworkEvent.Context> supplier){
    NetworkEvent.Context context = supplier.get();
    context.enqueueWork(() -> {
      if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof NodeBE entity){
        entity.removeBrain();
      }
    });
    return true;
  }

}
