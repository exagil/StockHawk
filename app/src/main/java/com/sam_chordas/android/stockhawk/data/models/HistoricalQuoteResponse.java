package com.sam_chordas.android.stockhawk.data.models;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoricalQuoteResponse {
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(YYYY_MM_DD);

    @SerializedName("Symbol")
    public String symbol;
    @SerializedName("Date")
    public String date;
    @SerializedName("Open")
    public String open;
    @SerializedName("High")
    public String high;
    @SerializedName("Low")
    public String low;
    @SerializedName("Close")
    public String close;
    @SerializedName("Volume")
    public String volume;
    @SerializedName("Adj_Close")
    public String adjClose;

    public HistoricalQuote toHistoricalQuote() throws ParseException {
        Date parsedDate = DATE_FORMAT.parse(date);
        return new HistoricalQuote(
                symbol,
                parsedDate,
                Double.parseDouble(open),
                Double.parseDouble(high),
                Double.parseDouble(low),
                Double.parseDouble(close),
                Double.parseDouble(volume),
                Double.parseDouble(adjClose)
        );
    }
}
