package com.java.linzexi;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.java.linzexi.JSONHandler.RemoteJSONFetcher;
import com.java.linzexi.database.NewsEntity;

import java.util.ArrayList;
import java.util.List;

public class NewsItemXRecyclerViewAdapter extends RecyclerView.Adapter<NewsItemXRecyclerViewAdapter.XRecyclerViewHolder> {

    private Context mContext;
    private String newsContext = null;
    private RemoteJSONFetcher netWork = null;
    private List<NewsEntity> browsingHistory = new ArrayList<>();

    private List<NewsEntity> newsEntityList = new ArrayList<>();

    public NewsItemXRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    public void resetList(List<NewsEntity> l) {
        newsEntityList.clear();
        newsEntityList.addAll(l);
    }

    public void appendList(List<NewsEntity> l) {
        newsEntityList.addAll(l);
    }

    @NonNull
    @Override
    public XRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new XRecyclerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_recycler, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull XRecyclerViewHolder holder, final int position) {
        if (newsEntityList.get(position).getTitle().length() >= 30) {
            holder.textView.setText(String.format("%s...", newsEntityList.get(position).getTitle().substring(0, 30)));
        } else {
            holder.textView.setText(newsEntityList.get(position).getTitle());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*String click_id = newsList.get(position).get_id();
                netWork = new Network(String.format("https://covid-dashboard.aminer.cn/api/event/%s", click_id));
                newsContext = netWork.getStringResult();
                String content = null;
                try {
                    JSONObject newsObj = new JSONObject(newsContext);
                    newsObj = newsObj.getJSONObject("data");
                    String id = newsObj.getString("_id");
                    content = newsObj.getString("content");
                    String date = newsObj.getString("date");
                    System.out.println(id);
                    System.out.println(content);
                    browsingHistory.add(new NewsContentDataModel(id, content, date));
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/

                Toast.makeText(mContext, "Click " + newsEntityList.get(position).getContent(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsEntityList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class XRecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public XRecyclerViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.news_title);
        }
    }
}
