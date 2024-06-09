package caittastic.caitsmod.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.block.Block;

import java.util.Objects;

public record DestructionCatalystComponent(int charge, int depth, int cooldown, boolean mining, BlockPos firstPos,
                                           Direction direction) {
    public static final DestructionCatalystComponent EMPTY = new DestructionCatalystComponent(0, 0, 0, false, BlockPos.ZERO, Direction.NORTH);

    public static MapCodec<DestructionCatalystComponent> MAP_CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            Codec.INT.fieldOf("charge").forGetter(DestructionCatalystComponent::charge),
            Codec.INT.fieldOf("depth").forGetter(DestructionCatalystComponent::depth),
            Codec.INT.fieldOf("cooldown").forGetter(DestructionCatalystComponent::cooldown),
            Codec.BOOL.fieldOf("mining").forGetter(DestructionCatalystComponent::mining),
            BlockPos.CODEC.fieldOf("first_pos").forGetter(DestructionCatalystComponent::firstPos),
            Direction.CODEC.fieldOf("direction").forGetter(DestructionCatalystComponent::direction)
    ).apply(builder, DestructionCatalystComponent::new));
    public static StreamCodec<ByteBuf, DestructionCatalystComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            DestructionCatalystComponent::charge,
            ByteBufCodecs.INT,
            DestructionCatalystComponent::depth,
            ByteBufCodecs.INT,
            DestructionCatalystComponent::cooldown,
            ByteBufCodecs.BOOL,
            DestructionCatalystComponent::mining,
            BlockPos.STREAM_CODEC,
            DestructionCatalystComponent::firstPos,
            Direction.STREAM_CODEC,
            DestructionCatalystComponent::direction,
            DestructionCatalystComponent::new
    );

    public DestructionCatalystComponent setCharge(int charge) {
        return new DestructionCatalystComponent(charge, depth, cooldown, mining, firstPos, direction);
    }

    public DestructionCatalystComponent setDepth(int depth) {
        return new DestructionCatalystComponent(charge, depth, cooldown, mining, firstPos, direction);
    }

    public DestructionCatalystComponent setCoolDown(int cooldown) {
        return new DestructionCatalystComponent(charge, depth, cooldown, mining, firstPos, direction);
    }

    public DestructionCatalystComponent setMining(boolean mining) {
        return new DestructionCatalystComponent(charge, depth, cooldown, mining, firstPos, direction);
    }

    public DestructionCatalystComponent setFirstPos(BlockPos firstPos) {
        return new DestructionCatalystComponent(charge, depth, cooldown, mining, firstPos, direction);
    }

    public DestructionCatalystComponent setDirection(Direction direction) {
        return new DestructionCatalystComponent(charge, depth, cooldown, mining, firstPos, direction);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DestructionCatalystComponent that)) return false;
        return depth == that.depth && charge == that.charge && cooldown == that.cooldown && mining == that.mining && Objects.equals(firstPos, that.firstPos) && direction == that.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(charge, depth, cooldown, mining, firstPos, direction);
    }
}
