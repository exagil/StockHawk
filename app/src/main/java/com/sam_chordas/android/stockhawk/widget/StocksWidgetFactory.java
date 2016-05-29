package com.sam_chordas.android.stockhawk.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

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
        long quoteId = stocksCursor.getLong(stocksCursor.getColumnIndex(QuoteColumns._ID));

        String stockSymbolContentDescription = String.format(context.getString(R.string.stock_symbol), stockSymbol);
        String bidPriceContentDescription = String.format(context.getString(R.string.bid_price), bidPrice);

        RemoteViews remoteViewStocksListItem = new RemoteViews(context.getPackageName(), R.layout.widget_stocks_list_item);
        remoteViewStocksListItem.setTextViewText(R.id.stock_symbol, stockSymbol);
        remoteViewStocksListItem.setContentDescription(R.id.stock_symbol, stockSymbolContentDescription);
        remoteViewStocksListItem.setTextViewText(R.id.bid_price, bidPrice);
        remoteViewStocksListItem.setContentDescription(R.id.bid_price, bidPriceContentDescription);
        if (Utils.showPercent) {
            remoteViewStocksListItem.setTextViewText(R.id.change, percentChange);
            String percetChangeContentDescription = String.format(context.getString(R.string.percent_change), percentChange);
            remoteViewStocksListItem.setContentDescription(R.id.change, percetChangeContentDescription);
        } else {
            remoteViewStocksListItem.setTextViewText(R.id.change, change);
            String changeContentDescription = String.format(context.getString(R.string.change), change);
            remoteViewStocksListItem.setContentDescription(R.id.change, changeContentDescription);
        }
        Intent fillInIntent = new Intent();
        fillInIntent.putExtra(QuoteColumns._ID, quoteId);
        remoteViewStocksListItem.setOnClickFillInIntent(R.id.widget_stocks_list_item, fillInIntent);
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
