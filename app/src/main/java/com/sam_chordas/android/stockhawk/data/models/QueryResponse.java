package com.sam_chordas.android.stockhawk.data.models;

import com.google.gson.annotations.SerializedName;

public class QueryResponse {
    @SerializedName("results")
    public ResultsResponse resultsResponse;
}
