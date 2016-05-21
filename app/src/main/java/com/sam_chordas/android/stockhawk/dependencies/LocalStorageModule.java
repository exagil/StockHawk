package com.sam_chordas.android.stockhawk.dependencies;

import android.content.Context;

import com.sam_chordas.android.stockhawk.data.StockLoaderService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LocalStorageModule {
    @Singleton
    @Provides
    public StockLoaderService providesStockLoaderService(Context context) {
        return new StockLoaderService(context);
    }
}
