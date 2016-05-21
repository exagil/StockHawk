package com.sam_chordas.android.stockhawk.ui;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sam_chordas.android.stockhawk.StockDetailView;
import com.sam_chordas.android.stockhawk.StockHawkApp;
import com.sam_chordas.android.stockhawk.StockNetworkService;
import com.sam_chordas.android.stockhawk.StockService;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;
import com.sam_chordas.android.stockhawk.data.StockLoaderService;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuote;

import java.util.List;

import javax.inject.Inject;

public class StockDetailActivity extends AppCompatActivity implements StockDetailView {
    @Inject
    public StockNetworkService stockNetworkService;
    @Inject
    public Context context;
    private StockDetailPresenter stockDetailPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((StockHawkApp) getApplication()).getStockHawkDependencies().inject(this);
        long quoteId = getIntent().getLongExtra(QuoteColumns._ID, 0);
        Cursor quoteCursor = getApplicationContext().getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI, null, "_id=?", new String[]{String.valueOf(quoteId)}, null);
        if (doesQuoteNotExist(quoteCursor))
            return;
        quoteCursor.moveToFirst();
        String quoteSymbol = quoteCursor.getString(quoteCursor.getColumnIndex(QuoteColumns.SYMBOL));
        StockLoaderService stockLoaderService = new StockLoaderService(context);
        StockService stockService = new StockService(stockNetworkService);
        stockDetailPresenter = new StockDetailPresenter(this, stockLoaderService, stockService);
    }

    private boolean doesQuoteNotExist(Cursor quote) {
        return quote.getCount() == 0;
    }

    @Override
    public void onSymbolLoaded(String quoteSymbol) {
    }

    @Override
    public void onSymbolLoadFailed() {

    }

    @Override
    public void onHistoricalQuotesLoaded(List<HistoricalQuote> historicalQuotes) {

    }
}
