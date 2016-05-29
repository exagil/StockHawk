package com.sam_chordas.android.stockhawk.data.models;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.test.AndroidTestCase;

import com.sam_chordas.android.stockhawk.data.HistoryColumns;
import com.sam_chordas.android.stockhawk.data.generated.HistoryProvider;

import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

public class HistoricalQuotesInstrumentationTest extends AndroidTestCase {
    @Test
    public void testHistoricalQuotesKnowsHowToConvertItselfIntoContentValues() {
        HistoricalQuote firstHistoricalQuote = new HistoricalQuote("FB", new Date(1463899584953l), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3));
        HistoricalQuote secondHistoricalQuote = new HistoricalQuote("APPL", new Date(1463998210000l), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3));
        HistoricalQuotes historicalQuotes = new HistoricalQuotes(firstHistoricalQuote, secondHistoricalQuote);
        ContentValues firstContentValue = new ContentValues();
        firstContentValue.put(HistoryColumns.SYMBOL, "FB");
        firstContentValue.put(HistoryColumns.DATE, 20160522l);
        firstContentValue.put(HistoryColumns.HIGH, 2.3);
        firstContentValue.put(HistoryColumns.LOW, 2.3);
        firstContentValue.put(HistoryColumns.OPEN, 2.3);
        firstContentValue.put(HistoryColumns.CLOSE, 2.3);
        firstContentValue.put(HistoryColumns.VOLUME, 2.3);
        firstContentValue.put(HistoryColumns.ADJ_CLOSE, 2.3);
        ContentValues secondContentValue = new ContentValues();
        secondContentValue.put(HistoryColumns.SYMBOL, "APPL");
        secondContentValue.put(HistoryColumns.DATE, 20160523l);
        secondContentValue.put(HistoryColumns.HIGH, 2.3);
        secondContentValue.put(HistoryColumns.LOW, 2.3);
        secondContentValue.put(HistoryColumns.OPEN, 2.3);
        secondContentValue.put(HistoryColumns.CLOSE, 2.3);
        secondContentValue.put(HistoryColumns.VOLUME, 2.3);
        secondContentValue.put(HistoryColumns.ADJ_CLOSE, 2.3);
        ContentValues[] actualContentValues = historicalQuotes.toContentValues();
        assertEquals(firstContentValue, actualContentValues[0]);
        assertEquals(secondContentValue, actualContentValues[1]);
    }

    @Test
    public void shouldKnowHowToConvertHistoricalQuotesFromCursor() throws ParseException {
        ContentResolver contentResolver = getContext().getContentResolver();
        HistoricalQuote firstHistoricalQuote = new HistoricalQuote("FB", new Date(1463899584953l), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3));
        HistoricalQuote secondHistoricalQuote = new HistoricalQuote("APPL", new Date(1463998210000l), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3));
        contentResolver.insert(HistoryProvider.History.CONTENT_URI, firstHistoricalQuote.toContentValues());
        contentResolver.insert(HistoryProvider.History.CONTENT_URI, secondHistoricalQuote.toContentValues());
        Cursor historyCursor = contentResolver.query(HistoryProvider.History.CONTENT_URI, null, null, null, null);
        HistoricalQuotes expectedHistoricalQuotes = new HistoricalQuotes(firstHistoricalQuote, secondHistoricalQuote);
        assertEquals(expectedHistoricalQuotes, HistoricalQuotes.fromCursor(historyCursor));
    }
}