package com.java.linzexi;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.java.linzexi.database.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class ClusteringFragment extends Fragment {
    private AppExecutors mExecutors;
    private AppDatabase db;
    private ClusteringViewModel mViewModel;
    List<ClusteringModel> list = new ArrayList<>();
    public static ClusteringFragment newInstance() {
        return new ClusteringFragment();
    }

    public void changeList(){
        List<ClusteringModel> temp_l = new ArrayList<>();
        temp_l.addAll(list);
        list.clear();
        for(int i = 0; i < temp_l.size(); i ++){
            List<String> ev = new ArrayList<>();
            for(int j = 0; j < temp_l.get(i).events.size(); j ++){
                ev.add(db.newsDao().loadNews(temp_l.get(i).events.get(j)).getTitle());
            }
            list.add(new ClusteringModel(temp_l.get(i).keyWords, ev));
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mExecutors = ((COVID19NewsApp) requireActivity().getApplication()).getExecutors();
        db = AppDatabase.getInstance(getActivity(), mExecutors);
        mViewModel = ViewModelProviders.of(this).get(ClusteringViewModel.class);
        mViewModel.run(getContext());
        list.addAll(mViewModel.getList());
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                changeList();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clustering, container, false);
        ListView listView = view.findViewById(R.id.clustering_list);
        listView.setAdapter(new ClusteringListAdapter(mViewModel.list));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ClusteringDetailActivity.class);
                intent.putStringArrayListExtra("events", (ArrayList<String>) list.get(i).events);
                getActivity().startActivity(intent);
            }
        });
        return view;
    }
}
