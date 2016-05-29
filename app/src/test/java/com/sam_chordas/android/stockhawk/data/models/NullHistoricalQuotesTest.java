package com.sam_chordas.android.stockhawk.data.models;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Matchers;

import static junit.framework.Assert.assertFalse;

public class NullHistoricalQuotesTest {
    @Test
    public void testThatNullHistoricalQuotesShouldBeEmpty() {
        Assert.assertTrue(new NullHistoricalQuotes().isEmpty());
    }

    @Test
    public void testThatNullHistoricalQuotesShouldNotKnowHowToBeSorted() {
        Assert.assertNull(new NullHistoricalQuotes().sortedCollection());
    }

    @Test
    public void testThatNullHistoricalQuotesShouldNotBeFresh() {
        assertFalse(new NullHistoricalQuotes().areFresh(Matchers.<HistoricalQuoteDate>any()));
    }
}
