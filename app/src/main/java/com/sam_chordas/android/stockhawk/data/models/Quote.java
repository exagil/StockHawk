package com.sam_chordas.android.stockhawk.data.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.sam_chordas.android.stockhawk.data.QuoteColumns;

public class Quote {
    public final String symbol;
    private final Float percentChange;
    private final Float change;
    private final Double bidPrice;
    private final PersistableBoolean isUp;
    private final PersistableBoolean isCurrent;

    public Quote(@NonNull String symbol, @NonNull Float percentChange, @NonNull Float change, @NonNull Double bidPrice, @NonNull PersistableBoolean isUp, @NonNull PersistableBoolean isCurrent) {
        this.symbol = symbol;
        this.percentChange = percentChange;
        this.change = change;
        this.bidPrice = bidPrice;
        this.isUp = isUp;
        this.isCurrent = isCurrent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quote quote = (Quote) o;

        if (symbol != null ? !symbol.equals(quote.symbol) : quote.symbol != null) return false;
        if (percentChange != null ? !percentChange.equals(quote.percentChange) : quote.percentChange != null)
            return false;
        if (change != null ? !change.equals(quote.change) : quote.change != null) return false;
        if (bidPrice != null ? !bidPrice.equals(quote.bidPrice) : quote.bidPrice != null)
            return false;
        if (isUp != quote.isUp) return false;
        return isCurrent == quote.isCurrent;

    }

    @Override
    public int hashCode() {
        int result = symbol != null ? symbol.hashCode() : 0;
        result = 31 * result + (percentChange != null ? percentChange.hashCode() : 0);
        result = 31 * result + (change != null ? change.hashCode() : 0);
        result = 31 * result + (bidPrice != null ? bidPrice.hashCode() : 0);
        result = 31 * result + (isUp != null ? isUp.hashCode() : 0);
        result = 31 * result + (isCurrent != null ? isCurrent.hashCode() : 0);
        return result;
    }

    public ContentValues toContentValues() {
        ContentValues quoteContentValues = new ContentValues();
        quoteContentValues.put(QuoteColumns.SYMBOL, this.symbol);
        quoteContentValues.put(QuoteColumns.PERCENT_CHANGE, String.valueOf(this.percentChange));
        quoteContentValues.put(QuoteColumns.CHANGE, String.valueOf(this.change));
        quoteContentValues.put(QuoteColumns.BIDPRICE, String.valueOf(this.bidPrice));
        quoteContentValues.put(QuoteColumns.ISUP, isUp.value());
        quoteContentValues.put(QuoteColumns.ISCURRENT, isCurrent.value());
        return quoteContentValues;
    }

    public static Quote fromContentValues(ContentValues contentValues) {
        String symbol = contentValues.getAsString(QuoteColumns.SYMBOL);
        Float percentChange = contentValues.getAsFloat(QuoteColumns.PERCENT_CHANGE);
        Float change = contentValues.getAsFloat(QuoteColumns.CHANGE);
        Double bidPrice = contentValues.getAsDouble(QuoteColumns.BIDPRICE);
        Integer isUpPersistableBoolean = contentValues.getAsInteger(QuoteColumns.ISUP);
        Integer isCurrentPersistableBoolean = contentValues.getAsInteger(QuoteColumns.ISCURRENT);
        PersistableBoolean isUp = PersistableBoolean.parse(isUpPersistableBoolean);
        PersistableBoolean isCurrent = PersistableBoolean.parse(isCurrentPersistableBoolean);
        return new Quote(symbol, percentChange, change, bidPrice, isUp, isCurrent);
    }

    public static Quote fromCursor(@NonNull Cursor quoteCursor) {
        String symbol = quoteCursor.getString(quoteCursor.getColumnIndex(QuoteColumns.SYMBOL));
        Float percentChange = quoteCursor.getFloat(quoteCursor.getColumnIndex(QuoteColumns.PERCENT_CHANGE));
        Float change = quoteCursor.getFloat(quoteCursor.getColumnIndex(QuoteColumns.CHANGE));
        Double bidPrice = quoteCursor.getDouble(quoteCursor.getColumnIndex(QuoteColumns.BIDPRICE));
        Integer isUpPersistableBoolean = quoteCursor.getInt(quoteCursor.getColumnIndex(QuoteColumns.ISUP));
        Integer isCurrentPersistableBoolean = quoteCursor.getInt(quoteCursor.getColumnIndex(QuoteColumns.ISCURRENT));
        PersistableBoolean isUp = PersistableBoolean.parse(isUpPersistableBoolean);
        PersistableBoolean isCurrent = PersistableBoolean.parse(isCurrentPersistableBoolean);
        return new Quote(symbol, percentChange, change, bidPrice, isUp, isCurrent);
    }
}
