package com.sam_chordas.android.stockhawk.data.models;

import android.content.Context;

import com.sam_chordas.android.stockhawk.R;

public class QuoteViewModel {
    private final Context context;
    private Quote quote;

    public QuoteViewModel(Context context, Quote quote) {
        this.context = context;
        this.quote = quote;
    }

    public String bidPrice() {
        return String.format(context.getString(R.string.quote_bid_price), this.quote.bidPrice);
    }

    public String symbol() {
        return this.quote.symbol;
    }

    public String percentChange() {
        return String.format(context.getString(R.string.quote_percent_change), this.quote.percentChange);
    }
}
