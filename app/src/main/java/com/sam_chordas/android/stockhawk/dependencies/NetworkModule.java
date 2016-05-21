package com.sam_chordas.android.stockhawk.dependencies;


import android.content.Context;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.StockNetworkService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

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
                .baseUrl(context.getString(R.string.base))
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    StockNetworkService providesStockNetworkService(Retrofit retrofit) {
        return retrofit.create(StockNetworkService.class);
    }
}
