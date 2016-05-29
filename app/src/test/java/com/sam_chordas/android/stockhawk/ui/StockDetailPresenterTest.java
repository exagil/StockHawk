package com.sam_chordas.android.stockhawk.ui;

import com.google.gson.Gson;
import com.sam_chordas.android.stockhawk.StockDetailView;
import com.sam_chordas.android.stockhawk.StockService;
import com.sam_chordas.android.stockhawk.data.StockProviderService;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuote;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuoteDate;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuoteResponse;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuotes;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuotesResponse;
import com.sam_chordas.android.stockhawk.data.models.NetworkError;
import com.sam_chordas.android.stockhawk.data.models.NullHistoricalQuotes;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class StockDetailPresenterTest {
    private StockDetailView stockDetailView;
    private StockProviderService stockProviderService;
    private StockDetailPresenter stockDetailPresenter;
    private StockService stockService;

    @Before
    public void setup() {
        stockDetailView = mock(StockDetailView.class);
        stockProviderService = mock(StockProviderService.class);
        stockService = mock(StockService.class);
        stockDetailPresenter = new StockDetailPresenter(stockDetailView, stockProviderService, stockService);
    }

    @Test
    public void shouldKnowHowToLoadStockSymbolForAParticularQuote() throws ParseException {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                StockProviderService.QuoteSymbolLoaderCallback callback = (StockProviderService.QuoteSymbolLoaderCallback) invocation.getArguments()[1];
                callback.onQuoteSymbolLoaded("YHOO");
                return null;
            }
        }).when(stockProviderService).loadQuoteSymbolForQuoteId(anyInt(), Matchers.<StockProviderService.QuoteSymbolLoaderCallback>any());
        stockDetailPresenter.loadQuoteSymbolForQuoteId(1);
        verify(stockDetailView).onSymbolLoaded("YHOO");
    }

    @Test
    public void shouldShowFailureErrorWhenUnableToLoadSymbolForAParticularQuote() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                StockProviderService.QuoteSymbolLoaderCallback callback = (StockProviderService.QuoteSymbolLoaderCallback) invocation.getArguments()[1];
                callback.onQuoteSymbolLoadFailed();
                return null;
            }
        }).when(stockProviderService).loadQuoteSymbolForQuoteId(anyInt(), Matchers.<StockProviderService.QuoteSymbolLoaderCallback>any());
        stockDetailPresenter.loadQuoteSymbolForQuoteId(1);
        verify(stockDetailView).onSymbolLoadFailed();
    }

    @Test
    public void shouldLoadOneMonthsHistoricalQuotesWhenLocalNotPresentAndFetchedThemSuccessfully() throws ParseException {
        HistoricalQuoteDate historicalQuoteDate = HistoricalQuoteDate.fromMilliseconds(1464516610000l);
        when(stockProviderService.loadOneMonthsHistoricalQuotes("FB", historicalQuoteDate)).thenReturn(new NullHistoricalQuotes());
        InputStreamReader historicalQuotesReader = new InputStreamReader(HistoricalQuoteResponse.class.getClassLoader().getResourceAsStream("historical_quotes_fb_20160429_20160527.json"));
        HistoricalQuotesResponse historicalQuotesResponse = new Gson().fromJson(historicalQuotesReader, HistoricalQuotesResponse.class);
        final List<HistoricalQuote> historicalQuoteList = historicalQuotesResponse.toHistoricalQuotes();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                StockService.HistoricalQuotesCallback callback = (StockService.HistoricalQuotesCallback) invocation.getArguments()[2];
                callback.onHistoricalQuotesLoaded(historicalQuoteList);
                return null;
            }
        }).when(stockService).loadOneMonthsHistoricalQuotes(eq("FB"), eq(historicalQuoteDate), Matchers.<StockService.HistoricalQuotesCallback>any());
        stockDetailPresenter.loadOneMonthsHistoricalQuotes("FB", historicalQuoteDate);
        verify(stockDetailView).onOneMonthsHistoricalQuotesLoaded(historicalQuoteList);
        verify(stockDetailView).beforeLoad();
        verify(stockDetailView).afterLoad();
        verifyNoMoreInteractions(stockDetailView);
    }

    @Test
    public void shouldShowErrorWhenNoLocalHistoricalQuotesPresentAndNotAbleToLoadHistoricalQuotesFromNetwork() throws ParseException {
        HistoricalQuoteDate historicalQuoteDate = HistoricalQuoteDate.fromMilliseconds(1464516610000l);
        when(stockProviderService.loadOneMonthsHistoricalQuotes("YHOO", historicalQuoteDate)).thenReturn(new NullHistoricalQuotes());
        Throwable t = new Throwable("Some Error");
        final NetworkError networkError = new NetworkError(t);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                StockService.HistoricalQuotesCallback callback = (StockService.HistoricalQuotesCallback) invocation.getArguments()[2];
                callback.onOneMonthsHistoricalQuotesLoadFailure(networkError);
                return null;
            }
        }).when(stockService).loadOneMonthsHistoricalQuotes(eq("YHOO"), eq(historicalQuoteDate), Matchers.<StockService.HistoricalQuotesCallback>any());
        stockDetailPresenter.loadOneMonthsHistoricalQuotes("YHOO", HistoricalQuoteDate.fromMilliseconds(1464516610000l));
        verify(stockDetailView).onOneMonthsHistoricalQuotesLoadFailure("Some Error");
        verify(stockDetailView).beforeLoad();
        verify(stockDetailView).afterLoad();
        verifyNoMoreInteractions(stockDetailView);
    }

    @Test
    public void testThatStockDetailPresenterShouldLoadHistoricalQuotesFromLocalIfLatestPresent() throws ParseException {
        HistoricalQuoteDate historicalQuoteDate = HistoricalQuoteDate.fromMilliseconds(1464516610000l);
        InputStreamReader historicalQuotesReader = new InputStreamReader(HistoricalQuoteResponse.class.getClassLoader().getResourceAsStream("historical_quotes_fb_20160429_20160527.json"));
        HistoricalQuotesResponse historicalQuotesResponse = new Gson().fromJson(historicalQuotesReader, HistoricalQuotesResponse.class);
        final List<HistoricalQuote> historicalQuoteList = historicalQuotesResponse.toHistoricalQuotes();
        when(stockProviderService.loadOneMonthsHistoricalQuotes("FB", historicalQuoteDate)).thenReturn(new HistoricalQuotes(historicalQuoteList));
        stockDetailPresenter.loadOneMonthsHistoricalQuotes("FB", historicalQuoteDate);
        verify(stockDetailView).onOneMonthsHistoricalQuotesLoaded(historicalQuoteList);
        verify(stockDetailView).beforeLoad();
        verify(stockDetailView).afterLoad();
        verifyNoMoreInteractions(stockService);
        verifyNoMoreInteractions(stockDetailView);
    }

    @Test
    public void testThatStockDetailPresenterShouldLoadHistoricalQuotesFromNetworkIfLatestAreNotPresent() throws ParseException {
        HistoricalQuoteDate historicalQuoteDate = HistoricalQuoteDate.fromMilliseconds(1464516610000l);
        InputStreamReader historicalQuotesReader = new InputStreamReader(HistoricalQuoteResponse.class.getClassLoader().getResourceAsStream("historical_quotes_fb_20160429_20160526.json"));
        HistoricalQuotesResponse historicalQuotesResponse = new Gson().fromJson(historicalQuotesReader, HistoricalQuotesResponse.class);
        final List<HistoricalQuote> oldHistoricalQuoteList = historicalQuotesResponse.toHistoricalQuotes();

        InputStreamReader newHistoricalQuotesReader = new InputStreamReader(HistoricalQuoteResponse.class.getClassLoader().getResourceAsStream("historical_quotes_fb_20160429_20160527.json"));
        HistoricalQuotesResponse newHistoricalQuotesResponse = new Gson().fromJson(newHistoricalQuotesReader, HistoricalQuotesResponse.class);
        final List<HistoricalQuote> newHistoricalQuoteList = newHistoricalQuotesResponse.toHistoricalQuotes();

        when(stockProviderService.loadOneMonthsHistoricalQuotes("FB", historicalQuoteDate)).thenReturn(new HistoricalQuotes(oldHistoricalQuoteList));
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                StockService.HistoricalQuotesCallback callback = (StockService.HistoricalQuotesCallback) invocation.getArguments()[2];
                callback.onHistoricalQuotesLoaded(newHistoricalQuoteList);
                return null;
            }
        }).when(stockService).loadOneMonthsHistoricalQuotes(eq("FB"), eq(historicalQuoteDate), Matchers.<StockService.HistoricalQuotesCallback>any());
        stockDetailPresenter.loadOneMonthsHistoricalQuotes("FB", historicalQuoteDate);
        verify(stockDetailView).onOneMonthsHistoricalQuotesLoaded(newHistoricalQuoteList);
        verify(stockDetailView).beforeLoad();
        verify(stockDetailView).afterLoad();
        verifyNoMoreInteractions(stockDetailView);
    }
}
