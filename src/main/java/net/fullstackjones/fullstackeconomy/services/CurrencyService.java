package net.fullstackjones.fullstackeconomy.services;

import net.fullstackjones.fullstackeconomy.data.CurrencyData;
import net.fullstackjones.fullstackeconomy.data.EconomySavedData;
import net.minecraft.server.level.ServerLevel;

import java.util.UUID;

public class CurrencyService implements ICurrencyService {
    private static CurrencyService instance;
    private final EconomySavedData _economySavedData;

    public CurrencyService(ServerLevel level) {
        _economySavedData = EconomySavedData.getOrCreate(level);
        _economySavedData.setDirty();
    }

    public static void initialize(ServerLevel level) {
        if (instance == null) {
            instance = new CurrencyService(level);
        }
    }

    public static CurrencyService getInstance() {
        if (instance == null) {
            throw new IllegalStateException("CurrencyService has not been initialized!");
        }
        return instance;
    }

    @Override
    public EconomySavedData CreateCurrency(CurrencyData currency) {
        if(_economySavedData.currencyDataList.stream().anyMatch(currencyData -> currencyData.Name.equals(currency.Name))){
            return _economySavedData;
        }
        _economySavedData.AddCurrency(currency);
        _economySavedData.setDirty();
        return _economySavedData;
    }

    @Override
    public EconomySavedData DeleteCurrency(UUID currencyId) {
        CurrencyData data = _economySavedData.currencyDataList.stream().filter(currencyData -> currencyData.id.equals(currencyId)).findFirst().orElse(null);
        if (data == null){
            return _economySavedData;
        }
        _economySavedData.RemoveCurrency(data);
        _economySavedData.setDirty();
        return _economySavedData;
    }

    @Override
    public EconomySavedData ChangeCurrency(CurrencyData currency) {
        CurrencyData data = _economySavedData.currencyDataList.stream()
                .filter(currencyData -> currencyData.id.equals(currency.id))
                .findFirst().orElse(null);
        if (data == null){
            return _economySavedData;
        }
        _economySavedData.RemoveCurrency(data);
        _economySavedData.AddCurrency(currency);
        _economySavedData.setDirty();
        return _economySavedData;
    }

    @Override
    public CurrencyData[] GetAllCurrencies() {
        return _economySavedData.currencyDataList.toArray(new CurrencyData[0]);
    }

    @Override
    public CurrencyData GetCurrencyById(UUID currencyID) {
        return _economySavedData.currencyDataList.stream().filter(currencyData -> currencyData.id.equals(currencyID)).findFirst().orElse(null);
    }
}
