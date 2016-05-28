package com.sam_chordas.android.stockhawk.data.models;

import junit.framework.Assert;

import org.junit.Test;

public class NullHistoricalQuotesTest {
    @Test
    public void testThatNullHistoricalQuotesShouldBeEmpty() {
        Assert.assertTrue(new NullHistoricalQuotes().isEmpty());
    }

    @Test
    public void testThatNullHistoricalQuotesShouldNotKnowHowToBeSorted() {
        Assert.assertEquals(new NullHistoricalQuotes(), new NullHistoricalQuotes().sort());
    }
}
