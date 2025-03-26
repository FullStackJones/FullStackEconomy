package net.fullstackjones.fullstackeconomy.data;

import net.fullstackjones.fullstackeconomy.Constants.CurrencyAccent;
import net.fullstackjones.fullstackeconomy.Constants.CurrencyShape;
import net.fullstackjones.fullstackeconomy.Constants.CurrencySize;
import net.fullstackjones.fullstackeconomy.items.CurrencyItem;
import net.fullstackjones.fullstackeconomy.services.CurrencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CurrencyDataTests {
    @InjectMocks
    private CurrencyData _currencyData;

    @Mock
    private CurrencyItem _silverCoin;

    @Mock
    private CurrencyItem _copperCoin;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        _currencyData.Name = "Sterling";
        _currencyData.Symbol = "£";
    }

    @Test
    void CurrencyData_constructsWithUUID() {

        CurrencyData result = new CurrencyData();
        assert result.id != null;
    }

    @Test
    void CurrencyData_constructsWithUUIDNameAndSymbol() {

        CurrencyData result = new CurrencyData("Sterling", "£");
        assert result.id != null;
        assert result.Name.equals("Sterling");
        assert result.Symbol.equals("£");
    }

    @Test
    void AddCurrencyItem_EntriesDoNotExist_CurrencyItemIsAdded() {

        _currencyData.AddCurrencyItem(_silverCoin);

        assertEquals(1, _currencyData.Currencies.length);

    }

    @Test
    void AddCurrencyItem_EntriesAlreadyExist_CurrencyItemIsAdded() {
        _currencyData.Currencies = new CurrencyItem[]{_silverCoin};

        _currencyData.AddCurrencyItem(_copperCoin);

        assertEquals(2, _currencyData.Currencies.length);
        assertTrue(Arrays.asList(_currencyData.Currencies).contains(_silverCoin));
        assertTrue(Arrays.asList(_currencyData.Currencies).contains(_copperCoin));
    }

    @Test
    void RemoveCurrencyItem_MultipleEntriesAlreadyExist_CurrencyItemIsAdded() {
        _currencyData.Currencies = new CurrencyItem[]{_copperCoin, _silverCoin};

        _currencyData.RemoveCurrencyItem(_silverCoin);

        assertEquals(1, _currencyData.Currencies.length);
        assertTrue(Arrays.asList(_currencyData.Currencies).contains(_copperCoin));
    }

    @Test
    void RemoveCurrencyItem_EntryExist_CurrencyItemIsAdded() {
        _currencyData.Currencies = new CurrencyItem[]{_silverCoin};

        _currencyData.RemoveCurrencyItem(_silverCoin);

        assertEquals(0, _currencyData.Currencies.length);
    }

    @Test
    void RemoveCurrencyItem_EntryDoesNotExist_CurrencyItemIsAdded() {
        _currencyData.Currencies = new CurrencyItem[]{_silverCoin};

        _currencyData.RemoveCurrencyItem(_silverCoin);

        assertEquals(0, _currencyData.Currencies.length);
    }

    @Test
    void AddCurrencyInCirculation_EntryDoesNotExist_AddsCurrencyInCirculation() {
        _currencyData.AddCurrencyInCirculation("Silver", 1);

        assertEquals(1, _currencyData.CurrencyInCirculation.size());
        assertEquals(1, _currencyData.CurrencyInCirculation.get("Silver"));
    }

    @Test
    void AddCurrencyInCirculation_EntryExists_IncreasesAmountOfCurrencyInCirculation() {
        _currencyData.CurrencyInCirculation.put("Silver", 1);

        _currencyData.AddCurrencyInCirculation("Silver", 10);

        assertEquals(1, _currencyData.CurrencyInCirculation.size());
        assertEquals(11, _currencyData.CurrencyInCirculation.get("Silver"));
    }

    @Test
    void RemoveCurrencyInCirculation_EntryExists_ReducesAmountOfCurrencyInCirculation() {
        _currencyData.CurrencyInCirculation.put("Silver", 1);

        _currencyData.RemoveCurrencyInCirculation("Silver",1);

        assertEquals(1, _currencyData.CurrencyInCirculation.size());
        assertEquals(0, _currencyData.CurrencyInCirculation.get("Silver"));
    }

    @Test
    void RemoveCurrencyInCirculation_EntryDoesNotExist_NoChangeToCurrencyInCirculation() {
        _currencyData.RemoveCurrencyInCirculation("Silver",1);

        assertEquals(0, _currencyData.CurrencyInCirculation.size());
    }

    @Test
    void RemoveCurrencyFromCirculation_EntryDoesNotExist_NoChangeToCurrencyInCirculation() {
        _currencyData.RemoveCurrencyFromCirculation("Silver");

        assertEquals(0, _currencyData.CurrencyInCirculation.size());
    }

    @Test
    void RemoveCurrencyFromCirculation_EntryDoesExist_CurrencyIsRemoved() {
        _currencyData.CurrencyInCirculation.put("Silver", 1);

        _currencyData.RemoveCurrencyFromCirculation("Silver");

        assertEquals(0, _currencyData.CurrencyInCirculation.size());
    }
}
