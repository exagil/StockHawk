package com.sam_chordas.android.stockhawk.data.models;

import com.google.gson.annotations.SerializedName;

public class HistoricalQuotesResponse {
    @SerializedName("query")
    public QueryResponse queryResponse;
}
