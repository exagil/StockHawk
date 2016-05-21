package com.sam_chordas.android.stockhawk.data;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;

public class StockLoaderService {
    private final ContentResolver contentResolver;

    public StockLoaderService(Context context) {
        contentResolver = context.getContentResolver();
    }

    public String loadQuoteSymbolForQuoteId(int quoteId) {
        Cursor quoteSymbolQuery = contentResolver.query(QuoteProvider.Quotes.CONTENT_URI,
                new String[]{QuoteColumns.SYMBOL},
                "_id=?",
                new String[]{String.valueOf(quoteId)},
                null
        );
        if (!quoteSymbolQuery.moveToFirst())
            return null;
        return quoteSymbolQuery.getString(quoteSymbolQuery.getColumnIndex(QuoteColumns.SYMBOL));
    }
}
