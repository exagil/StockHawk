package com.sam_chordas.android.stockhawk.dependencies;


import android.content.Context;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.StockNetworkService;
import com.sam_chordas.android.stockhawk.StockService;
import com.sam_chordas.android.stockhawk.utils.NetworkUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    OkHttpClient providesOkHttpClient() {
        return new OkHttpClient();
    }

    @Provides
    @Singleton
    Retrofit providesRetrofit(Context context, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(context.getString(R.string.improved_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    StockNetworkService providesStockNetworkService(Retrofit retrofit) {
        return retrofit.create(StockNetworkService.class);
    }
}
