package com.java.linzexi;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsItemXRecyclerViewFragment extends Fragment {
    public XRecyclerView newsRecyclerView;
    private NetWork netWork = null;
    private String context = null;
    private int type_now = 0;
    //List<NewsItemDataModel> eventList = new ArrayList<>();
    List<NewsItemDataModel> newsList = new ArrayList<>();
    List<NewsItemDataModel> paperList = new ArrayList<>();
    private NewsItemXRecyclerViewAdapter newsRecyclerAdapter;
    int []pages = new int[3];

    private void loadNewsItem(int type, int page){
        //type0:no use  1 for news  2 for paper
        if(type == 1)
            netWork.changeURL("https://covid-dashboard.aminer.cn/api/events/list?type=news&page=" + page);
        else if(type == 2){
            netWork.changeURL("https://covid-dashboard.aminer.cn/api/events/list?type=paper&page=" + page);
        }
        else
            return ;
        context = netWork.getStringResult();
        try{
            JSONObject obj = new JSONObject(context);
            JSONArray arr = obj.getJSONArray("data");
            for(int i = 0; i < arr.length(); i ++){
                JSONObject object = arr.getJSONObject(i);
                String id = object.getString("_id");
                String title = object.getString("title");
                String time = object.getString("date");
                if(type == 1){
                    newsList.add(new NewsItemDataModel(id, "news", title, time));
                }
                if(type == 2){
                    paperList.add(new NewsItemDataModel(id, "paper", title, time));
                }
            }
            pages[type] += 1;
        }
        catch (JSONException e){
            e.printStackTrace();
        }

    }

    public void changeShow(int type){
        type_now = type;
        if(pages[type] == 0){
            loadNewsItem(type, 1);
        }
        if(type == 1) {
            newsRecyclerAdapter.upDateList(newsList);
        }
        else if(type == 2){
            newsRecyclerAdapter.upDateList(paperList);
        }
        newsRecyclerAdapter.notifyDataSetChanged();
    }

    public void refreshShow(){
        pages[type_now] = 0;
        loadNewsItem(type_now, 1);
        if(type_now == 1){
            newsRecyclerAdapter.upDateList(newsList);
        }
        else if(type_now == 2){
            newsRecyclerAdapter.upDateList(paperList);
        }
        newsRecyclerAdapter.notifyDataSetChanged();
    }

    public void loadShow(){
        loadNewsItem(type_now, pages[type_now] + 1);
        if(type_now == 1){
            newsRecyclerAdapter.upDateList(newsList);
        }
        else if(type_now == 2){
            newsRecyclerAdapter.upDateList(paperList);
        }
        newsRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        netWork = new NetWork();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_x_recycler_view, container, false);

        newsRecyclerView = (XRecyclerView) view.findViewById(R.id.rv_main);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        newsRecyclerView.addItemDecoration(new MyDecoration());
        newsRecyclerAdapter = new NewsItemXRecyclerViewAdapter(getContext(), newsList);
        newsRecyclerView.setAdapter(newsRecyclerAdapter);
        newsRecyclerView.getDefaultRefreshHeaderView().setRefreshTimeVisible(true);
        View header = LayoutInflater.from(getContext()).inflate(R.layout.fragment_news_x_recycler_view, container, false);
        newsRecyclerView.addHeaderView(header);
        newsRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(type_now == 1)
                            newsList.clear();
                        else if(type_now == 2)
                            paperList.clear();
                        refreshShow();
                        newsRecyclerView.refreshComplete();
                    }
                },500);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadShow();
                        newsRecyclerView.loadMoreComplete();
                    }
                },500);
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
