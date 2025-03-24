package net.fullstackjones.fullstackeconomy.items;

import net.fullstackjones.fullstackeconomy.data.CurrencyItemData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;

public class CurrencyItem extends Item {
    private CurrencyItemData _CurrencyData;

    public CurrencyItem(Properties properties) {
        super(properties);
    }

    public CurrencyItem(CurrencyItemData data) {
        super(new Item.Properties());
        _CurrencyData = data;
    }

    public CurrencyItemData getCurrencyData() {
        return _CurrencyData;
    }
}
