package com.sam_chordas.android.stockhawk;

import android.app.Application;

import com.sam_chordas.android.stockhawk.dependencies.AppModule;
import com.sam_chordas.android.stockhawk.dependencies.DaggerStockHawkDependencies;
import com.sam_chordas.android.stockhawk.dependencies.NetworkModule;
import com.sam_chordas.android.stockhawk.dependencies.StockHawkDependencies;

public class StockHawkApp extends Application {
    private StockHawkDependencies dependencies;

    @Override
    public void onCreate() {
        dependencies = DaggerStockHawkDependencies.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .build();
        super.onCreate();
    }

    public StockHawkDependencies getStockHawkDependencies() {
        return dependencies;
    }

}
