package com.sam_chordas.android.stockhawk;

import android.content.Context;

import com.sam_chordas.android.stockhawk.data.StockProviderService;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuote;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuoteDate;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuotes;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuotesResponse;
import com.sam_chordas.android.stockhawk.data.models.NetworkError;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class StockService {
    private Context context;
    private StockProviderService stockProviderService;
    private StockNetworkService stockNetworkService;

    public StockService(Context context, StockProviderService stockProviderService, StockNetworkService stockNetworkService) {
        this.context = context;
        this.stockProviderService = stockProviderService;
        this.stockNetworkService = stockNetworkService;
    }

    public Subscription loadOneMonthsHistoricalQuotes(String stockSymbol, HistoricalQuoteDate historicalQuoteDate, final HistoricalQuotesCallback callback) {
        try {
            HistoricalQuoteDate startHistoricalQuoteDate = historicalQuoteDate.travelOneMonthBack();
            String urlString = buildUrlStringFor(stockSymbol, startHistoricalQuoteDate.queryable(), historicalQuoteDate.queryable());
            Subscription subscription = stockNetworkService.getHistoricalQuotes(urlString)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<HistoricalQuotesResponse>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            callback.onOneMonthsHistoricalQuotesLoadFailure(new NetworkError(e));
                        }

                        @Override
                        public void onNext(HistoricalQuotesResponse historicalQuotesResponse) {
                            try {
                                List<HistoricalQuote> historicalQuoteList = historicalQuotesResponse.toHistoricalQuotes();
                                HistoricalQuotes historicalQuotes = new HistoricalQuotes(historicalQuoteList);
                                stockProviderService.insertHistoricalQuotes(historicalQuotes);
                                callback.onHistoricalQuotesLoaded(historicalQuotes.sortedCollection());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    });
            return subscription;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String buildUrlStringFor(String stockSymbol, String queryableStartDate, String queryableEndDate) throws UnsupportedEncodingException {
        String baseUrl = context.getString(R.string.base_url);
        String historicalDataQuery = "select * from yahoo.finance.historicaldata where symbol='"
                + stockSymbol + "' and startDate='" + queryableStartDate + "' and endDate='" +
                queryableEndDate + "'";
        String postQuery = "&format=json&env=http://datatables.org/alltables.env";
        return baseUrl + URLEncoder.encode(historicalDataQuery, "UTF-8") + postQuery;
    }

    public interface HistoricalQuotesCallback {
        void onHistoricalQuotesLoaded(List<HistoricalQuote> historicalQuotes);

        void onOneMonthsHistoricalQuotesLoadFailure(NetworkError networkError);
    }
}
