package net.fullstackjones.fullstackeconomy.services;

import net.fullstackjones.fullstackeconomy.data.CurrencyData;
import net.fullstackjones.fullstackeconomy.data.EconomySavedData;
import net.minecraft.nbt.CompoundTag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class CurrencyServiceTests {

    @InjectMocks
    private CurrencyService currencyService;

    @Spy
    private EconomySavedData economySavedData;

    private CurrencyData sterlingData;
    private CurrencyData dollarData;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        sterlingData = new CurrencyData();
        sterlingData.Name = "Sterling";
        sterlingData.Symbol = "£";

        dollarData = new CurrencyData();
        dollarData.Name = "Dollar";
        dollarData.Symbol = "$";

        economySavedData = EconomySavedData.create();
    }

    @Test
    void CreateCurrency_CurrencyIsAdded() {
        EconomySavedData result = currencyService.CreateCurrency(sterlingData);

        assertEquals(1, result.currencyDataList.size());
        assertEquals("Sterling", result.currencyDataList.get(0).Name);
    }

    @Test
    void DeleteCurrency_When1EntryInList_ReturnsNewEconomySavedData() {
        economySavedData.currencyDataList.add(sterlingData);
        currencyService = new CurrencyService(economySavedData);

        EconomySavedData result = currencyService.DeleteCurrency("Sterling");

        assertEquals(0, result.currencyDataList.size());
    }

    @Test
    void DeleteCurrency_When0EntryInList_ReturnsNewEconomySavedData() {
        EconomySavedData result = currencyService.DeleteCurrency("Sterling");

        assertEquals(0, result.currencyDataList.size());
    }

    @Test
    void DeleteCurrency_whenThereIsMoreThan1Currency_ReturnsNewEconomySavedData() {
        economySavedData.currencyDataList.add(dollarData);
        currencyService = new CurrencyService(economySavedData);

        EconomySavedData result = currencyService.DeleteCurrency("Sterling");

        assertEquals(1, result.currencyDataList.size());
        assertEquals("Dollar", result.currencyDataList.get(0).Name);
    }
}
