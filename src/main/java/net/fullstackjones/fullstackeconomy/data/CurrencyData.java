package net.fullstackjones.fullstackeconomy.data;

import net.fullstackjones.fullstackeconomy.items.CurrencyItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

public class CurrencyData {
    public String Name;
    public String Symbol;
    public CurrencyItem[] Currencies = new CurrencyItem[0];
    public Dictionary<String, Integer> CurrencyInCirculation = new Hashtable<>();


    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        tag.putString("Name", Name);
        tag.putString("Symbol", Symbol);

        ListTag currenciesTag = new ListTag();
        for (CurrencyItem currency : Currencies) {
            CompoundTag currencyTag = new CompoundTag();
            currency.getCurrencyData().put(currencyTag);
            currenciesTag.add(currencyTag);
        }
        tag.put("Currencies", currenciesTag);

        // Save CurrencyInCirculation
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

        // Load Currencies
        ListTag currenciesTag = tag.getList("Currencies", 10);
        data.Currencies = new CurrencyItem[currenciesTag.size()];
        for (int i = 0; i < currenciesTag.size(); i++) {
            CurrencyItemData currency = CurrencyItemData.load(currenciesTag.getCompound(i));
            data.Currencies[i] = new CurrencyItem(currency);
        }

        // Load CurrencyInCirculation
        CompoundTag circulationTag = tag.getCompound("CurrencyInCirculation");
        data.CurrencyInCirculation = new Hashtable<>();
        for (String key : circulationTag.getAllKeys()) {
            data.CurrencyInCirculation.put(key, circulationTag.getInt(key));
        }

        return data;
    }
}
