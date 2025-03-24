package net.fullstackjones.fullstackeconomy.api;

import net.fullstackjones.fullstackeconomy.items.CurrencyItem;
import net.minecraft.world.item.Item;

public class CurrencyAPI implements ICurrencyAPI{
    public CurrencyAPI() {
    }

    @Override
    public void CreateCurrency() {
        // create currency data
    }

    @Override
    public void DeleteCurrency() {
        // delete currency data
    }

    @Override
    public CurrencyItem CreateCurrencyItem() {
        // create currencyItem data
        return new CurrencyItem(new Item.Properties());
    }

    @Override
    public void DeleteCurrencyItem() {
        // delete currencyItem data
    }

    @Override
    public void SetCurrencyInCirculation(CurrencyItem currencyItem, String Currency) {
        // Update coins in circulation
    }

    @Override
    public CurrencyItem[] GetCurrencyInCirculation(CurrencyItem currencyItem, String Currency) {
        return new CurrencyItem[]{};
    }

    @Override
    public CurrencyItem[] GetSumOfCurrency(int value, int value2, String Currency) {
        int sumValue = value + value2;
        // get currency data by name
        // create currency array based off of the sumValue
        return new CurrencyItem[]{};
    }

    @Override
    public CurrencyItem[] GetSubtractionOfCurrency(int value, int value2, String Currency) {
        int subtractedValue = value - value2;
        // get currency data by name
        // create currency array based off of the subtractedValue
        return new CurrencyItem[]{};
    }
}
