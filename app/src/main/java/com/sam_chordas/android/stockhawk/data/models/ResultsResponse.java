package com.sam_chordas.android.stockhawk.data.models;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ResultsResponse {
    @SerializedName("quote")
    public List<HistoricalQuoteResponse> quotes;

    public List<HistoricalQuote> toHistoricalQuotes() throws ParseException {
        return convertToHistoricalQuotes(quotes);
    }

    private List<HistoricalQuote> convertToHistoricalQuotes(List<HistoricalQuoteResponse> quotes) throws ParseException {
        List<HistoricalQuote> historicalQuotes = new ArrayList<>();
        if (quotes == null || quotes.isEmpty()) return historicalQuotes;
        for (HistoricalQuoteResponse historicalQuoteResponse : quotes)
            historicalQuotes.add(historicalQuoteResponse.toHistoricalQuote());
        return historicalQuotes;
    }
}
