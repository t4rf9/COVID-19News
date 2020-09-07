package com.example.covid_19news;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsItemXRecyclerViewAdapter extends RecyclerView.Adapter<NewsItemXRecyclerViewAdapter.XRecyclerViewHolder> {

    private Context mContext;

    public NewsItemXRecyclerViewAdapter(Context context){
        mContext = context;
    }

    @Override
    public XRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new XRecyclerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_recycler, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull XRecyclerViewHolder holder, int position) {
        holder.textView.setText(position);
    }

    @Override
    public int getItemCount() {
        return 10;
        //暂时取10， 可以增加参数list， 此处返回list长度
    }

    class XRecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public XRecyclerViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.news_title);
        }
    }
}
