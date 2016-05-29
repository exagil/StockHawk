package com.sam_chordas.android.stockhawk.data.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HistoricalQuoteDateTest {
    public static final long MILLISECONDS_UNTIL_20160528_1212HRS = 1464437520000l;
    public static final long MILLISECONDS_UNTIL_20160529_0000HRS = 1464480001000l;
    public static final long MILLISECONDS_UNTIL_20160627_1010HRS = 1464343810000l;
    public static final long MILLISECONDS_UNTIL_20160530_0000HRS = 1464566401000l;
    public static final long MILLISECONDS_UNTIL_20160529_1212HRS = 1464516610000l;
    public static final long MILLISECONDS_UNTIL_20160429_0000HRS = 1461888001000l;
    public static final long MILLISECONDS_UNTIL_20160525_1010HRS = 1464171000000l;

    @Test
    public void testHistoricalQuoteDateShouldNotBeEqualToNull() {
        assertFalse(HistoricalQuoteDate.fromMilliseconds(MILLISECONDS_UNTIL_20160529_0000HRS).equals(null));
    }

    @Test
    public void testHistoricalQuoteDateShouldKnowThatItsEqualToItself() {
        HistoricalQuoteDate historicalQuoteDate = HistoricalQuoteDate.fromMilliseconds(MILLISECONDS_UNTIL_20160529_0000HRS);
        assertTrue(historicalQuoteDate.equals(historicalQuoteDate));
    }

    @Test
    public void testHistoricalQuoteDateKnowsThatItsEqualToAnotherHistoricalQuoteDateWithSameIfItHasSameDate() {
        HistoricalQuoteDate thisHistoricalQuoteDate = HistoricalQuoteDate.fromMilliseconds(MILLISECONDS_UNTIL_20160529_0000HRS);
        HistoricalQuoteDate thatHistoricalQuoteDate = HistoricalQuoteDate.fromMilliseconds(MILLISECONDS_UNTIL_20160529_0000HRS);
        assertTrue(thisHistoricalQuoteDate.equals(thatHistoricalQuoteDate));
    }

    @Test
    public void testHistoricalQuoteDatesShouldNotBeEqualWhenOneHasDayFromMondayToThursdayWhileOtherHasUpcomingFridayToSunday() {
        HistoricalQuoteDate thisHistoricalQuoteDate = HistoricalQuoteDate.fromMilliseconds(MILLISECONDS_UNTIL_20160529_0000HRS);
        HistoricalQuoteDate thatHistoricalQuoteDate = HistoricalQuoteDate.fromMilliseconds(MILLISECONDS_UNTIL_20160530_0000HRS);
        assertFalse(thisHistoricalQuoteDate.equals(thatHistoricalQuoteDate));
    }

    @Test
    public void testThatTwoHistoricalQuoteDatesHavingDifferentDaysInWeeksShouldNotBeEqual() {
        HistoricalQuoteDate thisHistoricalQuoteDate = HistoricalQuoteDate.fromMilliseconds(MILLISECONDS_UNTIL_20160525_1010HRS);
        HistoricalQuoteDate thatHistoricalQuoteDate = HistoricalQuoteDate.fromMilliseconds(1464257400000l);
        assertFalse(thisHistoricalQuoteDate.equals(thatHistoricalQuoteDate));
    }

    @Test
    public void testHistoricalQuoteDateShouldNotBeEqualToAnythingOtherThanHistoricalQuoteDate() {
        assertFalse(HistoricalQuoteDate.fromMilliseconds(MILLISECONDS_UNTIL_20160529_0000HRS).equals(new Object()));
    }

    @Test
    public void testHistoricalQuoteDatesWithDifferentMillisecondsButSameDatesShouldBeEqual() {
        HistoricalQuoteDate thisHistoricalQuoteDate = HistoricalQuoteDate.fromMilliseconds(MILLISECONDS_UNTIL_20160529_0000HRS);
        HistoricalQuoteDate thatHistoricalQuoteDate = HistoricalQuoteDate.fromMilliseconds(MILLISECONDS_UNTIL_20160529_1212HRS);
        assertTrue(thisHistoricalQuoteDate.equals(thatHistoricalQuoteDate));
    }

    @Test
    public void testHistoricalQuoteDatesWhichAreSameInBusinessContextHaveSameHashCodes() {
        HistoricalQuoteDate thisHistoricalQuoteDate = HistoricalQuoteDate.fromMilliseconds(MILLISECONDS_UNTIL_20160529_0000HRS);
        HistoricalQuoteDate thatHistoricalQuoteDate = HistoricalQuoteDate.fromMilliseconds(MILLISECONDS_UNTIL_20160529_1212HRS);
        assertEquals(thisHistoricalQuoteDate.hashCode(), thatHistoricalQuoteDate.hashCode());
    }

    @Test
    public void testThatHistoricalQuoteDateOfFridayIsEqualToHistoricalQuoteDateOfUpcomingSaturday() {
        HistoricalQuoteDate thisHistoricalQuoteDate = HistoricalQuoteDate.fromMilliseconds(MILLISECONDS_UNTIL_20160627_1010HRS);
        HistoricalQuoteDate thatHistoricalQuoteDate = HistoricalQuoteDate.fromMilliseconds(MILLISECONDS_UNTIL_20160528_1212HRS);
        assertEquals(thatHistoricalQuoteDate, thisHistoricalQuoteDate);
    }

    @Test
    public void testHistoricalQuoteDatesWithOneOnSaturdayAndOtherOnUpcomingSundayAreEqual() {
        HistoricalQuoteDate thisHistoricalQuoteDate = HistoricalQuoteDate.fromMilliseconds(MILLISECONDS_UNTIL_20160529_1212HRS);
        HistoricalQuoteDate thatHistoricalQuoteDate = HistoricalQuoteDate.fromMilliseconds(MILLISECONDS_UNTIL_20160528_1212HRS);
        assertEquals(thatHistoricalQuoteDate, thisHistoricalQuoteDate);
    }

    @Test
    public void testThatHistoricalQuoteDateOfFridayIsEqualToHistoricalQuoteDateOfUpcomingSunday() {
        HistoricalQuoteDate thisHistoricalQuoteDate = HistoricalQuoteDate.fromMilliseconds(MILLISECONDS_UNTIL_20160627_1010HRS);
        HistoricalQuoteDate thatHistoricalQuoteDate = HistoricalQuoteDate.fromMilliseconds(MILLISECONDS_UNTIL_20160529_1212HRS);
        assertEquals(thatHistoricalQuoteDate, thisHistoricalQuoteDate);
    }

    @Test
    public void testThatHistoricalQuoteDateKnowsItsQueryableFormatForWeekdays() {
        HistoricalQuoteDate historicalQuoteDate = HistoricalQuoteDate.fromMilliseconds(MILLISECONDS_UNTIL_20160627_1010HRS);
        assertEquals("2016-05-27", historicalQuoteDate.queryable());
    }

    @Test
    public void testHistoricalQuoteDateKnowsItsQueryableFormatForSaturday() {
        HistoricalQuoteDate historicalQuoteDate = HistoricalQuoteDate.fromMilliseconds(MILLISECONDS_UNTIL_20160528_1212HRS);
        assertEquals("2016-05-27", historicalQuoteDate.queryable());
    }

    @Test
    public void testHistoricalQuoteDateKnowsItsQueryableFormatForSunday() {
        HistoricalQuoteDate historicalQuoteDate = HistoricalQuoteDate.fromMilliseconds(MILLISECONDS_UNTIL_20160529_0000HRS);
        assertEquals("2016-05-27", historicalQuoteDate.queryable());
    }

    @Test
    public void testHistoricalQuoteDateKnowsItsCorrespondingDateOneMonthBack() {
        HistoricalQuoteDate historicalQuoteDate = HistoricalQuoteDate.fromMilliseconds(MILLISECONDS_UNTIL_20160529_0000HRS);
        HistoricalQuoteDate expectedHistoricalQuoteDate = HistoricalQuoteDate.fromMilliseconds(MILLISECONDS_UNTIL_20160429_0000HRS);
        assertEquals(expectedHistoricalQuoteDate, historicalQuoteDate.travelOneMonthBack());
    }
}
