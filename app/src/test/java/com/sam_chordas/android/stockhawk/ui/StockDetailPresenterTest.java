package com.sam_chordas.android.stockhawk.ui;

import com.sam_chordas.android.stockhawk.StockDetailView;
import com.sam_chordas.android.stockhawk.StockNetworkService;
import com.sam_chordas.android.stockhawk.StockService;
import com.sam_chordas.android.stockhawk.data.StockLoaderService;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuote;
import com.sam_chordas.android.stockhawk.data.models.NetworkError;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class StockDetailPresenterTest {
    private StockDetailView stockDetailView;
    private StockLoaderService stockLoaderService;
    private StockNetworkService stockNetworkService;
    private StockDetailPresenter stockDetailPresenter;
    private StockService stockService;

    @Before
    public void setup() {
        stockDetailView = mock(StockDetailView.class);
        stockLoaderService = mock(StockLoaderService.class);
        stockNetworkService = mock(StockNetworkService.class);
        stockService = mock(StockService.class);
        stockDetailPresenter = new StockDetailPresenter(stockDetailView, stockLoaderService, stockService);
    }

    @Test
    public void shouldKnowHowToLoadStockSymbolForAParticularQuote() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                StockLoaderService.QuoteSymbolLoaderCallback callback = (StockLoaderService.QuoteSymbolLoaderCallback) invocation.getArguments()[1];
                callback.onQuoteSymbolLoaded("YHOO");
                return callback;
            }
        }).when(stockLoaderService).loadQuoteSymbolForQuoteId(eq(1), Matchers.<StockLoaderService.QuoteSymbolLoaderCallback>any());
        stockDetailPresenter.loadQuoteSymbolForQuoteId(1);
        verify(stockDetailView).onSymbolLoaded("YHOO");
    }

    @Test
    public void shouldShowFailureErrorWhenUnableToLoadSymbolForAParticularQuote() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                StockLoaderService.QuoteSymbolLoaderCallback callback = (StockLoaderService.QuoteSymbolLoaderCallback) invocation.getArguments()[1];
                callback.onQuoteSymbolLoadFailed();
                return callback;
            }
        }).when(stockLoaderService).loadQuoteSymbolForQuoteId(eq(1), Matchers.<StockLoaderService.QuoteSymbolLoaderCallback>any());
        stockDetailPresenter.loadQuoteSymbolForQuoteId(1);
        verify(stockDetailView).onSymbolLoadFailed();
    }

    @Test
    public void shouldLoadHistoricalQuotesWhenFetchedThemSuccessfully() {
        final List<HistoricalQuote> historicalQuotes = new ArrayList<>();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                StockService.HistoricalQuotesCallback callback = (StockService.HistoricalQuotesCallback) invocation.getArguments()[1];
                callback.onHistoricalQuotesLoaded(historicalQuotes);
                return null;
            }
        }).when(stockService).loadHistoricalQuotes(eq("YHOO"), Matchers.<StockService.HistoricalQuotesCallback>any());
        stockDetailPresenter.loadHistoricalQuotes("YHOO");
        verify(stockDetailView).onHistoricalQuotesLoaded(historicalQuotes);
    }

    @Test
    public void shouldShowErrorWhenNotAbleToLoadHistoricalQuotes() {
        Throwable t = new Throwable("Some Error");
        final NetworkError networkError = new NetworkError(t);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                StockService.HistoricalQuotesCallback callback = (StockService.HistoricalQuotesCallback) invocation.getArguments()[1];
                callback.onHistoricalQuotesLoadFailure(networkError);
                return null;
            }
        }).when(stockService).loadHistoricalQuotes(eq("YHOO"), Matchers.<StockService.HistoricalQuotesCallback>any());
        stockDetailPresenter.loadHistoricalQuotes("YHOO");
        verify(stockDetailView).onHistoricalQuotesLoadFailure("Some Error");
    }
}
