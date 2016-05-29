package com.sam_chordas.android.stockhawk;

import com.sam_chordas.android.stockhawk.data.models.HistoricalQuotesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

public interface StockNetworkService {
    @GET
    Observable<HistoricalQuotesResponse> getHistoricalQuotes(@Url String url);
}
