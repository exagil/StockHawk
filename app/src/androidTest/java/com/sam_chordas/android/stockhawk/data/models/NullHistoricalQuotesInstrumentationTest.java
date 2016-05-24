package com.sam_chordas.android.stockhawk.data.models;

import android.test.AndroidTestCase;

import junit.framework.Assert;

import org.junit.Test;

public class NullHistoricalQuotesInstrumentationTest extends AndroidTestCase {
    @Test
    public void testNullHistoricalQuotesCannotBeConvertedToContentValues() {
        Assert.assertEquals(null, new NullHistoricalQuotes().toContentValues());
    }
}