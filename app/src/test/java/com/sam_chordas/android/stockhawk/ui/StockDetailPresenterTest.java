package com.sam_chordas.android.stockhawk.ui;

import com.sam_chordas.android.stockhawk.StockDetailView;
import com.sam_chordas.android.stockhawk.StockService;
import com.sam_chordas.android.stockhawk.data.StockProviderService;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuote;
import com.sam_chordas.android.stockhawk.data.models.NetworkError;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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
    public void shouldKnowHowToLoadStockSymbolForAParticularQuote() {
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
