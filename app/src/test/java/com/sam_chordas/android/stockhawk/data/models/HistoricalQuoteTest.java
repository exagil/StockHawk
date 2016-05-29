package com.sam_chordas.android.stockhawk.data.models;

import org.junit.Test;

import java.util.Date;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

public class HistoricalQuoteTest {

    public static final long MILLISECONDS_UNTIL_20160529_1010HRS = 1464516610000l;
    public static final long MILLISECONDS_UNTIL_20160529_1212HRS = 1464523932000l;
    public static final long MILLISECONDS_UNTIL_20160528_1212HRS = 1464437520000l;
    public static final long MILLISECONDS_UNTIL_20160627_1010HRS = 1464343810000l;
    public static final long MILLISECONDS_UNTIL_20160525_1010HRS = 1464171000000l;

    @Test
    public void shouldBeEqualToItself() {
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
        assertEquals(historicalQuote, historicalQuote);
    }

    @Test
    public void shouldNotBeEqualToNull() {
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
        assertNotEquals(historicalQuote, null);
    }

    @Test
    public void shouldBeEqualToHistoricalQuoteWithSameValues() {
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
                "FB",
                new Date(1463899584953l),
                new Double(2.3),
                new Double(2.3),
                new Double(2.3),
                new Double(2.3),
                new Double(2.3),
                new Double(2.3)
        );
        assertEquals(firstHistoricalQuote, secondHistoricalQuote);
    }

    @Test
    public void shouldNotBeEqualToAnotherHistoricalQuoteWithDifferentValues() {
        HistoricalQuote firstHistoricalQuote = new HistoricalQuote(
                "FB",
                new Date(1463899584953l),
                new Double(2.3),
                new Double(2.3),
                new Double(2.3),
                new Double(2.4),
                new Double(2.3),
                new Double(2.3)
        );
        HistoricalQuote secondHistoricalQuote = new HistoricalQuote(
                "FB",
                new Date(1463899584953l),
                new Double(2.3),
                new Double(2.3),
                new Double(2.3),
                new Double(2.3),
                new Double(2.3),
                new Double(2.3)
        );
        assertNotEquals(firstHistoricalQuote, secondHistoricalQuote);
    }

    @Test
    public void shouldNotBeEqualToAnythingOtherThanHistoricalQuote() {
        HistoricalQuote historicalQuote = new HistoricalQuote(
                "FB",
                new Date(1463899584953l),
                new Double(2.3),
                new Double(2.3),
                new Double(2.3),
                new Double(2.4),
                new Double(2.3),
                new Double(2.3)
        );
        assertNotEquals(historicalQuote, new Object());
    }

    @Test
    public void shouldHaveSameHashCodeIfSameInBusinessDomain() {
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
                "FB",
                new Date(1463899584953l),
                new Double(2.3),
                new Double(2.3),
                new Double(2.3),
                new Double(2.3),
                new Double(2.3),
                new Double(2.3)
        );
        assertEquals(firstHistoricalQuote.hashCode(), secondHistoricalQuote.hashCode());
    }

    @Test
    public void shouldBeEqualToAnotherHistoricalQuoteIfHasSameAttributesAndSameDateNotSpecificAboutTime() {
        HistoricalQuote firstHistoricalQuote = new HistoricalQuote("FB", new Date(1463899584940l), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3));
        HistoricalQuote secondHistoricalQuote = new HistoricalQuote("FB", new Date(1463899584953l), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3));
        assertEquals(firstHistoricalQuote, secondHistoricalQuote);
    }

    @Test
    public void shouldBeLessThanAnotherHistoricalQuoteWhenDateBeforeOthersDate() {
        HistoricalQuote thisHistoricalQuote = new HistoricalQuote("FB", new Date(1463899584940l), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3));
        HistoricalQuote thatHistoricalQuote = new HistoricalQuote("FB", new Date(1463899584953l), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3));
        assertEquals(-1, thisHistoricalQuote.compareTo(thatHistoricalQuote));
    }

    @Test
    public void shouldBeGreaterThanAnotherHistoricalQuoteWhenDateAfterOthersDate() {
        HistoricalQuote thisHistoricalQuote = new HistoricalQuote("FB", new Date(1463899584953l), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3));
        HistoricalQuote thatHistoricalQuote = new HistoricalQuote("FB", new Date(1463899584940l), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3));
        assertEquals(1, thisHistoricalQuote.compareTo(thatHistoricalQuote));
    }

    @Test
    public void shouldBeEqualToAnotherHistoricalQuoteWhenDateEqualToOthersDate() {
        HistoricalQuote thisHistoricalQuote = new HistoricalQuote("FB", new Date(1463899584953l), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3));
        HistoricalQuote thatHistoricalQuote = new HistoricalQuote("FB", new Date(1463899584953l), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3));
        assertEquals(0, thisHistoricalQuote.compareTo(thatHistoricalQuote));
    }

    @Test
    public void testThatHistoricalQuoteKnowsIfItIsFreshWhenItHasTheSameDateAsTheDateItIsComparedWith() {
        HistoricalQuoteDate historicalQuoteDate = HistoricalQuoteDate.fromMilliseconds(MILLISECONDS_UNTIL_20160529_1010HRS);
        HistoricalQuote historicalQuote = new HistoricalQuote("FB", new Date(MILLISECONDS_UNTIL_20160529_1212HRS), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3));
        assertTrue(historicalQuote.isFresh(historicalQuoteDate));
    }

    @Test
    public void testThatHistoricalQuoteKnowsThatItIsFreshWhenItsDayAndComparisonDayAreConsecutiveSaturdayAndSunday() {
        HistoricalQuoteDate historicalQuoteDate = HistoricalQuoteDate.fromMilliseconds(MILLISECONDS_UNTIL_20160529_1010HRS);
        HistoricalQuote historicalQuote = new HistoricalQuote("FB", new Date(MILLISECONDS_UNTIL_20160528_1212HRS), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3));
        assertTrue(historicalQuote.isFresh(historicalQuoteDate));
    }

    @Test
    public void testThatHistoricalQuoteKnowsThatItIsFreshWhenItIsOfFridayAndComparedWithUpcomingSunday() {
        HistoricalQuoteDate historicalQuoteDate = HistoricalQuoteDate.fromMilliseconds(MILLISECONDS_UNTIL_20160529_1010HRS);
        HistoricalQuote historicalQuote = new HistoricalQuote("FB", new Date(MILLISECONDS_UNTIL_20160627_1010HRS), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3));
        assertTrue(historicalQuote.isFresh(historicalQuoteDate));
    }

    @Test
    public void testThatHistoricalQuoteKnowsThatItIsNotFreshWhenItIsOfAnyWeekDayExceptFridayAndComparedWithUpcomingWeekend() {
        HistoricalQuoteDate historicalQuoteDate = HistoricalQuoteDate.fromMilliseconds(MILLISECONDS_UNTIL_20160529_1010HRS);
        HistoricalQuote historicalQuote = new HistoricalQuote("FB", new Date(MILLISECONDS_UNTIL_20160525_1010HRS), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3));
        assertFalse(historicalQuote.isFresh(historicalQuoteDate));
    }

    @Test
    public void testThatHistoricalQuoteKnowsThatItIsNotFreshWhenItHasADifferentDateFromTheDateItIsComparedWith() {
        HistoricalQuoteDate historicalQuoteDate = HistoricalQuoteDate.fromMilliseconds(MILLISECONDS_UNTIL_20160529_1010HRS);
        HistoricalQuote historicalQuote = new HistoricalQuote("FB", new Date(1467108610000l), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3));
        assertFalse(historicalQuote.isFresh(historicalQuoteDate));
    }
}
