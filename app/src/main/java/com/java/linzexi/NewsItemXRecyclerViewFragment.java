package com.java.linzexi;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.java.linzexi.JSONHandler.RemoteJSONFetcher;
import com.java.linzexi.database.AppDatabase;
import com.java.linzexi.database.NewsEntity;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class NewsItemXRecyclerViewFragment extends Fragment {
    public XRecyclerView newsRecyclerView;
    private AppExecutors mExecutors;
    private AppDatabase db;

    private String context = null;
    private int type_id_curr = 0;

    private NewsItemXRecyclerViewAdapter newsRecyclerAdapter;

    private int[] pages = new int[2];
    private int page_size = 20;

    public void loadNewsListFromInternet(final int type_id) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int page = 0;
                while (true) {
                    page++;
                    RemoteJSONFetcher remoteJSONFetcher = new RemoteJSONFetcher("https://covid-dashboard.aminer.cn/api/events/list?type="
                            + (type_id == 0 ? "news" : "paper") + "&page=" + page + "&size=" + page_size);

                    String jsonFileString = remoteJSONFetcher.getJSONFileString();
                    try {
                        JSONObject obj = new JSONObject(jsonFileString);
                        JSONArray jsonArray = obj.getJSONArray("data");
                        if (jsonArray.length() == 0) {
                            return;
                        }
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            String _id = object.getString("_id");
                            String type = object.getString("type");
                            String title = object.getString("title");
                            String time = object.getString("time").replaceAll("-", "/");
                            System.err.println(time);
                            String source = object.getString("source");
                            String content = object.getString("content");

                            if (db.newsDao().loadNews(_id) == null) {
                                db.newsDao().insertNews(new NewsEntity(false, _id, type, time, source, title, content));
                            } else {
                                return;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace(System.err);
                    }
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace(System.err);
        }
    }

    public void changeShow(int type_id) {
        type_id_curr = type_id;
        if (pages[type_id_curr] == 0) {
            refreshShow();
        } else {
            final String type = type_id_curr == 0 ? "news" : "paper";
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    newsRecyclerAdapter.resetList(db.newsDao().loadNews(type, 0, pages[type_id_curr] * page_size));
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            newsRecyclerAdapter.notifyDataSetChanged();
        }
    }

    public void refreshShow() {
        final String type = type_id_curr == 0 ? "news" : "paper";
        pages[type_id_curr] = 1;
        loadNewsListFromInternet(type_id_curr);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                newsRecyclerAdapter.resetList(db.newsDao().loadNews(type, 0, page_size));
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        newsRecyclerAdapter.notifyDataSetChanged();
    }

    public void loadShow() {
        final String type = type_id_curr == 0 ? "news" : "paper";
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                newsRecyclerAdapter.appendList(db.newsDao().loadNews(type, pages[type_id_curr] * page_size, page_size));
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pages[type_id_curr]++;
        newsRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mExecutors = ((COVID19NewsApp) requireActivity().getApplication()).getExecutors();
        db = AppDatabase.getInstance(getActivity(), mExecutors);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_x_recycler_view, container, false);

        newsRecyclerView = (XRecyclerView) view.findViewById(R.id.rv_main);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        newsRecyclerView.addItemDecoration(new MyDecoration());

        newsRecyclerAdapter = new NewsItemXRecyclerViewAdapter(getActivity(), requireActivity().getSupportFragmentManager());
        newsRecyclerView.setAdapter(newsRecyclerAdapter);
        newsRecyclerView.getDefaultRefreshHeaderView().setRefreshTimeVisible(true);

        View header = LayoutInflater.from(getContext()).inflate(R.layout.fragment_news_x_recycler_view, container, false);
        newsRecyclerView.addHeaderView(header);

        newsRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        newsRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        newsRecyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);

        newsRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        refreshShow();
                        newsRecyclerView.refreshComplete();
                    }
                }, 500);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadShow();
                        newsRecyclerView.loadMoreComplete();
                    }
                }, 500);
            }
        });
        refreshShow();
        return view;
    }

    class MyDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(0, 0, 0, getResources().getDimensionPixelOffset(R.dimen.dividerHeright));
        }
    }
}
