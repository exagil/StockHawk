package com.sam_chordas.android.stockhawk;

import com.sam_chordas.android.stockhawk.data.models.HistoricalQuote;
import com.sam_chordas.android.stockhawk.data.models.Quote;

import java.text.ParseException;
import java.util.List;

public interface StockDetailView {
    void beforeLoad();

    void afterLoad();

    void onQuoteLoaded(Quote quote) throws ParseException;

    void onQuoteLoadFailed();

    void onOneMonthsHistoricalQuotesLoaded(List<HistoricalQuote> historicalQuotes);

    void onOneMonthsHistoricalQuotesLoadFailure(String error);
}
