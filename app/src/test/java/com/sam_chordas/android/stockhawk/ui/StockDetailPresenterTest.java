package com.sam_chordas.android.stockhawk.ui;

import com.sam_chordas.android.stockhawk.StockDetailView;
import com.sam_chordas.android.stockhawk.StockNetworkService;
import com.sam_chordas.android.stockhawk.data.StockLoaderService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class StockDetailPresenterTest {
    private StockDetailView stockDetailView;
    private StockLoaderService stockLoaderService;
    private StockNetworkService stockNetworkService;
    private StockDetailPresenter stockDetailPresenter;

    @Before
    public void setup() {
        stockDetailView = mock(StockDetailView.class);
        stockLoaderService = mock(StockLoaderService.class);
        stockNetworkService = mock(StockNetworkService.class);
        stockDetailPresenter = new StockDetailPresenter(stockDetailView, stockLoaderService, stockNetworkService);
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
}
