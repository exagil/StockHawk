package com.sam_chordas.android.stockhawk.service;

import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.RemoteException;
import android.support.annotation.IntDef;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.generated.QuoteProvider;
import com.sam_chordas.android.stockhawk.utils.NetworkUtils;
import com.sam_chordas.android.stockhawk.utils.Utils;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URLEncoder;

/**
 * Created by sam_chordas on 9/30/15.
 * The GCMTask service is primarily for periodic tasks. However, OnRunTask can be called directly
 * and is used for the initialization and adding task as well.
 */
public class StockTaskService extends GcmTaskService {
    private NetworkUtils networkUtils = null;
    private String LOG_TAG = StockTaskService.class.getSimpleName();

    private OkHttpClient client = new OkHttpClient();
    private Context context;
    private StringBuilder mStoredSymbols = new StringBuilder();
    private boolean isUpdate;

    public StockTaskService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public StockTaskService(Context context) {
        this.context = context;
        networkUtils = new NetworkUtils(context);
    }

    @Override
    public int onRunTask(TaskParams params) {
        setStockStatus(STOCK_STATUS_UNKNOWN, context);
        Cursor initQueryCursor;
        if (context == null) {
            context = this;
        }
        StringBuilder urlStringBuilder = new StringBuilder();
        try {
            // Base URL for the Yahoo query
            urlStringBuilder.append("https://query.yahooapis.com/v1/public/yql?q=");
            urlStringBuilder.append(URLEncoder.encode("select * from yahoo.finance.quotes where symbol "
                    + "in (", "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (params.getTag().equals("init") || params.getTag().equals("periodic")) {
            isUpdate = true;
            initQueryCursor = context.getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI,
                    new String[]{"Distinct " + QuoteColumns.SYMBOL}, null,
                    null, null);
            if (initQueryCursor.getCount() == 0 || initQueryCursor == null) {
                // Init task. Populates DB with quotes for the symbols seen below
                try {
                    urlStringBuilder.append(
                            URLEncoder.encode("\"YHOO\",\"AAPL\",\"GOOG\",\"MSFT\")", "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else if (initQueryCursor != null) {
                DatabaseUtils.dumpCursor(initQueryCursor);
                initQueryCursor.moveToFirst();
                for (int i = 0; i < initQueryCursor.getCount(); i++) {
                    mStoredSymbols.append("\"" +
                            initQueryCursor.getString(initQueryCursor.getColumnIndex("symbol")) + "\",");
                    initQueryCursor.moveToNext();
                }
                mStoredSymbols.replace(mStoredSymbols.length() - 1, mStoredSymbols.length(), ")");
                try {
                    urlStringBuilder.append(URLEncoder.encode(mStoredSymbols.toString(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        } else if (params.getTag().equals("add")) {
            isUpdate = false;
            // get symbol from params.getExtra and build query
            String stockInput = params.getExtras().getString("symbol");
            try {
                urlStringBuilder.append(URLEncoder.encode("\"" + stockInput + "\")", "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        // finalize the URL for the API query.
        urlStringBuilder.append("&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables."
                + "org%2Falltableswithkeys&callback=");

        String urlString;
        String getResponse;
        int result = GcmNetworkManager.RESULT_FAILURE;

        if (urlStringBuilder != null) {
            urlString = urlStringBuilder.toString();
            try {
                if (networkUtils.isNotConnectedToInternet()) {
                    setStockStatus(STOCK_STATUS_NETWORK_ERROR, context);
                    return result;
                }
                getResponse = fetchData(urlString);
                ContentValues contentValues = new ContentValues();
                // update ISCURRENT to 0 (false) so new data is current
                if (isUpdate) {
                    contentValues.put(QuoteColumns.ISCURRENT, 0);
                    context.getContentResolver().update(QuoteProvider.Quotes.CONTENT_URI, contentValues,
                            null, null);
                }
                context.getContentResolver().applyBatch(QuoteProvider.AUTHORITY,
                        Utils.quoteJsonToContentVals(getResponse));
                result = GcmNetworkManager.RESULT_SUCCESS;
            } catch (IOException e) {
                e.printStackTrace();
                setStockStatus(STOCK_STATUS_SERVER_DOWN, context);
            } catch (RemoteException | OperationApplicationException e) {
                setStockStatus(STOCK_STATUS_SERVER_INVALID, context);
            } catch (NumberFormatException e) {
                setStockStatus(STOCK_STATUS_INVALID, context);
            }
        }

        return result;
    }

    @IntDef({STOCK_STATUS_UNKNOWN, STOCK_STATUS_OK, STOCK_STATUS_INVALID, STOCK_STATUS_SERVER_DOWN,
            STOCK_STATUS_SERVER_INVALID, STOCK_STATUS_NETWORK_ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface StockStatus {
    }

    public static final int STOCK_STATUS_UNKNOWN = 0;
    public static final int STOCK_STATUS_OK = 1;
    public static final int STOCK_STATUS_INVALID = 2;
    public static final int STOCK_STATUS_SERVER_DOWN = 3;
    public static final int STOCK_STATUS_SERVER_INVALID = 4;
    public static final int STOCK_STATUS_NETWORK_ERROR = 5;

    private String fetchData(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private void setStockStatus(@StockStatus int stockStatus, Context context) {
        SharedPreferences stockHawkPreferences = context.getSharedPreferences("StockHawkPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = stockHawkPreferences.edit();
        editor.putInt(context.getString(R.string.stock_status_key), stockStatus);
        editor.commit();
    }
}
