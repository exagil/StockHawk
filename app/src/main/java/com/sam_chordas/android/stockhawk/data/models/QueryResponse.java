package com.sam_chordas.android.stockhawk.data.models;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.util.List;

public class QueryResponse {
    @SerializedName("results")
    public ResultsResponse resultsResponse;

    public List<HistoricalQuote> toHistoricalQuotes() throws ParseException {
        return resultsResponse.toHistoricalQuotes();
    }
}
