package net.fullstackjones.fullstackeconomy.api;

import net.fullstackjones.fullstackeconomy.items.CurrencyItem;
import net.minecraft.world.item.Item;

public class CurrencyAPI {

    public static void CreateCurrency(){
        // create currency data
    }

    public static void DeleteCurrency(){
        // delete currency data
    }

    public static CurrencyItem CreateCurrencyItem(){
        // create currencyItem data
        return new CurrencyItem(new Item.Properties());
    }

    public static void DeleteCurrencyItem(){
        // delete currencyItem data
    }

    public static void SetCurrencyInCirculation(CurrencyItem currencyItem, String Currency){
        // Update coins in circulation
    }

    public static CurrencyItem[] GetCurrencyInCirculation(CurrencyItem currencyItem, String Currency){
        return new CurrencyItem[]{};
    }

    public static CurrencyItem[] GetSumOfCurrency(int value, int value2, String Currency){
        int sumValue = value + value2;
        // get currency data by name
        // create currency array based off of the sumValue
        return new CurrencyItem[]{};
    }

    public static CurrencyItem[] GetSubtractionOfCurrency(int value, int value2, String Currency){
        int subtractedValue = value - value2;
        // get currency data by name
        // create currency array based off of the subtractedValue
        return new CurrencyItem[]{};
    }
}
