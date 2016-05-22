package com.sam_chordas.android.stockhawk.data.models;

import android.content.ContentValues;
import android.test.AndroidTestCase;

import com.sam_chordas.android.stockhawk.data.HistoryColumns;

import org.junit.Test;

import java.util.Date;

public class HistoricalQuotesTest extends AndroidTestCase {
    @Test
    public void testHistoricalQuotesKnowsHowToConvertItselfIntoContentValues() {
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
                "APPL",
                new Date(1463899584953l),
                new Double(2.3),
                new Double(2.3),
                new Double(2.3),
                new Double(2.3),
                new Double(2.3),
                new Double(2.3)
        );
        HistoricalQuotes historicalQuotes = new HistoricalQuotes(firstHistoricalQuote, secondHistoricalQuote);
        ContentValues firstContentValue = new ContentValues();
        firstContentValue.put(HistoryColumns.SYMBOL, "FB");
        firstContentValue.put(HistoryColumns.DATE, "2016-05-22");
        firstContentValue.put(HistoryColumns.HIGH, 2.3);
        firstContentValue.put(HistoryColumns.LOW, 2.3);
        firstContentValue.put(HistoryColumns.OPEN, 2.3);
        firstContentValue.put(HistoryColumns.CLOSE, 2.3);
        firstContentValue.put(HistoryColumns.VOLUME, 2.3);
        firstContentValue.put(HistoryColumns.ADJ_CLOSE, 2.3);
        ContentValues secondContentValue = new ContentValues();
        secondContentValue.put(HistoryColumns.SYMBOL, "APPL");
        secondContentValue.put(HistoryColumns.DATE, "2016-05-22");
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
}