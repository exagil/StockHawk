package com.sam_chordas.android.stockhawk.data.models;

import android.content.ContentValues;

import java.util.List;

public class NullHistoricalQuotes extends HistoricalQuotes {
    public NullHistoricalQuotes() {
    }

    public NullHistoricalQuotes(HistoricalQuote... historicalQuotes) {
    }

    public NullHistoricalQuotes(List<HistoricalQuote> historicalQuotes) {
    }

    @Override
    public ContentValues[] toContentValues() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public HistoricalQuotes sort() {
        return new NullHistoricalQuotes();
    }
}
