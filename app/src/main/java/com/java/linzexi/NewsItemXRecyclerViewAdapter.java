package com.java.linzexi;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsItemXRecyclerViewAdapter extends RecyclerView.Adapter<NewsItemXRecyclerViewAdapter.XRecyclerViewHolder> {

    private Context mContext;
    private String newsContext = null;
    private NetWork netWork = null;
    private List<NewsContentDataModel> browsingHistory = new ArrayList<>();

    private List<NewsItemDataModel> newsList = new ArrayList<>();

    public NewsItemXRecyclerViewAdapter(Context context, List<NewsItemDataModel> l){
        mContext = context;
        newsList.clear();
        newsList.addAll(l);
    }

    public void upDateList(List<NewsItemDataModel> l){
        newsList.clear();
        newsList.addAll(l);
    }

    @NonNull
    @Override
    public XRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new XRecyclerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_recycler, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull XRecyclerViewHolder holder, final int position) {
        if(newsList.get(position).title.length() >= 30)
            holder.textView.setText(String.format("%s...", newsList.get(position).title.substring(0, 30)));
        else
            holder.textView.setText(newsList.get(position).title);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String click_id = newsList.get(position).id;
                netWork = new NetWork(String.format("https://covid-dashboard.aminer.cn/api/event/%s",click_id));
                newsContext = netWork.getStringResult();
                String content = null;
                try{
                    JSONObject newsObj = new JSONObject(newsContext);
                    newsObj = newsObj.getJSONObject("data");
                    String id = newsObj.getString("_id");
                    content = newsObj.getString("content");
                    String date = newsObj.getString("date");
                    System.out.println(id);
                    System.out.println(content);
                    browsingHistory.add(new NewsContentDataModel(id, content, date));
                }catch(JSONException e){
                    e.printStackTrace();
                }

                Toast.makeText(mContext, "Click " + content, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
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
