package net.fullstackjones.fullstackeconomy.data;

import net.fullstackjones.fullstackeconomy.items.CurrencyItem;

import java.util.Dictionary;

public class CurrencyData {
    public String Name;
    public String Symbol;
    public CurrencyItem[] Currencies;
    public Dictionary<String, Integer> CurrencyInCirculation;
}
