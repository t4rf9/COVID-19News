package com.java.linzexi;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.java.linzexi.JSONHandler.NewsEntityLoader;
import com.java.linzexi.database.AppDatabase;
import com.java.linzexi.database.NewsEntity;

import java.util.ArrayList;
import java.util.List;

public class NewsItemXRecyclerViewAdapter extends RecyclerView.Adapter<NewsItemXRecyclerViewAdapter.XRecyclerViewHolder> {

    private Activity activity;
    private FragmentManager fragmentManager;
    private String newsContext = null;
    private List<NewsEntity> browsingHistory = new ArrayList<>();

    private List<NewsEntity> newsEntityList = new ArrayList<>();

    public NewsItemXRecyclerViewAdapter(Activity activity, FragmentManager fragmentManager) {
        this.activity = activity;
        this.fragmentManager = fragmentManager;
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
        return new XRecyclerViewHolder(LayoutInflater.from(activity).inflate(R.layout.layout_recycler, parent, false));
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
        if (read) {
            holder.textView.setTextColor(holder.textView.getResources().getColor(R.color.readNews));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AppDatabase db = ((COVID19NewsApp) activity.getApplication()).getDatabase();
                activity.findViewById(R.id.tabLayout).setVisibility(View.GONE);

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if (!read) {
                    final NewsEntity newsEntity_fresh = NewsEntityLoader.getNewsEntity(newsEntity.get_id());
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            db.newsDao().updateNews(newsEntity_fresh);
                        }
                    });
                    fragmentTransaction.replace(R.id.host_fragment_container, NewsDetailFragment.newInstance(newsEntity_fresh));
                } else {
                    fragmentTransaction.replace(R.id.host_fragment_container, NewsDetailFragment.newInstance(newsEntity));
                }
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
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
