package com.sam_chordas.android.stockhawk;

public interface StockDetailView {
    void onSymbolLoaded(String yhoo);

    void onSymbolLoadFailed();
}
