package com.sam_chordas.android.stockhawk.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.test.AndroidTestCase;

import com.sam_chordas.android.stockhawk.data.models.HistoricalQuote;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuotes;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.util.Date;

import static com.sam_chordas.android.stockhawk.data.StockProviderService.QuoteSymbolLoaderCallback;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class StockProviderServiceTest extends AndroidTestCase {
    @Before
    public void setup() {
        resetDatabase();
    }

    @After
    public void tearDown() {
        resetDatabase();
    }

    @Test
    public void shouldNotLoadQuoteNameForStockWhichDoesNotExistLocally() throws InterruptedException {
        StockProviderService stockProviderService = new StockProviderService(getContext());
        QuoteSymbolLoaderCallback quoteSymbolLoaderCallback = Mockito.mock(QuoteSymbolLoaderCallback.class);
        stockProviderService.loadQuoteSymbolForQuoteId(1, quoteSymbolLoaderCallback);
        Thread.sleep(1000);
        verify(quoteSymbolLoaderCallback).onQuoteSymbolLoadFailed();
        verifyNoMoreInteractions(quoteSymbolLoaderCallback);
    }

    @Test
    public void shouldLoadQuoteSymbolWhenCorrespondingQuoteExistsLocally() throws InterruptedException {
        StockProviderService stockProviderService = new StockProviderService(getContext());
        ContentValues quoteContentValues = new ContentValues();
        quoteContentValues.put(QuoteColumns.SYMBOL, "YHOO");
        quoteContentValues.put(QuoteColumns.PERCENT_CHANGE, "20");
        quoteContentValues.put(QuoteColumns.CHANGE, "10");
        quoteContentValues.put(QuoteColumns.BIDPRICE, "10");
        quoteContentValues.put(QuoteColumns.CREATED, "20-10-2016");
        quoteContentValues.put(QuoteColumns.ISUP, 1);
        quoteContentValues.put(QuoteColumns.ISCURRENT, 1);
        getContext().getContentResolver().insert(QuoteProvider.Quotes.CONTENT_URI, quoteContentValues);

        QuoteSymbolLoaderCallback quoteSymbolLoaderCallback = Mockito.mock(QuoteSymbolLoaderCallback.class);
        stockProviderService.loadQuoteSymbolForQuoteId(1, quoteSymbolLoaderCallback);
        Thread.sleep(1000);
        verify(quoteSymbolLoaderCallback).onQuoteSymbolLoaded("YHOO");
        verifyNoMoreInteractions(quoteSymbolLoaderCallback);
    }

    @Test
    public void testThatStockProviderServiceKnowsHowToInsertHistoricalQuotes() throws ParseException {
        StockProviderService stockProviderService = new StockProviderService(getContext());
        HistoricalQuote firstHistoricalQuote = new HistoricalQuote(
                "FB",
                new Date(1463899584953l),
                new Double(2.3),
                new Double(2.3),
                new Double(2.3),
                new Double(2.3),
                new Double(2.3),
                new Double(2.3)
        );
        HistoricalQuote secondHistoricalQuote = new HistoricalQuote(
                "FB",
                new Date(1463899584953l),
                new Double(2.3),
                new Double(2.3),
                new Double(2.3),
                new Double(2.3),
                new Double(2.3),
                new Double(2.3)
        );
        HistoricalQuotes expectedHistoricalQuotes = new HistoricalQuotes(firstHistoricalQuote, secondHistoricalQuote);
        stockProviderService.insertHistoricalQuotes(expectedHistoricalQuotes);
        Cursor historyCursor = getContext().getContentResolver().query(HistoryProvider.History.CONTENT_URI, null,
                HistoryColumns.SYMBOL + "=?", new String[]{"FB"}, null);
        HistoricalQuotes actualHistoricalQuotes = HistoricalQuotes.fromCursor(historyCursor);
        assertEquals(expectedHistoricalQuotes, actualHistoricalQuotes);
    }

    private void resetDatabase() {
        getContext().deleteDatabase("quoteDatabase.db");
        getContext().openOrCreateDatabase("quoteDatabase.db", Context.MODE_PRIVATE, null);
    }
}
