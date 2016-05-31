package com.sam_chordas.android.stockhawk.data.models;

import android.content.Context;

import com.sam_chordas.android.stockhawk.R;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QuoteViewModelTest {
    @Test
    public void testThatQuoteViewModelKnowsHowToDisplayBidPrice() {
        Context context = mock(Context.class);
        when(context.getString(R.string.quote_bid_price)).thenReturn("Bid Price: %s");
        Quote quote = new Quote("FB", 1.23f, 4.56f, 9.99d, PersistableBoolean.TRUE, PersistableBoolean.FALSE);
        QuoteViewModel quoteViewModel = new QuoteViewModel(context, quote);
        assertEquals("Bid Price: 9.99", quoteViewModel.bidPrice());
    }

    @Test
    public void testThatQuoteViewModelKnowsHowToDisplayQuoteName() {
        Context context = mock(Context.class);
        Quote quote = new Quote("FB", 1.23f, 4.56f, 9.99d, PersistableBoolean.TRUE, PersistableBoolean.FALSE);
        QuoteViewModel quoteViewModel = new QuoteViewModel(context, quote);
        assertEquals("FB", quoteViewModel.symbol());
    }

    @Test
    public void testThatQuoteViewModelKnowsHowToDisplayQuotePercentChange() {
        Context context = mock(Context.class);
        when(context.getString(R.string.quote_percent_change)).thenReturn("Percent Change: %s%%");
        Quote quote = new Quote("FB", 1.23f, 4.56f, 9.99d, PersistableBoolean.TRUE, PersistableBoolean.FALSE);
        QuoteViewModel quoteViewModel = new QuoteViewModel(context, quote);
        assertEquals("Percent Change: 1.23%", quoteViewModel.percentChange());
    }
}
