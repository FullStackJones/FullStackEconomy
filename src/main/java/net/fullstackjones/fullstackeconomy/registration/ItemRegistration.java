package net.fullstackjones.fullstackeconomy.registration;

import net.fullstackjones.fullstackeconomy.Constants.CurrencyAccent;
import net.fullstackjones.fullstackeconomy.Constants.CurrencyShape;
import net.fullstackjones.fullstackeconomy.Constants.CurrencySize;
import net.fullstackjones.fullstackeconomy.FullStackEconomy;
import net.fullstackjones.fullstackeconomy.data.CurrencyItemData;
import net.fullstackjones.fullstackeconomy.items.CurrencyItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemRegistration {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(FullStackEconomy.MODID);
    public static final DeferredItem<Item> COPPERCOIN_ITEM = ITEMS.register(
            "coppercoin",
            () -> new CurrencyItem(new CurrencyItemData(
                    CurrencySize.SMALL.name(),
                    CurrencyShape.CIRCLE.name(),
                    CurrencyAccent.NONE.name(),
                    1,
                    "Copper")));
    public static final DeferredItem<Item> SILVERCOIN_ITEM = ITEMS.register(
            "silvercoin",
            () -> new CurrencyItem(new CurrencyItemData(
                    CurrencySize.SMALL.name(),
                    CurrencyShape.CIRCLE.name(),
                    CurrencyAccent.NONE.name(),
                    9,
                    "Silver")));
    public static final DeferredItem<Item> GOLDCOIN_ITEM = ITEMS.register(
            "goldcoin",
            () -> new CurrencyItem(new CurrencyItemData(
                    CurrencySize.SMALL.name(),
                    CurrencyShape.CIRCLE.name(),
                    CurrencyAccent.NONE.name(),
                    81,
                    "Gold")));

    public static final DeferredItem<Item> COINPRESS_PLATE_ITEM = ITEMS.register(
            "coinpress_plate",
            () -> new Item(new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
