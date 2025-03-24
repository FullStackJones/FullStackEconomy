package net.fullstackjones.fullstackeconomy.services;

import net.fullstackjones.fullstackeconomy.data.CurrencyData;
import net.fullstackjones.fullstackeconomy.data.EconomySavedData;

public interface ICurrencyService {
    EconomySavedData CreateCurrency(CurrencyData currency);
    EconomySavedData DeleteCurrency(String name);
}
