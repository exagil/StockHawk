package com.sam_chordas.android.stockhawk.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViewsService;

import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.generated.QuoteProvider;

public class StocksWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Context context = getApplicationContext();
        Cursor currentQuotesCursor = context.getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI, null, QuoteColumns.ISCURRENT + "=?", new String[]{"1"}, null);
        return new StocksWidgetFactory(context, currentQuotesCursor);
    }
}
