package com.sam_chordas.android.stockhawk.data.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import rx.Observable;

public class HistoricalQuotes {
    // TODO: 25/05/16 Chirag - Encapsulate field - collection
    @NonNull
    public final List<HistoricalQuote> collection;

    public HistoricalQuotes(@NonNull HistoricalQuote... collection) {
        this(Arrays.asList(collection));
        Observable.from(collection).toSortedList();
    }

    public HistoricalQuotes(@NonNull List<HistoricalQuote> collection) {
        this.collection = new ArrayList<>();
        this.collection.addAll(collection);
    }

    public static HistoricalQuotes fromCursor(Cursor historyCursor) throws ParseException {
        if (historyCursor.getCount() == 0) return new NullHistoricalQuotes();
        HistoricalQuotes historicalQuotes = new HistoricalQuotes();
        historyCursor.moveToFirst();
        for (int index = 0; index < historyCursor.getCount(); index++, historyCursor.moveToNext())
            historicalQuotes.add(HistoricalQuote.fromCursor(historyCursor));
        historyCursor.close();
        return historicalQuotes;
    }

    public ContentValues[] toContentValues() {
        ContentValues[] historicalQuotesContentValues = new ContentValues[collection.size()];
        for (int index = 0; index < collection.size(); index++)
            historicalQuotesContentValues[index] = collection.get(index).toContentValues();
        return historicalQuotesContentValues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HistoricalQuotes that = (HistoricalQuotes) o;

        return collection != null ? collection.equals(that.collection) : that.collection == null;

    }

    @Override
    public int hashCode() {
        return collection != null ? collection.hashCode() : 0;
    }

    public boolean isEmpty() {
        return collection.isEmpty();
    }

    public List<HistoricalQuote> sortedCollection() {
        ArrayList<HistoricalQuote> historicalQuotes = new ArrayList<>(collection);
        Collections.sort(historicalQuotes);
        return historicalQuotes;
    }

    private void add(@NonNull HistoricalQuote historicalQuote) {
        this.collection.add(historicalQuote);
    }

    public boolean areFresh(HistoricalQuoteDate historicalQuoteDate) {
        HistoricalQuote lastHistoricalQuote = sortedCollection().get(lastHistoricalQuoteIndex());
        return lastHistoricalQuote.isFresh(historicalQuoteDate);
    }

    private int lastHistoricalQuoteIndex() {
        return collection.size() - 1;
    }
}
