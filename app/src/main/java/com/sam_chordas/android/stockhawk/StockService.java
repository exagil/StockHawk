package com.sam_chordas.android.stockhawk;

import android.content.Context;

import com.sam_chordas.android.stockhawk.data.StockProviderService;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuote;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuotes;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuotesResponse;
import com.sam_chordas.android.stockhawk.data.models.NetworkError;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockService {
    public static final String DEFAULT_START_DATE = "2016-03-24";
    public static final String DEFAULT_END_DATE = "2016-04-25";
    private Context context;
    private StockProviderService stockProviderService;
    private StockNetworkService stockNetworkService;

    public StockService(Context context, StockProviderService stockProviderService, StockNetworkService stockNetworkService) {
        this.context = context;
        this.stockProviderService = stockProviderService;
        this.stockNetworkService = stockNetworkService;
    }

    public void loadHistoricalQuotes(String stockSymbol, final HistoricalQuotesCallback callback) {
        try {
            String urlString = buildUrlStringFor(stockSymbol);
            stockNetworkService.getHistoricalQuotes(urlString).enqueue(new Callback<HistoricalQuotesResponse>() {
                @Override
                public void onResponse(Call<HistoricalQuotesResponse> call, Response<HistoricalQuotesResponse> response) {
                    try {
                        List<HistoricalQuote> historicalQuotes = response.body().toHistoricalQuotes();
                        stockProviderService.insertHistoricalQuotes(new HistoricalQuotes(historicalQuotes));
                        callback.onHistoricalQuotesLoaded(historicalQuotes);
                    } catch (Exception e) {
                        onFailure(call, e);
                    }
                }

                @Override
                public void onFailure(Call<HistoricalQuotesResponse> call, Throwable t) {
                    callback.onHistoricalQuotesLoadFailure(new NetworkError(t));
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private String buildUrlStringFor(String stockSymbol) throws UnsupportedEncodingException {
        String baseUrl = context.getString(R.string.base_url);
        String historicalDataQuery = "select * from yahoo.finance.historicaldata where symbol='"
                + stockSymbol + "' and startDate='" + DEFAULT_START_DATE + "' and endDate='" +
                DEFAULT_END_DATE + "'";
        String postQuery = "&format=json&env=http://datatables.org/alltables.env";
        return baseUrl + URLEncoder.encode(historicalDataQuery, "UTF-8") + postQuery;
    }

    public interface HistoricalQuotesCallback {
        void onHistoricalQuotesLoaded(List<HistoricalQuote> historicalQuotes);

        void onHistoricalQuotesLoadFailure(NetworkError networkError);
    }
}
