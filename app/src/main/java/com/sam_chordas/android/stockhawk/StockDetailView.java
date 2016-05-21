package com.sam_chordas.android.stockhawk;

import com.sam_chordas.android.stockhawk.data.models.HistoricalQuote;

import java.util.List;

public interface StockDetailView {
    void onSymbolLoaded(String yhoo);

    void onSymbolLoadFailed();

    void onHistoricalQuotesLoaded(List<HistoricalQuote> historicalQuotes);
}
