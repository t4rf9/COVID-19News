package com.java.linzexi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsTypesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsTypesFragment extends Fragment {

    // the fragment initialization parameter
    private static final String NEWS_TYPE = "news_type";

    private String mNewsType;

    public NewsTypesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param newsType the type of news.
     * @return A new instance of fragment NewsTypesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsTypesFragment newInstance(String newsType) {
        NewsTypesFragment fragment = new NewsTypesFragment();
        Bundle args = new Bundle();
        args.putString(NEWS_TYPE, newsType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNewsType = getArguments().getString(NEWS_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_types, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_news_type_all));
    }
}