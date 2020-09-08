package com.java.linzexi;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
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
    List<NewsItemDataModel> wholeList = new ArrayList<>();
    List<NewsItemDataModel> newsList = new ArrayList<>();
    List<NewsItemDataModel> paperList = new ArrayList<>();
    //private NewsItemXRecyclerViewAdapter newsRecyclerAdapter;

    private void loadNewsItem(){
        try{
            wholeList.clear();
            newsList.clear();
            paperList.clear();
            JSONObject obj = new JSONObject(context);
            JSONArray arr = obj.getJSONArray("datas");
            for(int i = 0; i < arr.length(); i ++){
                JSONObject object = arr.getJSONObject(i);
                String id = object.getString("_id");
                String type = object.getString("type");
                String title = object.getString("title");
                String time = object.getString("time");
                wholeList.add(new NewsItemDataModel(id, type, title, time));
                if(type.equals("news"))
                    newsList.add(new NewsItemDataModel(id, type, title, time));
                if(type.equals("paper"))
                    paperList.add(new NewsItemDataModel(id, type, title, time));
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }

    }

    public void changeShow(int type){
        type_now = type;
        if(type == 0){
            newsRecyclerView.setAdapter(new NewsItemXRecyclerViewAdapter(getContext(), wholeList));
        }else if(type == 1){
            newsRecyclerView.setAdapter(new NewsItemXRecyclerViewAdapter(getContext(), newsList));
        }else if(type == 2){
            newsRecyclerView.setAdapter(new NewsItemXRecyclerViewAdapter(getContext(), paperList));
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        netWork = new NetWork("https://covid-dashboard.aminer.cn/api/dist/events.json");
        context  = netWork.getStringResult();
        if(context == null)
            System.out.println("Connection Failed...");
        else
            loadNewsItem();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_x_recycler_view, container, false);

        newsRecyclerView = (XRecyclerView) view.findViewById(R.id.rv_main);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        newsRecyclerView.addItemDecoration(new MyDecoration());
        newsRecyclerView.setAdapter(new NewsItemXRecyclerViewAdapter(getContext(), wholeList));
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
                        context  = netWork.getStringResult();
                        if(context == null){
                            System.out.println("Connection Failed...");
                        }
                        else{
                            loadNewsItem();
                            changeShow(type_now);
                        }

                        newsRecyclerView.refreshComplete();
                    }
                },1000);
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
