package com.sam_chordas.android.stockhawk.data.models;

import android.content.ContentValues;
import android.test.AndroidTestCase;

import com.sam_chordas.android.stockhawk.data.QuoteColumns;

import org.junit.Test;

public class QuoteInstrumentationTest extends AndroidTestCase {
    @Test
    public void testThatQuoteKnowsItsCorrespondingContentValues() {
        Quote quote = new Quote("FB", 1.23f, 4.56f, 9.99d, PersistableBoolean.TRUE, PersistableBoolean.FALSE);
        ContentValues expectedContentValues = new ContentValues();
        expectedContentValues.put(QuoteColumns.SYMBOL, "FB");
        expectedContentValues.put(QuoteColumns.PERCENT_CHANGE, "1.23");
        expectedContentValues.put(QuoteColumns.CHANGE, "4.56");
        expectedContentValues.put(QuoteColumns.BIDPRICE, "9.99");
        expectedContentValues.put(QuoteColumns.ISUP, 1);
        expectedContentValues.put(QuoteColumns.ISCURRENT, 0);
        assertEquals(expectedContentValues, quote.toContentValues());
    }

    @Test
    public void testThatQuoteKnowsHowToBuildItselfFromContentValues() {
        Quote quote = new Quote("FB", 1.23f, 4.56f, 9.99d, PersistableBoolean.TRUE, PersistableBoolean.FALSE);
        ContentValues quoteContentValues = new ContentValues();
        quoteContentValues.put(QuoteColumns.SYMBOL, "FB");
        quoteContentValues.put(QuoteColumns.PERCENT_CHANGE, "1.23");
        quoteContentValues.put(QuoteColumns.CHANGE, "4.56");
        quoteContentValues.put(QuoteColumns.BIDPRICE, "9.99");
        quoteContentValues.put(QuoteColumns.ISUP, 1);
        quoteContentValues.put(QuoteColumns.ISCURRENT, 0);
        assertEquals(quote, Quote.fromContentValues(quoteContentValues));
    }
}
