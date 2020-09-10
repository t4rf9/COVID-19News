package com.java.linzexi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ScholarsViewAdapter extends BaseAdapter {
    List<ScholarsModel> list = new ArrayList<>();

    public ScholarsViewAdapter(List<ScholarsModel> l){
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
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.scholars_list_layout, null, false);
        }
        TextView textView = view.findViewById(R.id.name);
        if(!list.get(i).name_zh.equals(""))
            textView.setText(list.get(i).name_zh);
        else
            textView.setText(list.get(i).name);
        ImageView imageView = view.findViewById(R.id.photo);
        if(list.get(i).avatar != null)
            Glide.with(viewGroup.getContext()).load(list.get(i).avatar).into(imageView);
        return view;
    }
}
