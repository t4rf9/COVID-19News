package com.java.linzexi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsTypesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsTypesFragment extends Fragment {
    private boolean all = true;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_types, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TabLayout tabLayout = view.findViewById(R.id.tabLayout_news_types);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_news_type_all));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_news_type_news));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_news_type_paper));

        Button button_edit = view.findViewById(R.id.button_edit_news_types);
        button_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = fragmentManager.findFragmentById(R.id.constraintView_news_types);
                if (fragment == null || fragment.isRemoving()) {
                    fragmentTransaction.replace(R.id.constraintView_news_types, TypeEditFragment.newInstance(all, news, paper));
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                } else {
                    fragment.requireView().findViewById(R.id.button_edit_completed).callOnClick();
                }
            }
        });
    }

    public void setAll(final boolean b) {
        all = b;
    }

    public void setNews(final boolean b) {
        news = b;
    }

    public void setPaper(final boolean b) {
        paper = b;
    }
}