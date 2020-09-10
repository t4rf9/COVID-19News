package com.java.linzexi;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;
import com.java.linzexi.database.NewsDao;
import com.java.linzexi.database.NewsEntity;

import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsTypesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsTypesFragment extends Fragment {
    private boolean news = true;
    private boolean paper = true;

    public NewsTypesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NewsTypesFragment.
     */
    public static NewsTypesFragment newInstance() {
        return new NewsTypesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void search(String s){
        //List<NewsEntity> li = searchNewsTitle(s);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_news_types, container, false);
        final SearchView searchView = view.findViewById(R.id.search_news);
        searchView.setSubmitButtonEnabled(true);
        //searchView.onActionViewExpanded();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                search(s);
                if(searchView != null){
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(imm != null){
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TabLayout tabLayout = view.findViewById(R.id.tabLayout_news_types);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_news_type_news));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_news_type_paper));

        FragmentManager manager = getChildFragmentManager();
        final NewsItemXRecyclerViewFragment newsFragment = new NewsItemXRecyclerViewFragment();
        manager.beginTransaction().add(R.id.fl_container, newsFragment).commitAllowingStateLoss();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int type;
                String text = Objects.requireNonNull(tab.getText()).toString();
                if (text.equals(getString(R.string.tab_news_type_news))) {
                    type = 0;
                } else {
                    type = 1;
                }
                newsFragment.changeShow(type);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                onTabSelected(tab);
            }
        });

        Button button_edit = view.findViewById(R.id.button_edit_news_types);
        button_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = fragmentManager.findFragmentById(R.id.constraintView_news_types);
                if (fragment == null || fragment.isRemoving()) {
                    fragmentTransaction.replace(R.id.constraintView_news_types, TypeEditFragment.newInstance(news, paper));
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                } else {
                    fragment.requireView().findViewById(R.id.button_edit_completed).callOnClick();
                }
            }
        });
    }

    public void setNews(final boolean b) {
        news = b;
    }

    public void setPaper(final boolean b) {
        paper = b;
    }
}