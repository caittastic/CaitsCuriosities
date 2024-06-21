package strelka.gizmos.nodes;

import net.minecraft.core.BlockPos;
import net.neoforged.neoforge.items.IItemHandler;

public class Node {
    public BlockPos nodeBlockPosition;
    public IItemHandler connectedItemHandler;

    public Node(BlockPos nodeBlockPosition, IItemHandler connectedItemHandler) {
        this.nodeBlockPosition = nodeBlockPosition;
        this.connectedItemHandler = connectedItemHandler;
    }
}
