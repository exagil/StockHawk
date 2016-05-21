package com.sam_chordas.android.stockhawk;

import com.sam_chordas.android.stockhawk.data.models.HistoricalQuote;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuotesResponse;
import com.sam_chordas.android.stockhawk.data.models.NetworkError;

import java.text.ParseException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockService {
    private StockNetworkService stockNetworkService;

    public StockService(StockNetworkService stockNetworkService) {
        this.stockNetworkService = stockNetworkService;
    }

    public void loadHistoricalQuotes(String stockSymbol, final HistoricalQuotesCallback callback) {
        stockNetworkService.getHistoricalQuotes(stockSymbol).enqueue(new Callback<HistoricalQuotesResponse>() {
            @Override
            public void onResponse(Call<HistoricalQuotesResponse> call, Response<HistoricalQuotesResponse> response) {
                try {
                    callback.onHistoricalQuotesLoaded(response.body().toHistoricalQuotes());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<HistoricalQuotesResponse> call, Throwable t) {
                callback.onHistoricalQuotesLoadFailure(new NetworkError(t));
            }
        });
    }

    public interface HistoricalQuotesCallback {
        void onHistoricalQuotesLoaded(List<HistoricalQuote> historicalQuotes);

        void onHistoricalQuotesLoadFailure(NetworkError networkError);
    }
}
