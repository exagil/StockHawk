package com.sam_chordas.android.stockhawk.data.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HistoricalQuotes {
    @NonNull
    private final List<HistoricalQuote> historicalQuotes;

    public HistoricalQuotes(@NonNull HistoricalQuote... historicalQuotes) {
        this(Arrays.asList(historicalQuotes));
    }

    public HistoricalQuotes(@NonNull List<HistoricalQuote> historicalQuotes) {
        this.historicalQuotes = new ArrayList<>();
        this.historicalQuotes.addAll(historicalQuotes);
    }

    public static HistoricalQuotes fromCursor(Cursor historyCursor) throws ParseException {
        if (historyCursor.getCount() == 0) return new NullHistoricalQuotes();
        HistoricalQuotes historicalQuotes = new HistoricalQuotes();
        historyCursor.moveToFirst();
        for (int index = 0; index < historyCursor.getCount(); index++, historyCursor.moveToNext())
            historicalQuotes.add(HistoricalQuote.fromCursor(historyCursor));
        return historicalQuotes;
    }

    private void add(@NonNull HistoricalQuote historicalQuote) {
        this.historicalQuotes.add(historicalQuote);
    }

    public ContentValues[] toContentValues() {
        ContentValues[] historicalQuotesContentValues = new ContentValues[historicalQuotes.size()];
        for (int index = 0; index < historicalQuotes.size(); index++)
            historicalQuotesContentValues[index] = historicalQuotes.get(index).toContentValues();
        return historicalQuotesContentValues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HistoricalQuotes that = (HistoricalQuotes) o;

        return historicalQuotes != null ? historicalQuotes.equals(that.historicalQuotes) : that.historicalQuotes == null;

    }

    @Override
    public int hashCode() {
        return historicalQuotes != null ? historicalQuotes.hashCode() : 0;
    }

    public boolean isEmpty() {
        return historicalQuotes.isEmpty();
    }
}
