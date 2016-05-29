package com.sam_chordas.android.stockhawk.data.models;

import android.content.ContentValues;
import android.database.Cursor;

import com.sam_chordas.android.stockhawk.data.HistoryColumns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoricalQuote implements Comparable<HistoricalQuote> {
    public static final SimpleDateFormat PERSISTABLE_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
    public String symbol;
    public Date date;
    public Double open;
    public Double high;
    public Double low;
    public Double close;
    public Double volume;
    public Double adjClose;

    public HistoricalQuote(String symbol, Date date, Double open, Double high, Double low, Double close, Double volume, Double adjClose) {
        this.symbol = symbol;
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.adjClose = adjClose;
    }

    public float dayHigh() {
        return new Float(high);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof HistoricalQuote)) return false;
        HistoricalQuote that = (HistoricalQuote) o;
        return this.symbol.equals(that.symbol) &&
                persistableDate(this.date) == persistableDate(that.date) &&
                this.open.equals(that.open) &&
                this.high.equals(that.high) &&
                this.low.equals(that.low) &&
                this.close.equals(that.close) &&
                this.volume.equals(that.volume) &&
                this.adjClose.equals(that.adjClose);
    }

    @Override
    public int hashCode() {
        int result = symbol != null ? symbol.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (open != null ? open.hashCode() : 0);
        result = 31 * result + (high != null ? high.hashCode() : 0);
        result = 31 * result + (low != null ? low.hashCode() : 0);
        result = 31 * result + (close != null ? close.hashCode() : 0);
        result = 31 * result + (volume != null ? volume.hashCode() : 0);
        result = 31 * result + (adjClose != null ? adjClose.hashCode() : 0);
        return result;
    }

    public ContentValues toContentValues() {
        ContentValues historicalQuoteContentValues = new ContentValues();
        historicalQuoteContentValues.put(HistoryColumns.SYMBOL, symbol);
        historicalQuoteContentValues.put(HistoryColumns.DATE, persistableDate(date));
        historicalQuoteContentValues.put(HistoryColumns.HIGH, high);
        historicalQuoteContentValues.put(HistoryColumns.LOW, low);
        historicalQuoteContentValues.put(HistoryColumns.OPEN, open);
        historicalQuoteContentValues.put(HistoryColumns.CLOSE, close);
        historicalQuoteContentValues.put(HistoryColumns.VOLUME, volume);
        historicalQuoteContentValues.put(HistoryColumns.ADJ_CLOSE, adjClose);
        return historicalQuoteContentValues;
    }

    public static HistoricalQuote fromCursor(Cursor cursor) throws ParseException {
        String symbol = cursor.getString(cursor.getColumnIndex(HistoryColumns.SYMBOL));
        String date = cursor.getString(cursor.getColumnIndex(HistoryColumns.DATE));
        String open = cursor.getString(cursor.getColumnIndex(HistoryColumns.OPEN));
        String high = cursor.getString(cursor.getColumnIndex(HistoryColumns.HIGH));
        String low = cursor.getString(cursor.getColumnIndex(HistoryColumns.LOW));
        String close = cursor.getString(cursor.getColumnIndex(HistoryColumns.CLOSE));
        String volume = cursor.getString(cursor.getColumnIndex(HistoryColumns.VOLUME));
        String adjClose = cursor.getString(cursor.getColumnIndex(HistoryColumns.ADJ_CLOSE));
        return new HistoricalQuote(symbol, PERSISTABLE_DATE_FORMAT.parse(date), new Double(open), new Double(high),
                new Double(low), new Double(close), new Double(volume), new Double(adjClose));
    }

    private long persistableDate(Date date) {
        String formattedPersistableDate = PERSISTABLE_DATE_FORMAT.format(date);
        return Long.parseLong(formattedPersistableDate);
    }

    @Override
    public int compareTo(HistoricalQuote that) {
        if (this.date.before(that.date)) return -1;
        if (this.date.after(that.date)) return 1;
        return 0;
    }
}
