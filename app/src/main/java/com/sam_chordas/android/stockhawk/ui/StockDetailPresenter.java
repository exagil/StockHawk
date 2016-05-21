package com.sam_chordas.android.stockhawk.ui;

import com.sam_chordas.android.stockhawk.StockDetailView;
import com.sam_chordas.android.stockhawk.StockNetworkService;
import com.sam_chordas.android.stockhawk.data.StockLoaderService;

public class StockDetailPresenter {
    private StockDetailView stockDetailView;
    private final StockLoaderService stockLoaderService;
    private final StockNetworkService stockNetworkService;

    public StockDetailPresenter(StockDetailView stockDetailView, StockLoaderService stockLoaderService, StockNetworkService stockNetworkService) {
        this.stockDetailView = stockDetailView;
        this.stockLoaderService = stockLoaderService;
        this.stockNetworkService = stockNetworkService;
    }

    public void loadQuoteSymbolForQuoteId(int quoteId) {
        stockLoaderService.loadQuoteSymbolForQuoteId(quoteId, new StockLoaderService.QuoteSymbolLoaderCallback() {
            @Override
            public void onQuoteSymbolLoaded(String quoteSymbol) {
                stockDetailView.onSymbolLoaded(quoteSymbol);
            }

            @Override
            public void onQuoteSymbolLoadFailed() {

            }
        });
    }
}
