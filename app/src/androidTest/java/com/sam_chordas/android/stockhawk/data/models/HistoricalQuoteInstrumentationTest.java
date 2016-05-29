package com.sam_chordas.android.stockhawk.data.models;

import android.content.ContentValues;
import android.test.AndroidTestCase;

import com.sam_chordas.android.stockhawk.data.HistoryColumns;

import org.junit.Test;

import java.util.Date;

public class HistoricalQuoteInstrumentationTest extends AndroidTestCase {
    @Test
    public void shouldKnowHotToConvertItIntoPersistableContentValue() {
        HistoricalQuote historicalQuote = new HistoricalQuote(
                "FB",
                new Date(1463899584953l),
                new Double(2.3),
                new Double(2.3),
                new Double(2.3),
                new Double(2.3),
                new Double(2.3),
                new Double(2.3)
        );
        ContentValues expectedContentValues = new ContentValues();
        expectedContentValues.put(HistoryColumns.SYMBOL, "FB");
        expectedContentValues.put(HistoryColumns.DATE, 20160522l);
        expectedContentValues.put(HistoryColumns.HIGH, 2.3);
        expectedContentValues.put(HistoryColumns.LOW, 2.3);
        expectedContentValues.put(HistoryColumns.OPEN, 2.3);
        expectedContentValues.put(HistoryColumns.CLOSE, 2.3);
        expectedContentValues.put(HistoryColumns.VOLUME, 2.3);
        expectedContentValues.put(HistoryColumns.ADJ_CLOSE, 2.3);
        assertEquals(expectedContentValues, historicalQuote.toContentValues());
    }
}
