package com.sam_chordas.android.stockhawk;

import android.content.Context;

import com.google.gson.Gson;
import com.sam_chordas.android.stockhawk.data.StockProviderService;
import com.sam_chordas.android.stockhawk.data.dto.HistoricalQuoteResponse;
import com.sam_chordas.android.stockhawk.data.dto.HistoricalQuotesResponse;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuote;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuoteDate;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuotes;
import com.sam_chordas.android.stockhawk.data.models.NetworkError;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Matchers;

import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.List;

import rx.Observable;

import static com.sam_chordas.android.stockhawk.StockService.HistoricalQuotesCallback;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StockServiceTest {
    public static final long MILLISECONDS_UNTIL_20160530_0000HRS = 1464566401000l;

    @Rule
    public RxSchedulersOverrideRule rxSchedulersOverrideRule = new RxSchedulersOverrideRule();

    @Test
    public void testThatStockServiceKnowsHowToLoadSortedStocksCollection() throws ParseException {
        Context context = mock(Context.class);
        StockProviderService stockProviderService = mock(StockProviderService.class);
        StockNetworkService stockNetworkService = mock(StockNetworkService.class);
        HistoricalQuotesCallback callback = mock(HistoricalQuotesCallback.class);
        InputStreamReader historicalQuotesReader = new InputStreamReader(HistoricalQuoteResponse.class.getClassLoader().getResourceAsStream("historical_quotes_fb_20160429_20160527.json"));
        HistoricalQuotesResponse historicalQuotesResponse = new Gson().fromJson(historicalQuotesReader, HistoricalQuotesResponse.class);
        final List<HistoricalQuote> historicalQuoteList = historicalQuotesResponse.toHistoricalQuotes();
        StockService stockService = new StockService(context, stockProviderService, stockNetworkService);
        HistoricalQuotes historicalQuotes = new HistoricalQuotes(historicalQuoteList);

        when(stockNetworkService.getHistoricalQuotes(Matchers.<String>any())).thenReturn(Observable.just(historicalQuotesResponse));

        stockService.loadOneMonthsHistoricalQuotes("FB", HistoricalQuoteDate.fromMilliseconds(MILLISECONDS_UNTIL_20160530_0000HRS), callback);

        verify(stockProviderService).insertHistoricalQuotes(historicalQuotes);
        verify(callback).onHistoricalQuotesLoaded(historicalQuotes.sortedCollection());
    }

    @Test
    public void testThatStockServiceKnowsHowToHandleErrorConditionWhenStocksAreNotLoaded() {
        Context context = mock(Context.class);
        StockProviderService stockProviderService = mock(StockProviderService.class);
        StockNetworkService stockNetworkService = mock(StockNetworkService.class);
        HistoricalQuotesCallback callback = mock(HistoricalQuotesCallback.class);
        StockService stockService = new StockService(context, stockProviderService, stockNetworkService);

        NetworkError networkError = new NetworkError(new Throwable("Some Error"));
        doReturn(Observable.just(networkError)).when(stockNetworkService).getHistoricalQuotes(Matchers.anyString());
        stockService.loadOneMonthsHistoricalQuotes("FB", HistoricalQuoteDate.fromMilliseconds(MILLISECONDS_UNTIL_20160530_0000HRS), callback);

        verify(callback).onOneMonthsHistoricalQuotesLoadFailure(Matchers.<NetworkError>any());
    }
}
