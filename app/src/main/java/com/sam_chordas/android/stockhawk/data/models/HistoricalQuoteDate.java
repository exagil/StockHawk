package com.sam_chordas.android.stockhawk.data.models;

// HistoricalQuoteDate is a helper date used to load historical quotes for specific date intervals

import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoricalQuoteDate {
    private final Date date;
    private SimpleDateFormat HISTORICAL_QUOTE_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    public static HistoricalQuoteDate fromMilliseconds(long milliseconds) {
        return new HistoricalQuoteDate(milliseconds);
    }

    private HistoricalQuoteDate(long milliseconds) {
        this.date = new Date(milliseconds);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof HistoricalQuoteDate)) return false;
        HistoricalQuoteDate that = (HistoricalQuoteDate) o;
        return HISTORICAL_QUOTE_DATE_FORMAT.format(this.date).equals(HISTORICAL_QUOTE_DATE_FORMAT.format(that.date));
    }

    @Override
    public int hashCode() {
        return HISTORICAL_QUOTE_DATE_FORMAT.format(this.date) != null ? HISTORICAL_QUOTE_DATE_FORMAT.format(this.date).hashCode() : 0;
    }
}
