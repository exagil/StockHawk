package com.sam_chordas.android.stockhawk.ui;

import com.sam_chordas.android.stockhawk.StockNetworkService;
import com.sam_chordas.android.stockhawk.data.StockLoaderService;

public class StockDetailPresenter {
    private final StockLoaderService stockLoaderService;
    private final StockNetworkService stockNetworkService;

    public StockDetailPresenter(StockLoaderService stockLoaderService, StockNetworkService stockNetworkService) {
        this.stockLoaderService = stockLoaderService;
        this.stockNetworkService = stockNetworkService;
    }
}
