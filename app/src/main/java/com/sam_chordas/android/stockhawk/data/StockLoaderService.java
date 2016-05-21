package com.sam_chordas.android.stockhawk.data;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;

public class StockLoaderService {
    private final ContentResolver contentResolver;
    private Context context;

    public StockLoaderService(Context context) {
        contentResolver = context.getContentResolver();
    }

    public void loadQuoteSymbolForQuoteId(final long quoteId, final QuoteSymbolLoaderCallback callback) {
        final Cursor symbolCursor = contentResolver.query(QuoteProvider.Quotes.CONTENT_URI,
                new String[]{QuoteColumns.SYMBOL},
                "_id=?",
                new String[]{String.valueOf(quoteId)},
                null
        );
        if (!symbolCursor.moveToFirst()) {
            callback.onQuoteSymbolLoadFailed();
            return;
        }
        symbolCursor.moveToFirst();
        String symbol = symbolCursor.getString(symbolCursor.getColumnIndex(QuoteColumns.SYMBOL));
        callback.onQuoteSymbolLoaded(symbol);
    }

    public interface QuoteSymbolLoaderCallback {
        void onQuoteSymbolLoaded(String quoteSymbol);

        void onQuoteSymbolLoadFailed();
    }
}
