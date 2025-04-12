package net.fullstackjones.fullstackeconomy.networking;

import net.fullstackjones.fullstackeconomy.data.CurrencyData;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;

public class ClientPayloadHandler {
    public static List<CurrencyData> updatedCurrencies;
    public static void handleGetUpdatedCurrenciesPayloadDataOnMain(final UpdatedCurrenciesPayload data, final IPayloadContext context) {
        updatedCurrencies = data.getCurrencies();
        Minecraft.getInstance().execute(() -> {
            // Notify the screen or UI to refresh
            System.out.println("Updated currencies received: " + updatedCurrencies);
        });
    }
}
