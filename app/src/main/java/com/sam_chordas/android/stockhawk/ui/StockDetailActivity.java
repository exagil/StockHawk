package com.sam_chordas.android.stockhawk.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;

public class StockDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long quoteId = getIntent().getLongExtra(QuoteColumns._ID, 0);
        Cursor quoteCursor = getApplicationContext().getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI, null, "_id=?", new String[]{String.valueOf(quoteId)}, null);
        StockDetailPresenter stockDetailPresenter = new StockDetailPresenter();
        if (doesQuoteNotExist(quoteCursor))
            return;
        quoteCursor.moveToFirst();
        String quoteSymbol = quoteCursor.getString(quoteCursor.getColumnIndex(QuoteColumns.SYMBOL));
    }

    private boolean doesQuoteNotExist(Cursor quote) {
        return quote.getCount() == 0;
    }
}
