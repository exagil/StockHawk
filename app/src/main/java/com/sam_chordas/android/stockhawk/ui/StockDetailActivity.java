package com.sam_chordas.android.stockhawk.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sam_chordas.android.stockhawk.StockDetailView;
import com.sam_chordas.android.stockhawk.StockHawkApp;
import com.sam_chordas.android.stockhawk.StockService;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.StockLoaderService;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuote;

import java.util.List;

import javax.inject.Inject;

public class StockDetailActivity extends AppCompatActivity implements StockDetailView {
    @Inject
    public Context context;
    @Inject
    public StockLoaderService stockLoaderService;
    @Inject
    public StockService stockService;

    private StockDetailPresenter stockDetailPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((StockHawkApp) getApplication()).getStockHawkDependencies().inject(this);
        stockDetailPresenter = new StockDetailPresenter(this, stockLoaderService, stockService);

        long quoteId = getIntent().getLongExtra(QuoteColumns._ID, 0);
        stockDetailPresenter.loadQuoteSymbolForQuoteId(quoteId);
    }

    @Override
    public void onSymbolLoaded(String quoteSymbol) {
        stockDetailPresenter.loadHistoricalQuotes(quoteSymbol);
    }

    @Override
    public void onSymbolLoadFailed() {
    }

    @Override
    public void onHistoricalQuotesLoaded(List<HistoricalQuote> historicalQuotes) {
    }

    @Override
    public void onHistoricalQuotesLoadFailure(String s) {

    }
}
