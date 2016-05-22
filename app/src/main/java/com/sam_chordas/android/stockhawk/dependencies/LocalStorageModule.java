package com.sam_chordas.android.stockhawk.dependencies;

import android.content.Context;

import com.sam_chordas.android.stockhawk.data.StockProviderService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LocalStorageModule {
    @Singleton
    @Provides
    public StockProviderService providesStockProviderService(Context context) {
        return new StockProviderService(context);
    }
}
