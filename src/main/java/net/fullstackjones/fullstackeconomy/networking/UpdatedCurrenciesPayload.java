package net.fullstackjones.fullstackeconomy.networking;

import io.netty.buffer.ByteBuf;
import net.fullstackjones.fullstackeconomy.FullStackEconomy;
import net.fullstackjones.fullstackeconomy.data.CurrencyData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class UpdatedCurrenciesPayload implements CustomPacketPayload {
    private final List<CurrencyData> currencies;

    public UpdatedCurrenciesPayload() {
        this.currencies = new ArrayList<>();
    }
    public static final StreamCodec<ByteBuf, UpdatedCurrenciesPayload> STREAM_CODEC = StreamCodec.ofMember(
            UpdatedCurrenciesPayload::encode,
            UpdatedCurrenciesPayload::decode
    );

    public static final CustomPacketPayload.Type<UpdatedCurrenciesPayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(FullStackEconomy.MODID, "get_updated_currencydata"));

    public UpdatedCurrenciesPayload(List<CurrencyData> currencies) {
        this.currencies = currencies;
    }

    public List<CurrencyData> getCurrencies() {
        return currencies;
    }

    public static UpdatedCurrenciesPayload decode(ByteBuf buffer) {
        int size = buffer.readInt();
        List<CurrencyData> currencies = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            String name = buffer.readCharSequence(buffer.readInt(), java.nio.charset.StandardCharsets.UTF_8).toString(); // Read currency name
            String symbol = buffer.readCharSequence(buffer.readInt(), java.nio.charset.StandardCharsets.UTF_8).toString(); // Read currency symbol
            currencies.add(new CurrencyData(name, symbol));
        }
        return new UpdatedCurrenciesPayload(currencies);
    }

    public void encode(ByteBuf buffer) {
        buffer.writeInt(currencies.size());
        for (CurrencyData currency : currencies) {
            byte[] nameBytes = currency.Name.getBytes(java.nio.charset.StandardCharsets.UTF_8);
            buffer.writeInt(nameBytes.length); // Write the length of the name
            buffer.writeBytes(nameBytes); // Write the name

            byte[] symbolBytes = currency.Symbol.getBytes(java.nio.charset.StandardCharsets.UTF_8);
            buffer.writeInt(symbolBytes.length); // Write the length of the symbol
            buffer.writeBytes(symbolBytes); // Write the symbol
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
