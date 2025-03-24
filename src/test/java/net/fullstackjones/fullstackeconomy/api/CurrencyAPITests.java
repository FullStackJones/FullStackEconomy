package net.fullstackjones.fullstackeconomy.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class CurrencyAPITests {
    @Spy
    private CurrencyAPI _currencyAPI = spy(CurrencyAPI.class);

    @BeforeEach
    void setup() {

    }

    @Test
    void CreateCurrency_CurrencyIsCreated(){
        _currencyAPI.CreateCurrency();
        verify(_currencyAPI).CreateCurrency();
    }

    @Test
    void CreateCurrency_CurrencyIsNotCreated(){

    }
}
