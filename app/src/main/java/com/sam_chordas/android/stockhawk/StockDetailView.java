package com.sam_chordas.android.stockhawk;

import com.sam_chordas.android.stockhawk.data.models.HistoricalQuote;

import java.text.ParseException;
import java.util.List;

public interface StockDetailView {
    void onSymbolLoaded(String stockSymbol) throws ParseException;

    void onSymbolLoadFailed();

    void onHistoricalQuotesLoaded(List<HistoricalQuote> historicalQuotes);

    void onHistoricalQuotesLoadFailure(String error);
}
