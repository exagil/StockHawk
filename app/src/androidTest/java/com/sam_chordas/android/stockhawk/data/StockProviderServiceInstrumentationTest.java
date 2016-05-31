package com.sam_chordas.android.stockhawk.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.test.AndroidTestCase;

import com.google.gson.Gson;
import com.sam_chordas.android.stockhawk.data.generated.HistoryProvider;
import com.sam_chordas.android.stockhawk.data.generated.QuoteProvider;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuote;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuoteDate;
import com.sam_chordas.android.stockhawk.data.dto.HistoricalQuoteResponse;
import com.sam_chordas.android.stockhawk.data.models.HistoricalQuotes;
import com.sam_chordas.android.stockhawk.data.dto.HistoricalQuotesResponse;
import com.sam_chordas.android.stockhawk.data.models.NullHistoricalQuotes;
import com.sam_chordas.android.stockhawk.data.models.Quote;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static com.sam_chordas.android.stockhawk.data.StockProviderService.QuoteLoaderCallback;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class StockProviderServiceInstrumentationTest extends AndroidTestCase {

    public static final long MILLISECONDS_UNTIL_20160522_0626HRS = 1463899584953l;
    public static final long MILLISECONDS_UNTIL_20160524_1010HRS = 1464084610000l;
    public static final long MILLISECONDS_UNTIL_20160529_1010HRS = 1464516610000l;

    @Before
    public void setup() {
        resetDatabase();
    }

    @After
    public void tearDown() {
        resetDatabase();
    }

    @Test
    public void shouldNotLoadQuoteForStockWhichDoesNotExistLocally() throws InterruptedException {
        StockProviderService stockProviderService = new StockProviderService(getContext());
        QuoteLoaderCallback quoteLoaderCallback = Mockito.mock(QuoteLoaderCallback.class);
        stockProviderService.loadQuoteWithId(1, quoteLoaderCallback);
        Thread.sleep(1000);
        verify(quoteLoaderCallback).onQuoteLoadFailed();
        verifyNoMoreInteractions(quoteLoaderCallback);
    }

    @Test
    public void shouldLoadQuoteWhenExistsLocally() throws InterruptedException {
        StockProviderService stockProviderService = new StockProviderService(getContext());
        ContentValues quoteContentValues = new ContentValues();
        quoteContentValues.put(QuoteColumns.SYMBOL, "YHOO");
        quoteContentValues.put(QuoteColumns.PERCENT_CHANGE, "20");
        quoteContentValues.put(QuoteColumns.CHANGE, "10");
        quoteContentValues.put(QuoteColumns.BIDPRICE, "10");
        quoteContentValues.put(QuoteColumns.CREATED, "20-10-2016");
        quoteContentValues.put(QuoteColumns.ISUP, 1);
        quoteContentValues.put(QuoteColumns.ISCURRENT, 1);
        getContext().getContentResolver().insert(QuoteProvider.Quotes.CONTENT_URI, quoteContentValues);
        Quote quote = Quote.fromContentValues(quoteContentValues);
        QuoteLoaderCallback quoteLoaderCallback = Mockito.mock(QuoteLoaderCallback.class);
        stockProviderService.loadQuoteWithId(1, quoteLoaderCallback);
        Thread.sleep(1000);
        verify(quoteLoaderCallback).onQuoteLoaded(quote);
        verifyNoMoreInteractions(quoteLoaderCallback);
    }

    @Test
    public void testThatStockProviderServiceKnowsHowToInsertHistoricalQuotes() throws ParseException {
        StockProviderService stockProviderService = new StockProviderService(getContext());
        HistoricalQuote firstHistoricalQuote = new HistoricalQuote("FB", new Date(1463899584953l), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3));
        HistoricalQuote secondHistoricalQuote = new HistoricalQuote("FB", new Date(1464084610000l), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3));
        HistoricalQuotes expectedHistoricalQuotes = new HistoricalQuotes(firstHistoricalQuote, secondHistoricalQuote);
        stockProviderService.insertHistoricalQuotes(expectedHistoricalQuotes);
        Cursor historyCursor = getContext().getContentResolver().query(HistoryProvider.History.CONTENT_URI, null,
                HistoryColumns.SYMBOL + "=?", new String[]{"FB"}, null);
        HistoricalQuotes actualHistoricalQuotes = HistoricalQuotes.fromCursor(historyCursor);
        assertEquals(expectedHistoricalQuotes, actualHistoricalQuotes);
    }

    @Test
    public void testStockProviderServiceKnowsHowToInsertStocksWhenSameStocksInserted() throws ParseException {
        StockProviderService stockProviderService = new StockProviderService(getContext());
        HistoricalQuote firstHistoricalQuote = new HistoricalQuote("FB", new Date(1463899584953l), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3));
        HistoricalQuote secondHistoricalQuote = new HistoricalQuote("FB", new Date(1463998210000l), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3));
        HistoricalQuote thirdHistoricalQuote = new HistoricalQuote("FB", new Date(1464084610000l), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3));
        HistoricalQuotes formerHistoricalQuotes = new HistoricalQuotes(firstHistoricalQuote, secondHistoricalQuote);
        HistoricalQuotes latterHistoricalQuotes = new HistoricalQuotes(secondHistoricalQuote, thirdHistoricalQuote);
        stockProviderService.insertHistoricalQuotes(formerHistoricalQuotes);
        stockProviderService.insertHistoricalQuotes(latterHistoricalQuotes);

        HistoricalQuotes expectedHistoricalQuotes = new HistoricalQuotes(firstHistoricalQuote, secondHistoricalQuote, thirdHistoricalQuote);
        Cursor historyCursor = getContext().getContentResolver().query(HistoryProvider.History.CONTENT_URI, null,
                HistoryColumns.SYMBOL + "=?", new String[]{"FB"}, null);
        HistoricalQuotes actualHistoricalQuotes = HistoricalQuotes.fromCursor(historyCursor);

        assertEquals(expectedHistoricalQuotes, actualHistoricalQuotes);
    }

    @Test
    public void testThatStockProviderServiceLoadsNoHistoricalQuotesWhenNonePresent() throws ParseException {
        HistoricalQuotes expectedHistoricalQuotes = new NullHistoricalQuotes();
        StockProviderService stockProviderService = new StockProviderService(getContext());
        HistoricalQuoteDate historicalQuoteDate = HistoricalQuoteDate.fromMilliseconds(MILLISECONDS_UNTIL_20160524_1010HRS);
        assertEquals(expectedHistoricalQuotes, stockProviderService.loadOneMonthsHistoricalQuotes("APPL", historicalQuoteDate));
    }

    @Test
    public void testThatStockProviderServiceKnowsHowToFetchOneMonthsHistoricalQuotesForAParticularSymbolGivenThatOneMonthsStocksDontExist() throws ParseException {
        HistoricalQuote firstHistoricalQuote = new HistoricalQuote("APPL", new Date(MILLISECONDS_UNTIL_20160522_0626HRS), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3));
        HistoricalQuote secondHistoricalQuote = new HistoricalQuote("APPL", new Date(MILLISECONDS_UNTIL_20160524_1010HRS), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3), new Double(2.3));
        HistoricalQuotes expectedHistoricalQuotes = new HistoricalQuotes(firstHistoricalQuote, secondHistoricalQuote);
        StockProviderService stockProviderService = new StockProviderService(getContext());
        stockProviderService.insertHistoricalQuotes(expectedHistoricalQuotes);
        HistoricalQuoteDate historicalQuoteDate = HistoricalQuoteDate.fromMilliseconds(MILLISECONDS_UNTIL_20160524_1010HRS);
        assertEquals(expectedHistoricalQuotes, stockProviderService.loadOneMonthsHistoricalQuotes("APPL", historicalQuoteDate));
    }

    @Test
    public void testThatStockProviderServiceKnowsHowToFetchOneMonthsHistoricalQuotesGivenThatTheyExist() throws ParseException {
        InputStreamReader historicalQuotesReader = new InputStreamReader(HistoricalQuoteResponse.class.getClassLoader().getResourceAsStream("historical_quotes_fb_20160429_20160527.json"));
        HistoricalQuotesResponse historicalQuotesResponse = new Gson().fromJson(historicalQuotesReader, HistoricalQuotesResponse.class);
        final List<HistoricalQuote> historicalQuoteList = historicalQuotesResponse.toHistoricalQuotes();
        HistoricalQuotes historicalQuotes = new HistoricalQuotes(historicalQuoteList);
        StockProviderService stockProviderService = new StockProviderService(getContext());
        stockProviderService.insertHistoricalQuotes(historicalQuotes);
        HistoricalQuoteDate historicalQuoteDate = HistoricalQuoteDate.fromMilliseconds(MILLISECONDS_UNTIL_20160529_1010HRS);
        HistoricalQuotes expectedHistoricalQuotesCollection = new HistoricalQuotes(historicalQuotes.sortedCollection());
        HistoricalQuotes actualHistoricalQuotesCollection = stockProviderService.loadOneMonthsHistoricalQuotes("FB", historicalQuoteDate);
        assertEquals(expectedHistoricalQuotesCollection, actualHistoricalQuotesCollection);
    }

    @Test
    public void testThatStockProviderServiceKnowsHowToFetchExactlyOneMonthsHistoricalQuotesGivenQuotesMoreThanOneMonthExist() throws ParseException {
        InputStreamReader historicalQuotesReader = new InputStreamReader(HistoricalQuoteResponse.class.getClassLoader().getResourceAsStream("historical_quotes_fb_20160329_20160527.json"));
        HistoricalQuotesResponse historicalQuotesResponse = new Gson().fromJson(historicalQuotesReader, HistoricalQuotesResponse.class);
        final List<HistoricalQuote> historicalQuoteList = historicalQuotesResponse.toHistoricalQuotes();
        HistoricalQuotes historicalQuotes = new HistoricalQuotes(historicalQuoteList);
        StockProviderService stockProviderService = new StockProviderService(getContext());
        stockProviderService.insertHistoricalQuotes(historicalQuotes);

        InputStreamReader expectedHistoricalQuotesReader = new InputStreamReader(HistoricalQuoteResponse.class.getClassLoader().getResourceAsStream("historical_quotes_fb_20160429_20160527.json"));
        HistoricalQuotesResponse expectedHistoricalQuotesResponse = new Gson().fromJson(expectedHistoricalQuotesReader, HistoricalQuotesResponse.class);
        final List<HistoricalQuote> expectedHistoricalQuoteList = expectedHistoricalQuotesResponse.toHistoricalQuotes();
        HistoricalQuotes expectedHistoricalQuotes = new HistoricalQuotes(expectedHistoricalQuoteList);

        HistoricalQuoteDate historicalQuoteDate = HistoricalQuoteDate.fromMilliseconds(MILLISECONDS_UNTIL_20160529_1010HRS);
        HistoricalQuotes expectedHistoricalQuotesCollection = new HistoricalQuotes(expectedHistoricalQuotes.sortedCollection());
        HistoricalQuotes actualHistoricalQuotesCollection = stockProviderService.loadOneMonthsHistoricalQuotes("FB", historicalQuoteDate);
        assertEquals(expectedHistoricalQuotesCollection, actualHistoricalQuotesCollection);
    }

    private void resetDatabase() {
        getContext().deleteDatabase("quoteDatabase.db");
        getContext().openOrCreateDatabase("quoteDatabase.db", Context.MODE_PRIVATE, null);
    }
}
