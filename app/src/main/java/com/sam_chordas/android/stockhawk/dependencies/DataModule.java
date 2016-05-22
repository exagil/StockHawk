package com.sam_chordas.android.stockhawk.dependencies;

import android.content.Context;

import com.sam_chordas.android.stockhawk.StockNetworkService;
import com.sam_chordas.android.stockhawk.StockService;
import com.sam_chordas.android.stockhawk.data.StockProviderService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {
    @Singleton
    @Provides
    StockService providesStockService(Context context, StockProviderService stockProviderService, StockNetworkService stockNetworkService) {
        return new StockService(context, stockProviderService, stockNetworkService);
    }
}
