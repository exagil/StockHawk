package com.sam_chordas.android.stockhawk.data.models;

// HistoricalQuoteDate is a helper date used to load historical quotes for specific date intervals

import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoricalQuoteDate {
    public static final long MILLISECONDS_IN_THIRTY_DAYS = 2592000000l;
    private final Date date;
    private SimpleDateFormat HISTORICAL_QUOTE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private long milliseconds;

    public static HistoricalQuoteDate fromMilliseconds(long milliseconds) {
        return new HistoricalQuoteDate(milliseconds);
    }

    private HistoricalQuoteDate(long milliseconds) {
        this.milliseconds = milliseconds;
        this.date = new Date(milliseconds);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof HistoricalQuoteDate)) return false;
        HistoricalQuoteDate that = (HistoricalQuoteDate) o;
        return toQueryableDateFormat(this.date).equals(toQueryableDateFormat(that.date));
    }

    @Override
    public int hashCode() {
        return toQueryableDateFormat(this.date) != null ? toQueryableDateFormat(this.date).hashCode() : 0;
    }

    public String queryable() {
        return toQueryableDateFormat(this.date);
    }

    public HistoricalQuoteDate travelOneMonthBack() {
        return new HistoricalQuoteDate(this.milliseconds - MILLISECONDS_IN_THIRTY_DAYS);
    }

    private String toQueryableDateFormat(Date date) {
        return HISTORICAL_QUOTE_DATE_FORMAT.format(date);
    }
}
