package net.fullstackjones.fullstackeconomy.items;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fullstackjones.fullstackeconomy.data.CurrencyItemData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CurrencyItem extends Item {
    private CurrencyItemData _CurrencyData;

    public CurrencyItem(CurrencyItemData data) {
        super(new Item.Properties());
        _CurrencyData = data;
    }

    public CurrencyItemData getCurrencyData() {
        return _CurrencyData;
    }

    @Override
    public void onDestroyed(ItemEntity itemEntity, DamageSource damageSource) {
        super.onDestroyed(itemEntity, damageSource);

        // currency service needs to be used to remove items from circulation.
    }

    @Override
    public void onCraftedPostProcess(ItemStack stack, Level level) {
        super.onCraftedPostProcess(stack, level);

        // currency service adds items to circulation.
    }

    public static final Codec<CurrencyItem> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            CurrencyItemData.CODEC.fieldOf("currencyData").forGetter(CurrencyItem::getCurrencyData)
    ).apply(instance, CurrencyItem::new));
}
