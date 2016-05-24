package com.sam_chordas.android.stockhawk.data.models;

import android.database.Cursor;

import org.junit.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class HistoricalQuotesTest {
    @Test
    public void testThatHistoricalQuotesShouldNotBeEmptyIfConsistOfHistoricalQuote() {
        HistoricalQuote historicalQuote = new HistoricalQuote("historical_quote", new Date(System.currentTimeMillis()), new Double(1), new Double(1), new Double(2), new Double(4), new Double(1), new Double(9));
        HistoricalQuotes historicalQuotes = new HistoricalQuotes(historicalQuote);
        assertFalse(historicalQuotes.isEmpty());
    }

    @Test
    public void testThatHistoricalQuotesShouldBeEmptyWhenNoHistoricalQuotePresent() {
        HistoricalQuotes historicalQuotes = new HistoricalQuotes();
        assertTrue(historicalQuotes.isEmpty());
    }

    @Test
    public void testThatNoHistoricalQuotesAreObtainedWhenObtaintedNoneFromDatabase() throws ParseException {
        Cursor mockCursor = Mockito.mock(Cursor.class);
        when(mockCursor.getCount()).thenReturn(0);
        assertEquals(new NullHistoricalQuotes(), HistoricalQuotes.fromCursor(mockCursor));
    }
}