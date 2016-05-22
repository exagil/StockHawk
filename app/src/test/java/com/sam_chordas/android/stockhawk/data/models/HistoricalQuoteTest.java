package com.sam_chordas.android.stockhawk.data.models;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class HistoricalQuoteTest {
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
        HistoricalQuote firstHistoricalQuote = new HistoricalQuote(
                "FB",
                new Date(1463899584940l),
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
}
