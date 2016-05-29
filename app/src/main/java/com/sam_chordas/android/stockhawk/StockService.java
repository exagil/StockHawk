package com.sam_chordas.android.stockhawk;

import android.content.Context;

import com.sam_chordas.android.stockhawk.data.StockProviderService;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuote;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuotes;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuotesResponse;
import com.sam_chordas.android.stockhawk.data.models.NetworkError;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class StockService {
    public static final String DEFAULT_START_DATE = "2016-03-24";
    public static final String DEFAULT_END_DATE = "2016-04-25";
    private Context context;
    private StockProviderService stockProviderService;
    private StockNetworkService stockNetworkService;

    public StockService(Context context, StockProviderService stockProviderService, StockNetworkService stockNetworkService) {
        this.context = context;
        this.stockProviderService = stockProviderService;
        this.stockNetworkService = stockNetworkService;
    }

    public Subscription loadOneMonthsHistoricalQuotes(String stockSymbol, final HistoricalQuotesCallback callback) {
        try {
            String urlString = buildUrlStringFor(stockSymbol);
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

    private String buildUrlStringFor(String stockSymbol) throws UnsupportedEncodingException {
        String baseUrl = context.getString(R.string.base_url);
        String historicalDataQuery = "select * from yahoo.finance.historicaldata where symbol='"
                + stockSymbol + "' and startDate='" + DEFAULT_START_DATE + "' and endDate='" +
                DEFAULT_END_DATE + "'";
        String postQuery = "&format=json&env=http://datatables.org/alltables.env";
        return baseUrl + URLEncoder.encode(historicalDataQuery, "UTF-8") + postQuery;
    }

    public interface HistoricalQuotesCallback {
        void onHistoricalQuotesLoaded(List<HistoricalQuote> historicalQuotes);

        void onOneMonthsHistoricalQuotesLoadFailure(NetworkError networkError);
    }
}
