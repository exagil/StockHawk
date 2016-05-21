package com.sam_chordas.android.stockhawk.data;

import android.content.Context;
import android.test.AndroidTestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StockLoaderServiceTest extends AndroidTestCase {
    @Before
    public void setup() {
        resetDatabase();
    }

    @After
    public void tearDown() {
        resetDatabase();
    }

    @Test
    public void shouldNotLoadQuoteNameForStockWhichDoesNotExistLocally() {
        StockLoaderService stockLoaderService = new StockLoaderService(getContext());
        String actualSymbolName = stockLoaderService.loadQuoteSymbolForQuoteId(1);
        assertEquals(null, actualSymbolName);
    }

    private void resetDatabase() {
        getContext().deleteDatabase("quoteDatabase.db");
        getContext().openOrCreateDatabase("quoteDatabase.db", Context.MODE_PRIVATE, null);
    }
}
