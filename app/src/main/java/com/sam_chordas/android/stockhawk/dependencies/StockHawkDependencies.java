package com.sam_chordas.android.stockhawk.dependencies;

import com.sam_chordas.android.stockhawk.service.StockTaskService;
import com.sam_chordas.android.stockhawk.ui.MyStocksActivity;
import com.sam_chordas.android.stockhawk.ui.StockDetailActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        NetworkModule.class,
        DataModule.class,
        LocalStorageModule.class}
)
public interface StockHawkDependencies {
    void inject(StockDetailActivity stockDetailActivity);

    void inject(MyStocksActivity myStocksActivity);
}
