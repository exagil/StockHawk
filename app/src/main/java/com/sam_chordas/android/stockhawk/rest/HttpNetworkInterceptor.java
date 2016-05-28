package com.sam_chordas.android.stockhawk.rest;

import com.sam_chordas.android.stockhawk.utils.NetworkUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class HttpNetworkInterceptor implements Interceptor {
    private NetworkUtils networkUtils;

    public HttpNetworkInterceptor(NetworkUtils networkUtils) {
        this.networkUtils = networkUtils;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (networkUtils.isNotConnectedToInternet())
            throw new IOException("No Internet Connection!");
        return chain.proceed(chain.request());
    }
}
