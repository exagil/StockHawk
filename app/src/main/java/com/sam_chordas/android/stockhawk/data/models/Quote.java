package com.sam_chordas.android.stockhawk.data.models;

import android.content.ContentValues;

import com.sam_chordas.android.stockhawk.data.QuoteColumns;

import java.util.Date;
import java.util.StringTokenizer;

public class Quote {
    private final String symbol;
    private final Float percentChange;
    private final Float change;
    private final Double bidPrice;
    private final Date createdAt;
    private final boolean isUp;
    private final boolean isCurrent;

    public Quote(String symbol, Float percentChange, Float change, Double bidPrice, Date createdAt, boolean isUp, boolean isCurrent) {
        this.symbol = symbol;
        this.percentChange = percentChange;
        this.change = change;
        this.bidPrice = bidPrice;
        this.createdAt = createdAt;
        this.isUp = isUp;
        this.isCurrent = isCurrent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quote quote = (Quote) o;

        if (isUp != quote.isUp) return false;
        if (isCurrent != quote.isCurrent) return false;
        if (symbol != null ? !symbol.equals(quote.symbol) : quote.symbol != null) return false;
        if (percentChange != null ? !percentChange.equals(quote.percentChange) : quote.percentChange != null)
            return false;
        if (change != null ? !change.equals(quote.change) : quote.change != null) return false;
        if (bidPrice != null ? !bidPrice.equals(quote.bidPrice) : quote.bidPrice != null)
            return false;
        return createdAt != null ? createdAt.equals(quote.createdAt) : quote.createdAt == null;

    }

    @Override
    public int hashCode() {
        int result = symbol != null ? symbol.hashCode() : 0;
        result = 31 * result + (percentChange != null ? percentChange.hashCode() : 0);
        result = 31 * result + (change != null ? change.hashCode() : 0);
        result = 31 * result + (bidPrice != null ? bidPrice.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (isUp ? 1 : 0);
        result = 31 * result + (isCurrent ? 1 : 0);
        return result;
    }

    public ContentValues toContentValues() {
        ContentValues quoteContentValues = new ContentValues();
        quoteContentValues.put(QuoteColumns.SYMBOL, this.symbol);
        quoteContentValues.put(QuoteColumns.PERCENT_CHANGE, String.valueOf(this.percentChange));
        quoteContentValues.put(QuoteColumns.CHANGE, String.valueOf(this.change));
        quoteContentValues.put(QuoteColumns.BIDPRICE, String.valueOf(this.bidPrice));
        quoteContentValues.put(QuoteColumns.ISUP, persistableBoolean(isUp));
        quoteContentValues.put(QuoteColumns.ISCURRENT, persistableBoolean(isCurrent));
        return quoteContentValues;
    }

    private int persistableBoolean(boolean booleanField) {
        return booleanField == true ? 1 : 0;
    }
}
