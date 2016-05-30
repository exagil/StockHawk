package com.sam_chordas.android.stockhawk.data.models;

import android.content.ContentValues;
import android.test.AndroidTestCase;

import com.sam_chordas.android.stockhawk.data.QuoteColumns;

import org.junit.Test;

import java.util.Date;

public class QuoteInstrumentationTest extends AndroidTestCase {

    public static final long MILLISECONDS_UNTIL_20160530_0235HRS = 1464575752000l;

    @Test
    public void testThatQuoteKnowsItsCorrespondingContentValues() {
        Quote quote = new Quote("FB", 1.23f, 4.56f, 9.99d, new Date(MILLISECONDS_UNTIL_20160530_0235HRS), true, false);
        ContentValues expectedContentValues = new ContentValues();
        expectedContentValues.put(QuoteColumns.SYMBOL, "FB");
        expectedContentValues.put(QuoteColumns.PERCENT_CHANGE, "1.23");
        expectedContentValues.put(QuoteColumns.CHANGE, "4.56");
        expectedContentValues.put(QuoteColumns.BIDPRICE, "9.99");
        expectedContentValues.put(QuoteColumns.ISUP, 1);
        expectedContentValues.put(QuoteColumns.ISCURRENT, 0);
        assertEquals(expectedContentValues, quote.toContentValues());
    }
}
