package net.fullstackjones.fullstackeconomy.api;

import net.fullstackjones.fullstackeconomy.items.CurrencyItem;

public interface ICurrencyAPI {
    void CreateCurrency();
    void DeleteCurrency();
    CurrencyItem CreateCurrencyItem();
    void DeleteCurrencyItem();
    void SetCurrencyInCirculation(CurrencyItem currencyItem, String Currency);
    CurrencyItem[] GetCurrencyInCirculation(CurrencyItem currencyItem, String Currency);
    CurrencyItem[] GetSumOfCurrency(int value, int value2, String Currency);
    CurrencyItem[] GetSubtractionOfCurrency(int value, int value2, String Currency);
}
