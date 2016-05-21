package com.sam_chordas.android.stockhawk.data;

import android.content.ContentResolver;
import android.content.Context;

public class StockLoaderService {
    private final ContentResolver contentResolver;

    public StockLoaderService(Context context) {
        contentResolver = context.getContentResolver();
    }

    public String loadQuoteSymbolForQuoteId(int quoteId) {
        return null;
    }
}
