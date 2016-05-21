package com.sam_chordas.android.stockhawk.data;

import android.content.ContentValues;
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

    @Test
    public void shouldLoadQuoteSymbolWhenCorrespondingQuoteExistsLocally() {
        StockLoaderService stockLoaderService = new StockLoaderService(getContext());
        ContentValues quoteContentValues = new ContentValues();
        quoteContentValues.put(QuoteColumns.SYMBOL, "YHOO");
        quoteContentValues.put(QuoteColumns.PERCENT_CHANGE, "20");
        quoteContentValues.put(QuoteColumns.CHANGE, "10");
        quoteContentValues.put(QuoteColumns.BIDPRICE, "10");
        quoteContentValues.put(QuoteColumns.CREATED, "20-10-2016");
        quoteContentValues.put(QuoteColumns.ISUP, 1);
        quoteContentValues.put(QuoteColumns.ISCURRENT, 1);
        getContext().getContentResolver().insert(QuoteProvider.Quotes.CONTENT_URI, quoteContentValues);
        String actualSymbolName = stockLoaderService.loadQuoteSymbolForQuoteId(1);
        assertEquals("YHOO", actualSymbolName);
    }

    private void resetDatabase() {
        getContext().deleteDatabase("quoteDatabase.db");
        getContext().openOrCreateDatabase("quoteDatabase.db", Context.MODE_PRIVATE, null);
    }
}
