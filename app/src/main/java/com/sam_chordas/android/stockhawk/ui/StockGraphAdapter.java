package com.sam_chordas.android.stockhawk.ui;

import com.robinhood.spark.SparkAdapter;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuote;

import java.util.List;

public class StockGraphAdapter extends SparkAdapter {
    private List<HistoricalQuote> historicalQuotes;

    public StockGraphAdapter(List<HistoricalQuote> historicalQuotes) {
        this.historicalQuotes = historicalQuotes;
    }

    @Override
    public int getCount() {
        return (historicalQuotes == null || historicalQuotes.isEmpty()) ? 0 : historicalQuotes.size();
    }

    @Override
    public HistoricalQuote getItem(int index) {
        return historicalQuotes.get(index);
    }

    @Override
    public float getY(int index) {
        HistoricalQuote historicalQuote = getItem(index);
        return historicalQuote.dayHigh();
    }

    public void populate(List<HistoricalQuote> historicalQuotes) {
        this.historicalQuotes = historicalQuotes;
        notifyDataSetChanged();
    }
}
