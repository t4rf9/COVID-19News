package com.java.linzexi;

import android.content.SearchRecentSuggestionsProvider;

public class NewsSearchSuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.java.linzexi.NewsSearchSuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public NewsSearchSuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }

}
