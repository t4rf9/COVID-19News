package com.java.linzexi;


import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.java.linzexi.JSONHandler.NewsEntityLoader;
import com.java.linzexi.database.AppDatabase;
import com.java.linzexi.database.NewsEntity;

import java.util.ArrayList;
import java.util.List;

public class NewsItemXRecyclerViewAdapter extends RecyclerView.Adapter<NewsItemXRecyclerViewAdapter.XRecyclerViewHolder> {

    private Activity mActivity;

    private List<NewsEntity> newsEntityList = new ArrayList<>();

    public NewsItemXRecyclerViewAdapter(Activity activity) {
        this.mActivity = activity;
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
        return new XRecyclerViewHolder(LayoutInflater.from(mActivity).inflate(R.layout.layout_recycler, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull final XRecyclerViewHolder holder, final int position) {
        final NewsEntity newsEntity = newsEntityList.get(position);
        String title = newsEntity.getTitle();
        if (title.length() >= 30) {
            holder.textView.setText(String.format("%s...", title.substring(0, 30)));
        } else {
            holder.textView.setText(title);
        }
        final boolean read = newsEntity.getRead();
        holder.textView.setTextColor(mActivity.getColor(read ? R.color.readNews : R.color.design_default_color_on_secondary));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AppDatabase db = ((COVID19NewsApp) mActivity.getApplication()).getDatabase();

                NewsEntity newsEntity_display = newsEntity;
                if (!read) {
                    final NewsEntity newsEntity_fresh = NewsEntityLoader.getNewsEntity(newsEntity.get_id());
                    if (newsEntity_fresh != null) {
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                db.newsDao().updateNews(newsEntity_fresh);
                            }
                        });
                        thread.start();
                        newsEntityList.remove(position);
                        newsEntityList.add(position, newsEntity_fresh);
                        newsEntity_display = newsEntity_fresh;
                        holder.textView.setTextColor(mActivity.getColor(R.color.readNews));
                    }
                }
                Intent intent = new Intent(mActivity, NewsDetailActivity.class);
                intent.putExtra("time", newsEntity_display.getTime());
                intent.putExtra("source", newsEntity_display.getSource());
                intent.putExtra("title", newsEntity_display.getTitle());
                intent.putExtra("content", newsEntity_display.getContent());
                mActivity.startActivity(intent);

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
