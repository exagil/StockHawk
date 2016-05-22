package com.sam_chordas.android.stockhawk.data.models;

import android.content.ContentValues;

import com.sam_chordas.android.stockhawk.data.HistoryColumns;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoricalQuote {
    public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
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

    private String persistableDate(Date date) {
        return new SimpleDateFormat(FORMAT_YYYY_MM_DD).format(date);
    }
}
