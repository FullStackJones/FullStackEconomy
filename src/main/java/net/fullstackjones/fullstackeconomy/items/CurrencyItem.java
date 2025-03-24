package net.fullstackjones.fullstackeconomy.items;

import net.fullstackjones.fullstackeconomy.data.CurrencyItemData;
import net.minecraft.world.item.Item;

public class CurrencyItem extends Item {
    private CurrencyItemData _CurrencyData;

    public CurrencyItem(Properties properties) {
        super(properties);
    }

    public CurrencyItem(Properties properties, CurrencyItemData data) {
        super(properties);
        _CurrencyData = data;
    }

    public CurrencyItemData getCurrencyData() {
        return _CurrencyData;
    }
}
