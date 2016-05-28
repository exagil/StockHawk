package com.sam_chordas.android.stockhawk.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.robinhood.spark.SparkView;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.StockDetailView;
import com.sam_chordas.android.stockhawk.StockHawkApp;
import com.sam_chordas.android.stockhawk.StockService;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.StockProviderService;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuote;

import java.text.ParseException;
import java.util.List;

import javax.inject.Inject;

public class StockDetailActivity extends AppCompatActivity implements StockDetailView {
    @Inject
    public Context context;
    @Inject
    public StockProviderService stockProviderService;
    @Inject
    public StockService stockService;

    private StockDetailPresenter stockDetailPresenter;
    private StockGraphAdapter stockGraphAdapter;
    private TextView textError;
    private SparkView sparkGraphView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_stock);
        ((StockHawkApp) getApplication()).getStockHawkDependencies().inject(this);
        stockDetailPresenter = new StockDetailPresenter(this, stockProviderService, stockService);

        long quoteId = getIntent().getLongExtra(QuoteColumns._ID, 0);
        sparkGraphView = (SparkView) findViewById(R.id.sparkview);
        textError = (TextView) findViewById(R.id.text_error);
        stockGraphAdapter = new StockGraphAdapter(null);
        sparkGraphView.setAdapter(stockGraphAdapter);
        stockDetailPresenter.loadQuoteSymbolForQuoteId(quoteId);
    }

    @Override
    public void onSymbolLoaded(String stockSymbol) throws ParseException {
        stockDetailPresenter.loadHistoricalQuotes(stockSymbol);
    }

    @Override
    public void onSymbolLoadFailed() {
    }

    @Override
    public void onHistoricalQuotesLoaded(List<HistoricalQuote> historicalQuotes) {
        sparkGraphView.setVisibility(SparkView.VISIBLE);
        textError.setVisibility(TextView.GONE);
        stockGraphAdapter.populate(historicalQuotes);
    }

    @Override
    public void onHistoricalQuotesLoadFailure(String error) {
        sparkGraphView.setVisibility(SparkView.GONE);
        textError.setVisibility(TextView.VISIBLE);
        textError.setText(error);
    }
}
