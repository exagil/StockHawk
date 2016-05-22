package com.sam_chordas.android.stockhawk.ui;

import com.sam_chordas.android.stockhawk.StockDetailView;
import com.sam_chordas.android.stockhawk.StockService;
import com.sam_chordas.android.stockhawk.data.StockProviderService;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuote;
import com.sam_chordas.android.stockhawk.data.models.NetworkError;

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
        stockProviderService.loadQuoteSymbolForQuoteId(quoteId, new StockProviderService.QuoteSymbolLoaderCallback() {
            @Override
            public void onQuoteSymbolLoaded(String quoteSymbol) {
                stockDetailView.onSymbolLoaded(quoteSymbol);
            }

            @Override
            public void onQuoteSymbolLoadFailed() {
                stockDetailView.onSymbolLoadFailed();
            }
        });
    }

    public void loadHistoricalQuotes(String stockSymbol) {
        stockService.loadHistoricalQuotes(stockSymbol, new StockService.HistoricalQuotesCallback() {
            public void onHistoricalQuotesLoaded(List<HistoricalQuote> historicalQuotes) {
                stockDetailView.onHistoricalQuotesLoaded(historicalQuotes);
            }

            @Override
            public void onHistoricalQuotesLoadFailure(NetworkError networkError) {
                stockDetailView.onHistoricalQuotesLoadFailure(networkError.error());
            }
        });
    }
}
