package com.java.linzexi;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ExpandableListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

public class EpiGraphFragment extends Fragment {

    private EpiGraphViewModel mViewModel;
    private SearchView searchView;
    private EpiGraphViewAdapter adapter;
    private ExpandableListView listView;
    private List<SearchResultModel> srl = new ArrayList<>();

    public static EpiGraphFragment newInstance() {
        return new EpiGraphFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EpiGraphViewModel.class);


    }

    public void search(String s) {
        srl.clear();
        adapter.changeAdapter(srl);
        adapter.notifyDataSetChanged();
        srl.addAll(mViewModel.getSearchResult(s));
        adapter.changeAdapter(srl);
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.epi_graph_fragment, container, false);

        listView = (ExpandableListView) view.findViewById(R.id.list);

        adapter = new EpiGraphViewAdapter(srl);
        listView.setAdapter(adapter);


        searchView = view.findViewById(R.id.search_bar);
        searchView.setSubmitButtonEnabled(true);
        //searchView.onActionViewExpanded();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                search(s);
                if (searchView != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                        searchView.clearFocus();
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}