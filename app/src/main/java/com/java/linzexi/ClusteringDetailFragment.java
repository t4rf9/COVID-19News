package com.java.linzexi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class ClusteringDetailFragment extends Fragment {

    List<String> event = new ArrayList<>();

    public ClusteringDetailFragment() {
    }

    public static ClusteringDetailFragment newInstance(List<String> events) {
        ClusteringDetailFragment fragment = new ClusteringDetailFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("events", (ArrayList<String>) events);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            event.addAll(getArguments().getStringArrayList("events"));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clustering_detail, container, false);
        ListView listView = view.findViewById(R.id.events_list);
        listView.setAdapter(new ClusteringDetailListAdapter(event));
        return view;
    }
}
