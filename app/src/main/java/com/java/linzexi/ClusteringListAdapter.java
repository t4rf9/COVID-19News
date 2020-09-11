package com.java.linzexi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class ClusteringListAdapter extends BaseAdapter {
    List<ClusteringModel> list = new ArrayList<>();

    public ClusteringListAdapter(List<ClusteringModel> l){
        list.addAll(l);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_clustering_list, null, false);
        }
        TextView textView = view.findViewById(R.id.clustering_layout);
        textView.setText(list.get(i).keyWords);
        return view;
    }
}
