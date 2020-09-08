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
    List<NewsItemDataModel> newsList = new ArrayList<>();
    //private NewsItemXRecyclerViewAdapter newsRecyclerAdapter;

    private void loadNewsItem(){
        try{
            newsList.clear();
            JSONObject obj = new JSONObject(context);
            JSONArray arr = obj.getJSONArray("datas");
            for(int i = 0; i < arr.length(); i ++){
                JSONObject object = arr.getJSONObject(i);
                String id = object.getString("_id");
                String type = object.getString("type");
                String title = object.getString("title");
                String time = object.getString("time");
                newsList.add(new NewsItemDataModel(id, type, title, time));
            }
        }
        catch (JSONException e){
            e.printStackTrace();
//            System.out.println("Why???????");
//            Looper.prepare();
//            Toast.makeText(getContext(),"文件解析错误！",Toast.LENGTH_SHORT).show();
//            Looper.loop();
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        netWork = new NetWork("https://covid-dashboard.aminer.cn/api/dist/events.json");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_x_recycler_view, container, false);
        context  = netWork.getStringResult();
        if(context == null){
            System.out.println("Connection Failed...");
            return view;
        }

        loadNewsItem();

        newsRecyclerView = (XRecyclerView) view.findViewById(R.id.rv_main);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        newsRecyclerView.addItemDecoration(new MyDecoration());
        newsRecyclerView.setAdapter(new NewsItemXRecyclerViewAdapter(getContext(), newsList));
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
                            newsList.remove(0);
                            newsRecyclerView.setAdapter(new NewsItemXRecyclerViewAdapter(getContext(), newsList));
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
