package com.java.linzexi;

import android.app.ActionBar;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toolbar;

import com.java.linzexi.JSONHandler.NewsEntityLoader;
import com.java.linzexi.database.AppDatabase;
import com.java.linzexi.database.NewsEntity;

import java.util.List;

public class NewsSearchResultActivity extends ListActivity {

    private List<NewsEntity> resList;

    private NewsSearchResultActivity thisActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_search_result);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);


        ActionBar actionBar = getActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            final AppDatabase db = ((COVID19NewsApp) getApplication()).getDatabase();
            final String query = intent.getStringExtra(SearchManager.QUERY);

            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    NewsSearchSuggestionProvider.AUTHORITY, NewsSearchSuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    resList = db.newsDao().searchNewsTitle(query);
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            setListAdapter(new BaseAdapter() {

                @Override
                public int getCount() {
                    return resList.size();
                }

                @Override
                public Object getItem(int i) {
                    return resList.get(i);
                }

                @Override
                public long getItemId(int i) {
                    return i;
                }

                @Override
                public View getView(int i, View view, ViewGroup viewGroup) {
                    if (view == null) {
                        view = getLayoutInflater().inflate(R.layout.layout_recycler, viewGroup, false);
                    }

                    final NewsEntity newsEntity = (NewsEntity) getItem(i);
                    final TextView textView = view.findViewById(R.id.news_title);
                    textView.setText(newsEntity.getTitle());

                    final boolean read = newsEntity.getRead();
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            textView.setTextColor(getColor(R.color.readNews));

                            NewsEntity newsEntity_display;
                            if (!read) {
                                final NewsEntity newsEntity_fresh = NewsEntityLoader.getNewsEntity(newsEntity.get_id());
                                Thread thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        db.newsDao().updateNews(newsEntity_fresh);
                                    }
                                });
                                thread.start();
                                newsEntity_display = newsEntity_fresh;
                            } else {
                                newsEntity_display = newsEntity;
                            }
                            Intent intent = new Intent(thisActivity, NewsDetailActivity.class);
                            intent.putExtra("time", newsEntity_display.getTime());
                            intent.putExtra("source", newsEntity_display.getSource());
                            intent.putExtra("title", newsEntity_display.getTitle());
                            intent.putExtra("content", newsEntity_display.getContent());
                            startActivity(intent);
                        }
                    });
                    return view;
                }
            });
        }
    }
}