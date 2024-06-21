package strelka.gizmos.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Objects;

public record ResonatingWandComponent(BlockPos blockPos, boolean attuned) {
    public static final ResonatingWandComponent EMPTY = new ResonatingWandComponent(BlockPos.ZERO, false);

    public static MapCodec<ResonatingWandComponent> MAP_CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            BlockPos.CODEC.fieldOf("blockpos").forGetter(ResonatingWandComponent::blockPos),
            Codec.BOOL.fieldOf("attuned").forGetter(ResonatingWandComponent::attuned)
    ).apply(builder, ResonatingWandComponent::new));

    public static StreamCodec<ByteBuf, ResonatingWandComponent> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            ResonatingWandComponent::blockPos,
            ByteBufCodecs.BOOL,
            ResonatingWandComponent::attuned,
            ResonatingWandComponent::new
    );

    public ResonatingWandComponent setBlockPos(BlockPos blockPos) {
        return new ResonatingWandComponent(blockPos, attuned);
    }

    public ResonatingWandComponent setAttuned(boolean attuned) {
        return new ResonatingWandComponent(blockPos, attuned);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResonatingWandComponent that)) return false;
        return attuned == that.attuned && Objects.equals(blockPos, that.blockPos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blockPos, attuned);
    }
}
