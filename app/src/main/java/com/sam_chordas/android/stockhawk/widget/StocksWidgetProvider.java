package com.sam_chordas.android.stockhawk.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.widget.RemoteViews;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.generated.QuoteProvider;

public class StocksWidgetProvider extends AppWidgetProvider {
    private final HandlerThread handlerThread;
    private final Handler workerQueue;

    public StocksWidgetProvider() {
        handlerThread = new HandlerThread("stock_handler_thread");
        handlerThread.start();
        workerQueue = new Handler(handlerThread.getLooper());
    }

    private class StockWidgetProviderObserver extends ContentObserver {
        private final AppWidgetManager appWidgetManager;
        private final ComponentName componentName;

        public StockWidgetProviderObserver(AppWidgetManager appWidgetManager, ComponentName componentName, Handler handler) {
            super(handler);
            this.appWidgetManager = appWidgetManager;
            this.componentName = componentName;
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetManager.getAppWidgetIds(componentName), R.id.list_stocks);
        }
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName componentName = new ComponentName(context, StocksWidgetProvider.class);
        ContentResolver contentResolver = context.getContentResolver();
        StockWidgetProviderObserver observer = new StockWidgetProviderObserver(appWidgetManager, componentName, workerQueue);
        contentResolver.registerContentObserver(QuoteProvider.Quotes.CONTENT_URI, true, observer);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.d("chi6rag", "onUpdate -> called");
        for (int appWidgetId : appWidgetIds) {
            Intent stockWidgetServiceIntent = new Intent(context, StocksWidgetService.class);
            RemoteViews widgetRemoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_stocks);
            widgetRemoteViews.setRemoteAdapter(R.id.list_stocks, stockWidgetServiceIntent);
            appWidgetManager.updateAppWidget(appWidgetId, widgetRemoteViews);
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        Log.d("chi6rag", "onAppWidgetOptionsChanged -> called");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }
}
