package caittastic.caitsmod.NodeNetworkUtils;

import net.minecraft.core.BlockPos;
import net.minecraftforge.items.IItemHandler;

public class Node{
  public BlockPos nodeBlockPosition;
  public IItemHandler connectedItemHandler;

  public Node(BlockPos nodeBlockPosition, IItemHandler connectedItemHandler){
    this.nodeBlockPosition = nodeBlockPosition;
    this.connectedItemHandler = connectedItemHandler;
  }
}
