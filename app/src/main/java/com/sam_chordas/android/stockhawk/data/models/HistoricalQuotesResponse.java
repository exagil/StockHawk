package com.sam_chordas.android.stockhawk.data.models;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.util.List;

public class HistoricalQuotesResponse {
    @SerializedName("query")
    public QueryResponse queryResponse;

    public List<HistoricalQuote> toHistoricalQuotes() throws ParseException {
        return queryResponse.toHistoricalQuotes();
    }
}
