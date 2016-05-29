package com.sam_chordas.android.stockhawk.data.models;

import android.database.Cursor;

import org.junit.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Test
    public void testThatHistoricalQuotesKnowHowToBeSortedByDateAscendingIfTheyAreUnsorted() {
        HistoricalQuote thisHistoricalQuote = new HistoricalQuote("FB", new Date(1464498687000l), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3));
        HistoricalQuote thatHistoricalQuote = new HistoricalQuote("FB", new Date(1464603010000l), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3));
        HistoricalQuotes historicalQuotes = new HistoricalQuotes(thatHistoricalQuote, thisHistoricalQuote);
        List<HistoricalQuote> expectedHistoricalQuotes = new ArrayList<>();
        expectedHistoricalQuotes.add(thisHistoricalQuote);
        expectedHistoricalQuotes.add(thatHistoricalQuote);
        assertEquals(expectedHistoricalQuotes, historicalQuotes.sortedCollection());
    }

    @Test
    public void testThatHistoricalQuotesKnowHowToNotBeSortedByDateAscendingIfTheyAreSortedAlready() {
        HistoricalQuote thisHistoricalQuote = new HistoricalQuote("FB", new Date(1464498687000l), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3));
        HistoricalQuote thatHistoricalQuote = new HistoricalQuote("FB", new Date(1464603010000l), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3));
        HistoricalQuotes historicalQuotes = new HistoricalQuotes(thisHistoricalQuote, thatHistoricalQuote);
        List<HistoricalQuote> expectedHistoricalQuotes = new ArrayList<>();
        expectedHistoricalQuotes.add(thisHistoricalQuote);
        expectedHistoricalQuotes.add(thatHistoricalQuote);
        assertEquals(expectedHistoricalQuotes, historicalQuotes.sortedCollection());
    }

    @Test
    public void testThatHistoricalQuotesAreNotSortedIfTheyAreEmpty() {
        HistoricalQuotes historicalQuotes = new HistoricalQuotes();
        assertEquals(new ArrayList<HistoricalQuote>(), historicalQuotes.sortedCollection());
    }
}