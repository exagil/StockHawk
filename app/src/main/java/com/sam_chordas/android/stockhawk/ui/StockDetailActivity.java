package com.sam_chordas.android.stockhawk.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.StockDetailView;
import com.sam_chordas.android.stockhawk.StockHawkApp;
import com.sam_chordas.android.stockhawk.StockService;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.StockProviderService;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuote;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuoteDate;
import com.sam_chordas.android.stockhawk.data.models.Quote;

import java.text.ParseException;
import java.util.ArrayList;
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
    private TextView textError;
    private LineChart graphHistory;
    private ProgressWheel progressWheel;
    private TextView textSymbol;
    private TextView textBidPrice;
    private TextView textChange;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_stock);
        ((StockHawkApp) getApplication()).getStockHawkDependencies().inject(this);
        stockDetailPresenter = new StockDetailPresenter(this, stockProviderService, stockService);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        long quoteId = getIntent().getLongExtra(QuoteColumns._ID, 0);
        graphHistory = (LineChart) findViewById(R.id.graph_history);
        progressWheel = (ProgressWheel) findViewById(R.id.progress_wheel);
        textSymbol = (TextView) findViewById(R.id.text_symbol);
        textBidPrice = (TextView) findViewById(R.id.text_bidPrice);
        textChange = (TextView) findViewById(R.id.text_change);
        textError = (TextView) findViewById(R.id.text_error);
        stockDetailPresenter.loadQuoteSymbolForQuoteId(quoteId);
    }

    @Override
    public void beforeLoad() {
        progressWheel.spin();
    }

    @Override
    public void afterLoad() {
        progressWheel.stopSpinning();
    }

    @Override
    public void onQuoteLoaded(Quote quote) throws ParseException {
        textSymbol.setText(quote.symbol);
        textBidPrice.setText(quote.bidPrice());
        textChange.setText(quote.change());
        stockDetailPresenter.loadOneMonthsHistoricalQuotes(quote.symbol, HistoricalQuoteDate.fromMilliseconds(System.currentTimeMillis()));
    }

    @Override
    public void onQuoteLoadFailed() {
    }

    @Override
    public void onOneMonthsHistoricalQuotesLoaded(List<HistoricalQuote> historicalQuotes) {
        XAxis xAxis = graphHistory.getXAxis();
        LimitLine dateLimitLine = new LimitLine(140f, "Date");
        xAxis.addLimitLine(dateLimitLine);
        YAxis yAxis = graphHistory.getAxisLeft();
        LimitLine historyLimitLine = new LimitLine(140f, "Stock History");
        yAxis.addLimitLine(historyLimitLine);

        List<Entry> stockHistoryEntries = new ArrayList<>();
        List<String> stockDateDataSet = new ArrayList<>();
        for (int historicalQuoteIndex = 0; historicalQuoteIndex < historicalQuotes.size(); historicalQuoteIndex++) {
            HistoricalQuote historicalQuote = historicalQuotes.get(historicalQuoteIndex);
            Entry stockHistoryEntry = new Entry(historicalQuote.dayHigh(), historicalQuoteIndex);
            stockDateDataSet.add(historicalQuote.plottableDate());
            stockHistoryEntries.add(stockHistoryEntry);
        }
        LineDataSet stockHistoryDataSet = new LineDataSet(stockHistoryEntries, "Stock History");
        stockHistoryDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        ArrayList<ILineDataSet> stockHistoryDataSetCollection = new ArrayList<>();
        stockHistoryDataSetCollection.add(stockHistoryDataSet);
        LineData finalData = new LineData(stockDateDataSet, stockHistoryDataSetCollection);
        graphHistory.setData(finalData);
        graphHistory.invalidate();
        afterLoad();
        toggleGraphVisibility(LineChart.VISIBLE, TextView.GONE);
    }

    @Override
    public void onOneMonthsHistoricalQuotesLoadFailure(String error) {
        toggleGraphVisibility(LineChart.GONE, TextView.VISIBLE);
        textError.setText(error);
    }

    private void toggleGraphVisibility(int graphVisibility, int textErrorVisibility) {
        graphHistory.setVisibility(graphVisibility);
        textError.setVisibility(textErrorVisibility);
    }
}
