package com.sam_chordas.android.stockhawk.data.models;

import java.util.Date;

public class HistoricalQuote {
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
}
