package net.fullstackjones.fullstackeconomy.services;

import net.fullstackjones.fullstackeconomy.data.CurrencyData;
import net.fullstackjones.fullstackeconomy.data.EconomySavedData;
import net.minecraft.nbt.CompoundTag;

import java.util.Iterator;

public class CurrencyService implements ICurrencyService {
    private final EconomySavedData _economySavedData;

    public CurrencyService(EconomySavedData economySavedData) {
        _economySavedData = economySavedData;
    }

    public CurrencyService() {
        _economySavedData = EconomySavedData.create();
    }

    @Override
    public EconomySavedData CreateCurrency(CurrencyData currency) {
        _economySavedData.AddCurrency(currency);
        return _economySavedData;
    }

    @Override
    public EconomySavedData DeleteCurrency(String name) {
        CurrencyData data = _economySavedData.currencyDataList.stream().filter(currencyData -> currencyData.Name.equals(name)).findFirst().orElse(null);
        if (data == null){
            return _economySavedData;
        }
        _economySavedData.RemoveCurrency(data);
        return _economySavedData;
    }
}
