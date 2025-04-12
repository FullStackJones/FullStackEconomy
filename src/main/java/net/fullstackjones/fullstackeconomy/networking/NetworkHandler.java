package net.fullstackjones.fullstackeconomy.networking;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class NetworkHandler {
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playBidirectional(
                CurrencyDataCreatePayload.TYPE,
                CurrencyDataCreatePayload.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        null,
                        ServerPayloadHandler::handleCreateCurrencyDataOnMain
                )
        );
        registrar.playBidirectional(
                UpdatedCurrenciesPayload.TYPE,
                UpdatedCurrenciesPayload.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        ClientPayloadHandler::handleGetUpdatedCurrenciesPayloadDataOnMain,
                        ServerPayloadHandler::handleGetCurrencyDataOnMain
                )
        );
    }
}
