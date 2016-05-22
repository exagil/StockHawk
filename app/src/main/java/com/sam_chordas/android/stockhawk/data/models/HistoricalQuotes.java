package com.sam_chordas.android.stockhawk.data.models;

import android.content.ContentValues;
import android.database.Cursor;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

public class HistoricalQuotes {
    private final List<HistoricalQuote> historicalQuotes;

    public HistoricalQuotes(HistoricalQuote... historicalQuotes) {
        this.historicalQuotes = Arrays.asList(historicalQuotes);
    }

    public static HistoricalQuotes fromCursor(Cursor historyCursor) throws ParseException {
        HistoricalQuotes historicalQuotes = new HistoricalQuotes();
        historyCursor.moveToFirst();
        while (historyCursor.moveToNext())
            historicalQuotes.add(HistoricalQuote.fromCursor(historyCursor));
        return historicalQuotes;
    }

    private void add(HistoricalQuote historicalQuote) {
        this.historicalQuotes.add(historicalQuote);
    }

    public ContentValues[] toContentValues() {
        ContentValues[] historicalQuotesContentValues = new ContentValues[historicalQuotes.size()];
        for (int index = 0; index < historicalQuotes.size(); index++)
            historicalQuotesContentValues[index] = historicalQuotes.get(index).toContentValues();
        return historicalQuotesContentValues;
    }
}
