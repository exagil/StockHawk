package com.sam_chordas.android.stockhawk.data.models;

// HistoricalQuoteDate is a helper date used to load historical quotes for specific date intervals

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HistoricalQuoteDate {
    public static final long MILLISECONDS_IN_THIRTY_DAYS = 2592000000l;
    public static final long MILLISECONDS_IN_ONE_DAY = 86400000l;
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
        return toQueryableDateFormat(this).equals(toQueryableDateFormat(that));
    }

    @Override
    public int hashCode() {
        return toQueryableDateFormat(this) != null ? toQueryableDateFormat(this).hashCode() : 0;
    }

    public String queryable() {
        return toQueryableDateFormat(this);
    }

    public HistoricalQuoteDate travelOneMonthBack() {
        return new HistoricalQuoteDate(this.milliseconds - MILLISECONDS_IN_THIRTY_DAYS);
    }

    private String toQueryableDateFormat(HistoricalQuoteDate historicalQuoteDate) {
        String queryableDateFormat;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(historicalQuoteDate.date);
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SATURDAY:
                queryableDateFormat = HISTORICAL_QUOTE_DATE_FORMAT.format(new Date(historicalQuoteDate.milliseconds - MILLISECONDS_IN_ONE_DAY));
                break;
            case Calendar.SUNDAY:
                queryableDateFormat = HISTORICAL_QUOTE_DATE_FORMAT.format(new Date(historicalQuoteDate.milliseconds - 2 * MILLISECONDS_IN_ONE_DAY));
                break;
            default:
                queryableDateFormat = HISTORICAL_QUOTE_DATE_FORMAT.format(historicalQuoteDate.date);
                break;
        }
        return queryableDateFormat;
    }
}
