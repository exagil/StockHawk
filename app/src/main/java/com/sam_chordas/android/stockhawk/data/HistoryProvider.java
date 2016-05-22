package com.sam_chordas.android.stockhawk.data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

@ContentProvider(authority = HistoryProvider.AUTHORITY, database = QuoteDatabase.class)
public class HistoryProvider {
    public static final String AUTHORITY = "com.sam_chordas.android.stockhawk.data.HistoryProvider";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    interface Path {
        String HISTORY = "history";
    }

    private static Uri buildUri(String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    @TableEndpoint(table = QuoteDatabase.HISTORY)
    public static class History {
        @ContentUri(
                path = Path.HISTORY,
                type = "vnd.android.cursor.dir/history"
        )
        public static final Uri CONTENT_URI = buildUri(Path.HISTORY);
    }
}
