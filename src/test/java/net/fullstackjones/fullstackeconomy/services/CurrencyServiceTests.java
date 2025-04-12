package net.fullstackjones.fullstackeconomy.services;

import net.fullstackjones.fullstackeconomy.data.CurrencyData;
import net.fullstackjones.fullstackeconomy.data.EconomySavedData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.neoforged.neoforge.common.util.FakePlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CurrencyServiceTests {

    @Mock
    private CurrencyService currencyService;

    @Spy
    private EconomySavedData economySavedData;

    @Mock
    private ServerLevel serverLevel;

    @Mock
    private DimensionDataStorage dimensionDataStorage;

    private CurrencyData sterlingData;
    private CurrencyData dollarData;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);;

        when(serverLevel.getDataStorage()).thenReturn(dimensionDataStorage);
        when(dimensionDataStorage.computeIfAbsent(any(), eq(EconomySavedData.NAME)))
                .thenReturn(economySavedData);

        currencyService = new CurrencyService(serverLevel);

        sterlingData = new CurrencyData();
        sterlingData.Name = "Sterling";
        sterlingData.Symbol = "£";

        dollarData = new CurrencyData();
        dollarData.Name = "Dollar";
        dollarData.Symbol = "$";
    }

    @Test
    void CreateCurrency_CurrencyIsAdded() {
        EconomySavedData result = currencyService.CreateCurrency(sterlingData);

        assertEquals(1, result.currencyDataList.size());
        assertEquals("Sterling", result.currencyDataList.get(0).Name);
    }

    @Test
    void CreateCurrency_CurrencyAlreadyInList_CurrencyIsAdded() {
        economySavedData.currencyDataList.add(sterlingData);
        currencyService = new CurrencyService(serverLevel);

        EconomySavedData result = currencyService.CreateCurrency(sterlingData);

        assertEquals(1, result.currencyDataList.size());
        assertEquals("Sterling", result.currencyDataList.get(0).Name);
    }

    @Test
    void DeleteCurrency_WhenEntryInList_ReturnsNewEconomySavedData() {
        economySavedData.currencyDataList.add(sterlingData);
        currencyService = new CurrencyService(serverLevel);

        EconomySavedData result = currencyService.DeleteCurrency(sterlingData.id);

        assertEquals(0, result.currencyDataList.size());
    }

    @Test
    void DeleteCurrency_WhenEntryNotInList_ReturnsNewEconomySavedData() {
        EconomySavedData result = currencyService.DeleteCurrency(sterlingData.id);

        assertEquals(0, result.currencyDataList.size());
    }

    @Test
    void DeleteCurrency_whenThereIsMoreThan1Currency_ReturnsNewEconomySavedData() {
        economySavedData.currencyDataList.add(dollarData);
        currencyService = new CurrencyService(serverLevel);

        EconomySavedData result = currencyService.DeleteCurrency(sterlingData.id);

        assertEquals(1, result.currencyDataList.size());
        assertEquals("Dollar", result.currencyDataList.get(0).Name);
    }

    @Test
    void ChangeCurrency_ReturnsNewEconomySavedData() {
        economySavedData.currencyDataList.add(sterlingData);
        currencyService = new CurrencyService(serverLevel);

        CurrencyData euroData = sterlingData;
        euroData.Name = "Euro";
        euroData.Symbol = "€";

        EconomySavedData result = currencyService.ChangeCurrency(euroData);

        assertEquals(1, result.currencyDataList.size());
        assertEquals(sterlingData.id, result.currencyDataList.get(0).id);
        assertEquals("Euro", result.currencyDataList.get(0).Name);
        assertEquals("€", result.currencyDataList.get(0).Symbol);
    }
}
