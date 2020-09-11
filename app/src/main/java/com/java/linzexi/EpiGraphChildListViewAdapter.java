package com.java.linzexi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EpiGraphChildListViewAdapter extends BaseAdapter {
    List<SearchResultRelationModel> srrl = new ArrayList<>();

    public EpiGraphChildListViewAdapter(List<SearchResultRelationModel> l){
        srrl.addAll(l);
        //System.out.println(srrl.size() + "KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
    }
//    @Override
//    public boolean areAllItemsEnabled() {
//        return false;
//    }
//
//    @Override
//    public boolean isEnabled(int position) {
//        return false;
//    }

    @Override
    public int getCount() {
        return srrl.size();
    }



    @Override
    public Object getItem(int i) {
        return srrl.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.epi_graph_child_list_layout, null, false);
        }

        TextView textView = view.findViewById(R.id.relation);
        textView.setText(srrl.get(i).relation);
        textView = view.findViewById(R.id.arrow);
        if(srrl.get(i).forward)
            textView.setText("\u2192");
        else
            textView.setText("\u2190");
        textView = view.findViewById(R.id.label);
        textView.setText(srrl.get(i).label);
        return view;
    }
}
