package com.java.linzexi;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.java.linzexi.NewsItemXRecyclerViewAdapter;
import com.java.linzexi.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

public class NewsItemXRecyclerViewFragment extends Fragment {
    public XRecyclerView newsRecyclerView;
    //private NewsItemXRecyclerViewAdapter newsRecyclerAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_x_recycler_view, container, false);
        newsRecyclerView = (XRecyclerView) view.findViewById(R.id.rv_main);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        newsRecyclerView.addItemDecoration(new MyDecoration());
        newsRecyclerView.setAdapter(new NewsItemXRecyclerViewAdapter(getContext()));
        //XRecyclerView Settings
        newsRecyclerView.getDefaultRefreshHeaderView().setRefreshTimeVisible(true);
        View header = LayoutInflater.from(getContext()).inflate(R.layout.fragment_news_x_recycler_view, container, false);
        newsRecyclerView.addHeaderView(header);
        newsRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        newsRecyclerView.refreshComplete();
                    }
                },3000);
            }

            @Override
            public void onLoadMore() {

            }
        });
        return view;
    }

    class MyDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(0,0,0,getResources().getDimensionPixelOffset(R.dimen.dividerHeright));
        }
    }
}
