package com.java.linzexi;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ScholarsFragment extends Fragment {

    private ScholarsViewModel mViewModel;
    List<ScholarsModel> list = new ArrayList<>();

    public static ScholarsFragment newInstance() {
        return new ScholarsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ScholarsViewModel.class);
        list.addAll(mViewModel.list);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scholars_fragment, container, false);
        ListView listView = view.findViewById(R.id.scholars_list);
        listView.setAdapter(new ScholarsViewAdapter(mViewModel.list));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ScholarsDetailActivity.class);
                intent.putExtra("name", list.get(i).name);
                intent.putExtra("name_zh", list.get(i).name_zh);
                intent.putExtra("introduction", list.get(i).introduction);
                intent.putExtra("avatar", list.get(i).avatar);
                intent.putExtra("position", list.get(i).position);
                getActivity().startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
    }

}