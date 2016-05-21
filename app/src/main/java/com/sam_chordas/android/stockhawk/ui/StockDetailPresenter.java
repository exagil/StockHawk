package com.sam_chordas.android.stockhawk.ui;

import com.sam_chordas.android.stockhawk.StockDetailView;
import com.sam_chordas.android.stockhawk.StockService;
import com.sam_chordas.android.stockhawk.data.StockLoaderService;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuote;

import java.util.List;

public class StockDetailPresenter {
    private StockDetailView stockDetailView;
    private final StockLoaderService stockLoaderService;
    private final StockService stockService;

    public StockDetailPresenter(StockDetailView stockDetailView, StockLoaderService stockLoaderService, StockService stockService) {
        this.stockDetailView = stockDetailView;
        this.stockLoaderService = stockLoaderService;
        this.stockService = stockService;
    }

    public void loadQuoteSymbolForQuoteId(int quoteId) {
        stockLoaderService.loadQuoteSymbolForQuoteId(quoteId, new StockLoaderService.QuoteSymbolLoaderCallback() {
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
            @Override
            public void onHistoricalQuotesLoaded(List<HistoricalQuote> historicalQuotes) {
                stockDetailView.onHistoricalQuotesLoaded(historicalQuotes);
            }
        });
    }
}
