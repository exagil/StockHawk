package com.sam_chordas.android.stockhawk.data.models;

import android.database.Cursor;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;
import org.mockito.Mockito;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Test
    public void testThatHistoricalQuotesKnowHowToBeSortedByDateAscendingIfTheyAreUnsorted() {
        HistoricalQuote thisHistoricalQuote = new HistoricalQuote("FB", new Date(1463899584953l), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3));
        HistoricalQuote thatHistoricalQuote = new HistoricalQuote("FB", new Date(1463899584940l), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3));
        HistoricalQuotes historicalQuotes = new HistoricalQuotes(thatHistoricalQuote, thisHistoricalQuote);
        HistoricalQuotes expectedHistoricalQuotes = new HistoricalQuotes(thisHistoricalQuote, thatHistoricalQuote);
        assertEquals(expectedHistoricalQuotes, historicalQuotes.sort());
    }

    @Test
    public void testThatHistoricalQuotesKnowHowToNotBeSortedByDateAscendingIfTheyAreSortedAlready() {
        HistoricalQuote thisHistoricalQuote = new HistoricalQuote("FB", new Date(1463899584953l), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3));
        HistoricalQuote thatHistoricalQuote = new HistoricalQuote("FB", new Date(1463899584940l), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3));
        HistoricalQuotes historicalQuotes = new HistoricalQuotes(thisHistoricalQuote, thatHistoricalQuote);
        HistoricalQuotes expectedHistoricalQuotes = new HistoricalQuotes(thisHistoricalQuote, thatHistoricalQuote);
        assertEquals(expectedHistoricalQuotes, historicalQuotes.sort());
    }

    @Test
    public void testThatHistoricalQuotesAreNotSortedIfTheyAreEmpty() {
        HistoricalQuotes historicalQuotes = new HistoricalQuotes();
        HistoricalQuotes expectedHistoricalQuotes = new HistoricalQuotes();
        assertEquals(expectedHistoricalQuotes, historicalQuotes.sort());
    }
}