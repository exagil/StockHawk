package com.sam_chordas.android.stockhawk.data.generated.values;

import android.content.ContentValues;

import com.sam_chordas.android.stockhawk.data.HistoryColumns;

public class HistoryValuesBuilder {
    ContentValues values = new ContentValues();

    public HistoryValuesBuilder Id(int value) {
        values.put(HistoryColumns._ID, value);
        return this;
    }

    public HistoryValuesBuilder Id(long value) {
        values.put(HistoryColumns._ID, value);
        return this;
    }

    public HistoryValuesBuilder symbol(String value) {
        values.put(HistoryColumns.SYMBOL, value);
        return this;
    }

    public HistoryValuesBuilder date(String value) {
        values.put(HistoryColumns.DATE, value);
        return this;
    }

    public HistoryValuesBuilder open(float value) {
        values.put(HistoryColumns.OPEN, value);
        return this;
    }

    public HistoryValuesBuilder high(float value) {
        values.put(HistoryColumns.HIGH, value);
        return this;
    }

    public HistoryValuesBuilder low(float value) {
        values.put(HistoryColumns.LOW, value);
        return this;
    }

    public HistoryValuesBuilder close(float value) {
        values.put(HistoryColumns.CLOSE, value);
        return this;
    }

    public HistoryValuesBuilder volume(int value) {
        values.put(HistoryColumns.VOLUME, value);
        return this;
    }

    public HistoryValuesBuilder volume(long value) {
        values.put(HistoryColumns.VOLUME, value);
        return this;
    }

    public HistoryValuesBuilder adjClose(float value) {
        values.put(HistoryColumns.ADJ_CLOSE, value);
        return this;
    }

    public ContentValues values() {
        return values;
    }
}
