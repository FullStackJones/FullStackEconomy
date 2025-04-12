package net.fullstackjones.fullstackeconomy.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.fullstackjones.fullstackeconomy.items.CurrencyItem;
import net.fullstackjones.fullstackeconomy.networking.UUIDCodec;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.*;

public class CurrencyData {
    public UUID id;
    public String Name;
    public String Symbol;
    public CurrencyItem[] Currencies = new CurrencyItem[0];
    public Dictionary<String, Integer> CurrencyInCirculation = new Hashtable<>();

    public CurrencyData() {
        id = UUID.randomUUID();
    }

    public CurrencyData(String name, String symbol) {
        id = UUID.randomUUID();
        Name = name;
        Symbol = symbol;
    }

    public void AddCurrencyItem(CurrencyItem currency) {
        CurrencyItem[] newCurrencies = new CurrencyItem[Currencies.length + 1];
        System.arraycopy(Currencies, 0, newCurrencies, 0, Currencies.length);
        newCurrencies[Currencies.length] = currency;
        Currencies = newCurrencies;
    }

    public void RemoveCurrencyItem(CurrencyItem currency) {
        Arrays.stream(Currencies).filter(currencyItem -> Objects.equals(currencyItem, currency)).findFirst().ifPresent(currencyItem -> {
            CurrencyItem[] newCurrencies = new CurrencyItem[Currencies.length - 1];
            int j = 0;
            for (CurrencyItem item : Currencies) {
                if (item != currency) {
                    newCurrencies[j] = item;
                    j++;
                }
            }
            Currencies = newCurrencies;
        });
    }

    public void AddCurrencyInCirculation(String currency, int amount) {
        if (CurrencyInCirculation.get(currency) != null) {
            CurrencyInCirculation.put(currency, CurrencyInCirculation.get(currency) + amount);
        } else {
            CurrencyInCirculation.put(currency, amount);
        }
    }

    public void RemoveCurrencyInCirculation(String currency, int amount) {
        if (CurrencyInCirculation.get(currency) != null) {
            CurrencyInCirculation.put(currency, CurrencyInCirculation.get(currency) - amount);
        }
    }

    public void RemoveCurrencyFromCirculation(String currency) {
        CurrencyInCirculation.remove(currency);
    }

    public String getName() {
        return Name;
    }

    public String getSymbol() {
        return Symbol;
    }

    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        tag.putString("Name", Name);
        tag.putString("Symbol", Symbol);
        tag.putUUID("id", id);

        ListTag currenciesTag = new ListTag();
        for (CurrencyItem currency : Currencies) {
            CompoundTag currencyTag = new CompoundTag();
            currency.getCurrencyData().put(currencyTag);
            currenciesTag.add(currencyTag);
        }
        tag.put("Currencies", currenciesTag);

        CompoundTag circulationTag = new CompoundTag();
        Enumeration<String> keys = CurrencyInCirculation.keys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            circulationTag.putInt(key, CurrencyInCirculation.get(key));
        }
        tag.put("CurrencyInCirculation", circulationTag);

        return tag;
    }

    public static CurrencyData load(CompoundTag tag) {
        CurrencyData data = new CurrencyData();
        data.Name = tag.getString("Name");
        data.Symbol = tag.getString("Symbol");
        data.id = tag.getUUID("id");

        ListTag currenciesTag = tag.getList("Currencies", 10);
        data.Currencies = new CurrencyItem[currenciesTag.size()];
        for (int i = 0; i < currenciesTag.size(); i++) {
            CurrencyItemData currency = CurrencyItemData.load(currenciesTag.getCompound(i));
            data.Currencies[i] = new CurrencyItem(currency);
        }

        CompoundTag circulationTag = tag.getCompound("CurrencyInCirculation");
        data.CurrencyInCirculation = new Hashtable<>();
        for (String key : circulationTag.getAllKeys()) {
            data.CurrencyInCirculation.put(key, circulationTag.getInt(key));
        }

        return data;
    }

    public static final Codec<CurrencyData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            UUIDCodec.CODEC.fieldOf("id").forGetter(data -> data.id),
            Codec.STRING.fieldOf("Name").forGetter(data -> data.Name),
            Codec.STRING.fieldOf("Symbol").forGetter(data -> data.Symbol),
            Codec.list(CurrencyItem.CODEC).fieldOf("Currencies").forGetter(data -> Arrays.asList(data.Currencies)),
            Codec.unboundedMap(Codec.STRING, Codec.INT).fieldOf("CurrencyInCirculation").forGetter(data -> {
                Map<String, Integer> map = new HashMap<>();
                Enumeration<String> keys = data.CurrencyInCirculation.keys();
                while (keys.hasMoreElements()) {
                    String key = keys.nextElement();
                    map.put(key, data.CurrencyInCirculation.get(key));
                }
                return map;
            })
    ).apply(instance, (id, name, symbol, currencies, circulation) -> {
        CurrencyData data = new CurrencyData();
        data.id = id;
        data.Name = name;
        data.Symbol = symbol;
        data.Currencies = currencies.toArray(new CurrencyItem[0]);
        data.CurrencyInCirculation = new Hashtable<>(circulation);
        return data;
    }));

    public static final StreamCodec<ByteBuf, CurrencyData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, // Codec for Name
            CurrencyData::getName,
            ByteBufCodecs.STRING_UTF8, // Codec for Symbol
            CurrencyData::getSymbol,
            CurrencyData::new
    );
}
