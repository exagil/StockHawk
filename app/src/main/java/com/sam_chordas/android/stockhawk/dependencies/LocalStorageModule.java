package com.sam_chordas.android.stockhawk.dependencies;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;

import com.sam_chordas.android.stockhawk.data.StockProviderService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LocalStorageModule {

    public static final String STOCK_HAWK_PREFERENCES = "StockHawkPreferences";

    @Singleton
    @Provides
    public StockProviderService providesStockProviderService(Context context) {
        return new StockProviderService(context);
    }

    @Singleton
    @Provides
    public SharedPreferences providesDefaultSharedPreferences(Context context) {
        return context.getSharedPreferences(STOCK_HAWK_PREFERENCES, Context.MODE_PRIVATE);
    }
}
