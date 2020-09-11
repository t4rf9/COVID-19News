package com.java.linzexi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class EpiGraphViewAdapter extends BaseExpandableListAdapter {
    List<SearchResultModel> srl = new ArrayList<>();

    public EpiGraphViewAdapter(List<SearchResultModel> l) {
        srl.addAll(l);
    }

    public void changeAdapter(List<SearchResultModel> l) {
        srl.clear();
        srl.addAll(l);
    }

    @Override
    public int getGroupCount() {
        return srl.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return srl.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        switch (i1) {
            case 0:
                return srl.get(i).name;
            case 1:
                return srl.get(i).img;
            case 2:
                return srl.get(i).wikiInfo;
            case 3:
                return srl.get(i).properties;
            default:
                return srl.get(i).srl.get(i - 4);
        }
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.epi_graph_group_layout, null, false);
        }
        TextView textView = view.findViewById(R.id.group_name);
        textView.setText(srl.get(i).name);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.epi_graph_child_layout, null, false);
        }

        TextView textView = view.findViewById(R.id.child_label);
        textView.setText(srl.get(i).name);
        ImageView imageView = view.findViewById(R.id.child_img);
        if (srl.get(i).img != null)
            Glide.with(viewGroup.getContext()).load(srl.get(i).img).into(imageView);
        textView = view.findViewById(R.id.child_wiki);
        textView.setText(srl.get(i).wikiInfo);
        textView = view.findViewById(R.id.child_properties);
        textView.setText(srl.get(i).properties);
        ListView listView = view.findViewById(R.id.child_list);
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = srl.get(i).srl.size() * 120;
        listView.setLayoutParams(params);
        listView.setAdapter(new EpiGraphChildListViewAdapter(srl.get(i).srl));
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
