package com.sam_chordas.android.stockhawk.data.models;

import junit.framework.Assert;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HistoricalQuoteDateTest {
    @Test
    public void testHistoricalQuoteDateShouldNotBeEqualToNull() {
        assertFalse(HistoricalQuoteDate.fromMilliseconds(1464480001000l).equals(null));
    }

    @Test
    public void testHistoricalQuoteDateShouldKnowThatItsEqualToItself() {
        HistoricalQuoteDate historicalQuoteDate = HistoricalQuoteDate.fromMilliseconds(1464480001000l);
        assertTrue(historicalQuoteDate.equals(historicalQuoteDate));
    }

    @Test
    public void testHistoricalQuoteDateKnowsThatItsEqualToAnotherHistoricalQuoteDateWithSameIfItHasSameDate() {
        HistoricalQuoteDate thisHistoricalQuoteDate = HistoricalQuoteDate.fromMilliseconds(1464480001000l);
        HistoricalQuoteDate thatHistoricalQuoteDate = HistoricalQuoteDate.fromMilliseconds(1464480001000l);
        assertTrue(thisHistoricalQuoteDate.equals(thatHistoricalQuoteDate));
    }

    @Test
    public void testHistoricalQuoteDateShouldNotBeEqualToAnotherHistoricalQuoteDateWhenTheyHaveDifferentDates() {
        HistoricalQuoteDate thisHistoricalQuoteDate = HistoricalQuoteDate.fromMilliseconds(1464480001000l);
        HistoricalQuoteDate thatHistoricalQuoteDate = HistoricalQuoteDate.fromMilliseconds(1464566401000l);
        assertFalse(thisHistoricalQuoteDate.equals(thatHistoricalQuoteDate));
    }

    @Test
    public void testHistoricalQuoteDateShouldNotBeEqualToAnythingOtherThanHistoricalQuoteDate() {
        assertFalse(HistoricalQuoteDate.fromMilliseconds(1464480001000l).equals(new Object()));
    }

    @Test
    public void testHistoricalQuoteDatesWithDifferentMillisecondsButSameDatesShouldBeEqual() {
        HistoricalQuoteDate thisHistoricalQuoteDate = HistoricalQuoteDate.fromMilliseconds(1464480001000l);
        HistoricalQuoteDate thatHistoricalQuoteDate = HistoricalQuoteDate.fromMilliseconds(1464516610000l);
        assertTrue(thisHistoricalQuoteDate.equals(thatHistoricalQuoteDate));
    }

    @Test
    public void testHistoricalQuoteDatesWhichAreSameInBusinessContextHaveSameHashCodes() {
        HistoricalQuoteDate thisHistoricalQuoteDate = HistoricalQuoteDate.fromMilliseconds(1464480001000l);
        HistoricalQuoteDate thatHistoricalQuoteDate = HistoricalQuoteDate.fromMilliseconds(1464516610000l);
        assertEquals(thisHistoricalQuoteDate.hashCode(), thatHistoricalQuoteDate.hashCode());
    }

    @Test
    public void testHistoricalQuoteDateKnowsItsQueryableFormat() {
        HistoricalQuoteDate historicalQuoteDate = HistoricalQuoteDate.fromMilliseconds(1464480001000l);
        Assert.assertEquals("2016-05-29", historicalQuoteDate.queryable());
    }

    @Test
    public void testHistoricalQuoteDateKnowsItsCorrespondingDateOneMonthBack() {
        HistoricalQuoteDate historicalQuoteDate = HistoricalQuoteDate.fromMilliseconds(1464480001000l);
        HistoricalQuoteDate expectedHistoricalQuoteDate = HistoricalQuoteDate.fromMilliseconds(1461888001000l);
        assertEquals(expectedHistoricalQuoteDate, historicalQuoteDate.travelOneMonthBack());
    }
}
