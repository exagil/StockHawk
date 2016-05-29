package com.sam_chordas.android.stockhawk.ui;

import com.sam_chordas.android.stockhawk.StockDetailView;
import com.sam_chordas.android.stockhawk.StockService;
import com.sam_chordas.android.stockhawk.data.StockProviderService;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuote;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuotes;
import com.sam_chordas.android.stockhawk.data.models.NetworkError;
import com.sam_chordas.android.stockhawk.data.models.NullHistoricalQuotes;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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
    public void shouldLoadHistoricalQuotesWhenFetchedThemSuccessfully() throws ParseException {
        when(stockProviderService.loadHistoricalQuotesFor("YHOO")).thenReturn(new NullHistoricalQuotes());
        final List<HistoricalQuote> historicalQuotes = new ArrayList<>();
        HistoricalQuote thisHistoricalQuote = new HistoricalQuote("FB", new Date(1464498687000l), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3));
        HistoricalQuote thatHistoricalQuote = new HistoricalQuote("FB", new Date(1464603010000l), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3));
        historicalQuotes.add(thatHistoricalQuote);
        historicalQuotes.add(thisHistoricalQuote);
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
        verifyNoMoreInteractions(stockDetailView);
    }

    @Test
    public void shouldShowErrorWhenNotAbleToLoadHistoricalQuotes() throws ParseException {
        when(stockProviderService.loadHistoricalQuotesFor("YHOO")).thenReturn(new NullHistoricalQuotes());
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
        verifyNoMoreInteractions(stockDetailView);
    }

    @Test
    public void testThatStockDetailPresenterShouldLoadHistoricalQuotesFromLocalIfPresent() throws ParseException {
        List<HistoricalQuote> historicalQuotes = new ArrayList<>();
        HistoricalQuote historicalQuote = new HistoricalQuote("APPL", new Date(1463899584940l), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3));
        historicalQuotes.add(historicalQuote);
        when(stockProviderService.loadHistoricalQuotesFor("APPL")).thenReturn(new HistoricalQuotes(historicalQuotes));
        stockDetailPresenter.loadHistoricalQuotes("APPL");
        verify(stockDetailView).onHistoricalQuotesLoaded(historicalQuotes);
        verifyNoMoreInteractions(stockDetailView);
        verifyNoMoreInteractions(stockService);
    }
}
