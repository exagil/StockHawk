package com.sam_chordas.android.stockhawk.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultsResponse {
    @SerializedName("quote")
    public List<HistoricalQuotesResponse> quotes;
}
