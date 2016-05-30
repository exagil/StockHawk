package com.sam_chordas.android.stockhawk.data.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.test.AndroidTestCase;

import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.generated.QuoteProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class QuoteInstrumentationTest extends AndroidTestCase {
    @Before
    public void setup() {
        resetDatabase();
    }

    @After
    public void tearDown() {
        resetDatabase();
    }

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

    @Test
    public void testThatQuoteKnowsHowToBuildItselfFromCursor() {
        Quote expectedQuote = new Quote("FB", 1.23f, 4.56f, 9.99d, PersistableBoolean.TRUE, PersistableBoolean.FALSE);
        getContext().getContentResolver().insert(QuoteProvider.Quotes.CONTENT_URI, expectedQuote.toContentValues());
        Cursor quoteCursor = getContext().getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI, null, QuoteColumns.SYMBOL + "=?", new String[]{"FB"}, null);
        quoteCursor.moveToFirst();
        assertEquals(expectedQuote, Quote.fromCursor(quoteCursor));
    }

    private void resetDatabase() {
        getContext().deleteDatabase("quoteDatabase.db");
        getContext().openOrCreateDatabase("quoteDatabase.db", Context.MODE_PRIVATE, null);
    }
}
