package com.java.linzexi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ClusteringDetailListAdapter extends BaseAdapter {
    List<String> events = new ArrayList<>();

    public ClusteringDetailListAdapter(List<String> e){
        events.addAll(e);
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int i) {
        return events.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_clustering_detail_list, null, false);
        }
        TextView textView = view.findViewById(R.id.clustering_detail_layout);
        textView.setText(events.get(i));
        return view;
    }
}
