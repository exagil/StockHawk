package com.sam_chordas.android.stockhawk.data;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.sam_chordas.android.stockhawk.data.models.HistoricalQuote;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuotes;

// StockProviderService gives access to Stocks in the local storage

public class StockProviderService implements Loader.OnLoadCompleteListener<Cursor> {
    public static final int ID_LOAD_QUOTE_SYMBOL = 1;
    private Context context;
    private QuoteSymbolLoaderCallback quoteSymbolLoaderCallback;
    private CursorLoader cursorLoader;

    public StockProviderService(Context context) {
        this.context = context;
    }

    public void loadQuoteSymbolForQuoteId(final long quoteId, @NonNull final QuoteSymbolLoaderCallback callback) {
        this.quoteSymbolLoaderCallback = callback;
        this.cursorLoader = new CursorLoader(context, QuoteProvider.Quotes.CONTENT_URI,
                new String[]{QuoteColumns.SYMBOL},
                "_id=?",
                new String[]{String.valueOf(quoteId)},
                null
        );
        this.cursorLoader.registerListener(ID_LOAD_QUOTE_SYMBOL, this);
        this.cursorLoader.startLoading();
    }

    @Override
    public void onLoadComplete(Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == ID_LOAD_QUOTE_SYMBOL) {
            if (!data.moveToFirst()) {
                quoteSymbolLoaderCallback.onQuoteSymbolLoadFailed();
                return;
            }
            String symbol = data.getString(data.getColumnIndex(QuoteColumns.SYMBOL));
            quoteSymbolLoaderCallback.onQuoteSymbolLoaded(symbol);
        }
    }

    public void insertHistoricalQuotes(HistoricalQuotes expectedHistoricalQuotes) {
        context.getContentResolver().bulkInsert(HistoryProvider.History.CONTENT_URI, expectedHistoricalQuotes.toContentValues());
    }

    public interface QuoteSymbolLoaderCallback {
        void onQuoteSymbolLoaded(String quoteSymbol);

        void onQuoteSymbolLoadFailed();
    }
}
