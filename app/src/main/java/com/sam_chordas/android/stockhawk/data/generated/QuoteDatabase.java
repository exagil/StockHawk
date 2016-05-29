package com.sam_chordas.android.stockhawk.data.generated;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sam_chordas.android.stockhawk.data.HistoryColumns;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;

public class QuoteDatabase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 7;

    public static final String QUOTES = "CREATE TABLE quotes ("
            + QuoteColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + QuoteColumns.SYMBOL + " TEXT NOT NULL,"
            + QuoteColumns.PERCENT_CHANGE + " TEXT NOT NULL,"
            + QuoteColumns.CHANGE + " TEXT NOT NULL,"
            + QuoteColumns.BIDPRICE + " TEXT NOT NULL,"
            + QuoteColumns.CREATED + " TEXT,"
            + QuoteColumns.ISUP + " INTEGER NOT NULL,"
            + QuoteColumns.ISCURRENT + " INTEGER NOT NULL)";

    public static final String HISTORY = "CREATE TABLE history ("
            + HistoryColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + HistoryColumns.SYMBOL + " TEXT NOT NULL,"
            + HistoryColumns.DATE + " NUMERIC NOT NULL,"
            + HistoryColumns.OPEN + " REAL NOT NULL,"
            + HistoryColumns.HIGH + " REAL NOT NULL,"
            + HistoryColumns.LOW + " REAL NOT NULL,"
            + HistoryColumns.CLOSE + " REAL,"
            + HistoryColumns.VOLUME + " INTEGER NOT NULL,"
            + HistoryColumns.ADJ_CLOSE + " REAL NOT NULL,"
            + "UNIQUE (" + HistoryColumns.SYMBOL + "," + HistoryColumns.DATE + ") ON CONFLICT REPLACE)";

    private static volatile QuoteDatabase instance;

    private Context context;

    private QuoteDatabase(Context context) {
        super(context, "quoteDatabase.db", null, DATABASE_VERSION);
        this.context = context;
    }

    public static QuoteDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (QuoteDatabase.class) {
                if (instance == null) {
                    instance = new QuoteDatabase(context);
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUOTES);
        db.execSQL(HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
