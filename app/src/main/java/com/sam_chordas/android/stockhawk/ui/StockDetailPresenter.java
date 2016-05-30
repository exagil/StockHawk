package com.sam_chordas.android.stockhawk.ui;

import com.sam_chordas.android.stockhawk.StockDetailView;
import com.sam_chordas.android.stockhawk.StockService;
import com.sam_chordas.android.stockhawk.data.StockProviderService;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuote;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuoteDate;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuotes;
import com.sam_chordas.android.stockhawk.data.models.NetworkError;
import com.sam_chordas.android.stockhawk.data.models.Quote;

import java.text.ParseException;
import java.util.List;

public class StockDetailPresenter {
    private StockDetailView stockDetailView;
    private final StockProviderService stockProviderService;
    private final StockService stockService;

    public StockDetailPresenter(StockDetailView stockDetailView, StockProviderService stockProviderService, StockService stockService) {
        this.stockDetailView = stockDetailView;
        this.stockProviderService = stockProviderService;
        this.stockService = stockService;
    }

    public void loadQuoteSymbolForQuoteId(long quoteId) {
        stockDetailView.beforeLoad();
        stockProviderService.loadQuoteWithId(quoteId, new StockProviderService.QuoteLoaderCallback() {
            @Override
            public void onQuoteLoaded(Quote quote) {
                // TODO: 25/05/16 Chirag - Not a good idea to handle exception here
                try {
                    stockDetailView.afterLoad();
                    stockDetailView.onQuoteLoaded(quote);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onQuoteLoadFailed() {
                stockDetailView.afterLoad();
                stockDetailView.onQuoteLoadFailed();
            }
        });
    }

    public void loadOneMonthsHistoricalQuotes(String stockSymbol, HistoricalQuoteDate historicalQuoteDate) throws ParseException {
        stockDetailView.beforeLoad();
        HistoricalQuotes historicalQuotes = stockProviderService.loadOneMonthsHistoricalQuotes(stockSymbol, historicalQuoteDate);
        if (!historicalQuotes.isEmpty() && historicalQuotes.areFresh(historicalQuoteDate)) {
            stockDetailView.onOneMonthsHistoricalQuotesLoaded(historicalQuotes.collection);
            return;
        }
        stockService.loadOneMonthsHistoricalQuotes(stockSymbol, historicalQuoteDate, new StockService.HistoricalQuotesCallback() {
            public void onHistoricalQuotesLoaded(List<HistoricalQuote> historicalQuotes) {
                stockDetailView.onOneMonthsHistoricalQuotesLoaded(historicalQuotes);
            }

            @Override
            public void onOneMonthsHistoricalQuotesLoadFailure(NetworkError networkError) {
                stockDetailView.afterLoad();
                stockDetailView.onOneMonthsHistoricalQuotesLoadFailure(networkError.error());
            }
        });
    }
}
