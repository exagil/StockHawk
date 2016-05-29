package com.sam_chordas.android.stockhawk.data.models;

// HistoricalQuoteDate is a helper date used to load historical quotes for specific date intervals

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HistoricalQuoteDate {
    private static final long MILLISECONDS_IN_THIRTY_DAYS = 2592000000l;
    private static final long MILLISECONDS_IN_ONE_DAY = 86400000l;
    private final Date date;
    private SimpleDateFormat QUERYABLE_HISTORICAL_QUOTE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat PERSISTABLE_HISTORICAL_QUOTE_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
    private long milliseconds;

    public static HistoricalQuoteDate fromMilliseconds(long milliseconds) {
        return new HistoricalQuoteDate(milliseconds);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof HistoricalQuoteDate)) return false;
        HistoricalQuoteDate that = (HistoricalQuoteDate) o;
        return toDateFormat(this, QUERYABLE_HISTORICAL_QUOTE_DATE_FORMAT).equals(toDateFormat(that, QUERYABLE_HISTORICAL_QUOTE_DATE_FORMAT));
    }

    @Override
    public int hashCode() {
        return toDateFormat(this, QUERYABLE_HISTORICAL_QUOTE_DATE_FORMAT) != null ? toDateFormat(this, QUERYABLE_HISTORICAL_QUOTE_DATE_FORMAT).hashCode() : 0;
    }

    public String queryable() {
        return toDateFormat(this, QUERYABLE_HISTORICAL_QUOTE_DATE_FORMAT);
    }

    public HistoricalQuoteDate travelOneMonthBack() {
        return new HistoricalQuoteDate(this.milliseconds - MILLISECONDS_IN_THIRTY_DAYS);
    }

    public String persistable() {
        return toDateFormat(this, PERSISTABLE_HISTORICAL_QUOTE_DATE_FORMAT);
    }

    private HistoricalQuoteDate(long milliseconds) {
        this.milliseconds = milliseconds;
        this.date = new Date(milliseconds);
    }

    private String toDateFormat(HistoricalQuoteDate historicalQuoteDate, SimpleDateFormat dateFormat) {
        String formattedDate;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(historicalQuoteDate.date);
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SATURDAY:
                formattedDate = dateFormat.format(new Date(historicalQuoteDate.milliseconds - MILLISECONDS_IN_ONE_DAY));
                break;
            case Calendar.SUNDAY:
                formattedDate = dateFormat.format(new Date(historicalQuoteDate.milliseconds - 2 * MILLISECONDS_IN_ONE_DAY));
                break;
            default:
                formattedDate = dateFormat.format(historicalQuoteDate.date);
                break;
        }
        return formattedDate;
    }
}
