package net.fullstackjones.fullstackeconomy.data;

import net.fullstackjones.fullstackeconomy.items.CurrencyItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

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
        for (int i = 0; i < Currencies.length; i++) {
            newCurrencies[i] = Currencies[i];
        }
        newCurrencies[Currencies.length] = currency;
        Currencies = newCurrencies;
    }

    public void RemoveCurrencyItem(CurrencyItem currency) {
        Arrays.stream(Currencies).filter(currencyItem -> Objects.equals(currencyItem, currency)).findFirst().ifPresent(currencyItem -> {
            CurrencyItem[] newCurrencies = new CurrencyItem[Currencies.length - 1];
            int j = 0;
            for (int i = 0; i < Currencies.length; i++) {
                if (Currencies[i] != currency) {
                    newCurrencies[j] = Currencies[i];
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
}
