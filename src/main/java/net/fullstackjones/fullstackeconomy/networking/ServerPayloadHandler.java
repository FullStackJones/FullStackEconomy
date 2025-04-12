package net.fullstackjones.fullstackeconomy.networking;

import net.fullstackjones.fullstackeconomy.data.CurrencyData;
import net.fullstackjones.fullstackeconomy.services.CurrencyService;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;

public class ServerPayloadHandler{

    public static void handleCreateCurrencyDataOnMain(final CurrencyDataCreatePayload data, final IPayloadContext context) {
        CurrencyService currencyService = CurrencyService.getInstance();
        CurrencyData currencyData = new CurrencyData(data.name(), data.symbol());
        currencyService.CreateCurrency(currencyData);
        System.out.println("Server Handler says Hi, data should be inserted now!");

        ServerPlayer player = (ServerPlayer) context.player();
        if (player != null) {
            List<CurrencyData> updatedCurrencies = List.of(currencyService.GetAllCurrencies());
            UpdatedCurrenciesPayload payload = new UpdatedCurrenciesPayload(updatedCurrencies);
            context.reply(payload); // Send the payload back to the client
        }
    }

    public static void handleGetCurrencyDataOnMain(final UpdatedCurrenciesPayload data, final IPayloadContext context) {
        CurrencyService currencyService = CurrencyService.getInstance();

        ServerPlayer player = (ServerPlayer) context.player();
        if (player != null) {
            List<CurrencyData> updatedCurrencies = List.of(currencyService.GetAllCurrencies());
            UpdatedCurrenciesPayload payload = new UpdatedCurrenciesPayload(updatedCurrencies);
            context.reply(payload); // Send the payload back to the client
        }
    }
}
