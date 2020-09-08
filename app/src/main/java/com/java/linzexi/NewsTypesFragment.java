package com.java.linzexi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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

    public NewsTypesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NewsTypesFragment.
     */
    public static NewsTypesFragment newInstance(){
        return new NewsTypesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_news_type_news));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_news_type_paper));
        FragmentManager manager = getChildFragmentManager();
        final NewsItemXRecyclerViewFragment newsFragment = new NewsItemXRecyclerViewFragment();
        manager.beginTransaction().add(R.id.fl_container, newsFragment).commitAllowingStateLoss();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                newsFragment.changeShow(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                onTabSelected(tab);
            }
        });
    }
}