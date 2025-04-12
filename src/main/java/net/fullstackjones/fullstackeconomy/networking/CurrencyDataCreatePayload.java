package net.fullstackjones.fullstackeconomy.networking;


import io.netty.buffer.ByteBuf;
import net.fullstackjones.fullstackeconomy.FullStackEconomy;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record CurrencyDataCreatePayload(String name, String symbol) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<CurrencyDataCreatePayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(FullStackEconomy.MODID, "create_currencydata"));

    public static final StreamCodec<ByteBuf, CurrencyDataCreatePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            CurrencyDataCreatePayload::name,
            ByteBufCodecs.STRING_UTF8,
            CurrencyDataCreatePayload::symbol,
            CurrencyDataCreatePayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
