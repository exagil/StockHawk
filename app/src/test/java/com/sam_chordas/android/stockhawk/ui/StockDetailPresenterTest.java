package com.sam_chordas.android.stockhawk.ui;

import com.sam_chordas.android.stockhawk.StockDetailView;
import com.sam_chordas.android.stockhawk.StockNetworkService;
import com.sam_chordas.android.stockhawk.data.StockLoaderService;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class StockDetailPresenterTest {
    @Test
    public void shouldKnowHowToLoadStockSymbolForAParticularQuote() {
        StockDetailView stockDetailView = mock(StockDetailView.class);
        StockLoaderService stockLoaderService = mock(StockLoaderService.class);
        StockNetworkService stockNetworkService = mock(StockNetworkService.class);
        StockDetailPresenter stockDetailPresenter = new StockDetailPresenter(stockDetailView, stockLoaderService, stockNetworkService);
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
}
