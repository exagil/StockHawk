package com.sam_chordas.android.stockhawk.data.models;

public class NetworkError {
    private Throwable t;

    public NetworkError(Throwable t) {
        this.t = t;
    }


    public String error() {
        return t.getMessage();
    }
}
