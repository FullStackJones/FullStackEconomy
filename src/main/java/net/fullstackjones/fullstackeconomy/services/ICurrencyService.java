package net.fullstackjones.fullstackeconomy.services;

import net.fullstackjones.fullstackeconomy.data.CurrencyData;
import net.fullstackjones.fullstackeconomy.data.EconomySavedData;

import java.util.UUID;

public interface ICurrencyService {
    EconomySavedData CreateCurrency(CurrencyData currency);
    EconomySavedData DeleteCurrency(UUID currencyID);
    EconomySavedData ChangeCurrency(CurrencyData currency);
    CurrencyData[] GetAllCurrencies();
    CurrencyData GetCurrencyById(UUID currencyID);
}
