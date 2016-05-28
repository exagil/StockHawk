package com.sam_chordas.android.stockhawk.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {
    private Context context;

    public NetworkUtils(Context context) {
        this.context = context;
    }

    public boolean isNotConnectedToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null) return true;
        return !activeNetworkInfo.isConnected();
    }
}
