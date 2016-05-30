package com.sam_chordas.android.stockhawk.data;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.sam_chordas.android.stockhawk.data.generated.HistoryProvider;
import com.sam_chordas.android.stockhawk.data.generated.QuoteProvider;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuoteDate;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuotes;
import com.sam_chordas.android.stockhawk.data.models.Quote;

import java.text.ParseException;

// StockProviderService gives access to Stocks in the local storage

public class StockProviderService implements Loader.OnLoadCompleteListener<Cursor> {
    public static final int ID_LOAD_QUOTE_SYMBOL = 1;
    private Context context;
    private QuoteLoaderCallback quoteLoaderCallback;
    private CursorLoader cursorLoader;

    public StockProviderService(Context context) {
        this.context = context;
    }

    public void loadQuoteWithId(final long quoteId, @NonNull final QuoteLoaderCallback callback) {
        this.quoteLoaderCallback = callback;
        this.cursorLoader = new CursorLoader(context, QuoteProvider.Quotes.CONTENT_URI,
                null,
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
                quoteLoaderCallback.onQuoteLoadFailed();
                data.close();
                return;
            }
            Quote quote = Quote.fromCursor(data);
            quoteLoaderCallback.onQuoteLoaded(quote);
            data.close();
        }
    }

    public void insertHistoricalQuotes(HistoricalQuotes historicalQuotes) {
        context.getContentResolver().bulkInsert(HistoryProvider.History.CONTENT_URI, historicalQuotes.toContentValues());
    }

    public HistoricalQuotes loadOneMonthsHistoricalQuotes(String quoteSymbol, HistoricalQuoteDate endHistoricalQuoteDate) throws ParseException {
        HistoricalQuoteDate startHistoricalQuoteDate = endHistoricalQuoteDate.travelOneMonthBack();
        Cursor historicalQuotesCursor = context.getContentResolver().query(HistoryProvider.History.CONTENT_URI, null, HistoryColumns.SYMBOL + "=? AND " + HistoryColumns.DATE + " BETWEEN ? AND ?", new String[]{quoteSymbol, startHistoricalQuoteDate.persistable(), endHistoricalQuoteDate.persistable()}, null);
        return HistoricalQuotes.fromCursor(historicalQuotesCursor);
    }

    public interface QuoteLoaderCallback {
        void onQuoteLoaded(Quote quote);

        void onQuoteLoadFailed();
    }
}
