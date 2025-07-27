package com.github.rotlug.glebabrushes;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record BrushPayload(String brushId, BlockPos start, BlockPos end) implements CustomPacketPayload {

    public static final Type<BrushPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Brushes.MODID, "brush_data"));

    public static final StreamCodec<ByteBuf, BrushPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, BrushPayload::brushId,
            BlockPos.STREAM_CODEC, BrushPayload::start,
            BlockPos.STREAM_CODEC, BrushPayload::end,
            BrushPayload::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
