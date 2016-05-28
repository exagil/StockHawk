package com.sam_chordas.android.stockhawk.widget;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sam_chordas.android.stockhawk.BuildConfig;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.generated.QuoteProvider;
import com.sam_chordas.android.stockhawk.utils.Utils;

public class StocksWidgetFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    private Cursor stocksCursor;

    public StocksWidgetFactory(Context context, Cursor stocksCursor) {
        this.context = context;
        this.stocksCursor = stocksCursor;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        if (stocksCursor != null) {
            stocksCursor.close();
        }
        stocksCursor = context.getContentResolver().query(
                QuoteProvider.Quotes.CONTENT_URI,
                null,
                QuoteColumns.ISCURRENT + "=?",
                new String[]{"1"},
                null
        );
    }

    @Override
    public void onDestroy() {
        if (stocksCursor != null) {
            stocksCursor.close();
        }
    }

    @Override
    public int getCount() {
        return stocksCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        stocksCursor.moveToPosition(position);
        String stockSymbol = stocksCursor.getString(stocksCursor.getColumnIndex(QuoteColumns.SYMBOL));
        String bidPrice = stocksCursor.getString(stocksCursor.getColumnIndex(QuoteColumns.BIDPRICE));
        String percentChange = stocksCursor.getString(stocksCursor.getColumnIndex(QuoteColumns.PERCENT_CHANGE));
        String change = stocksCursor.getString(stocksCursor.getColumnIndex(QuoteColumns.CHANGE));

        RemoteViews remoteViewStocksListItem = new RemoteViews(context.getPackageName(), R.layout.widget_stocks_list_item);
        remoteViewStocksListItem.setTextViewText(R.id.stock_symbol, stockSymbol);
        remoteViewStocksListItem.setTextViewText(R.id.bid_price, bidPrice);
        if (Utils.showPercent) {
            remoteViewStocksListItem.setTextViewText(R.id.change, percentChange);
        } else {
            remoteViewStocksListItem.setTextViewText(R.id.change, change);
        }
        return remoteViewStocksListItem;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
