package com.java.linzexi;

import android.annotation.SuppressLint;
import android.app.Activity;
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

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TypeEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TypeEditFragment extends Fragment {

    private static final String ALL = "ALL";
    private static final String NEWS = "NEWS";
    private static final String PAPER = "PAPER";

    private boolean all = true;
    private boolean news = true;
    private boolean paper = true;

    private boolean all_origin = true;
    private boolean news_origin = true;
    private boolean paper_origin = true;

    public TypeEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param all   whether tab all is enabled.
     * @param news  whether tab news is enabled.
     * @param paper whether tab paper is enabled.
     * @return A new instance of fragment TypeSelectFragment.
     */
    public static TypeEditFragment newInstance(boolean all, boolean news, boolean paper) {
        TypeEditFragment fragment = new TypeEditFragment();
        Bundle args = new Bundle();
        args.putBoolean(ALL, all);
        args.putBoolean(NEWS, news);
        args.putBoolean(PAPER, paper);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            all = getArguments().getBoolean(ALL);
            news = getArguments().getBoolean(NEWS);
            paper = getArguments().getBoolean(PAPER);

            all_origin = all;
            news_origin = news;
            paper_origin = paper;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_type_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.button_edit_completed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(Objects.requireNonNull(fragmentManager.findFragmentById(R.id.constraintView_news_types)));
                fragmentTransaction.commit();

                Activity mainActivity = requireActivity();
                TabLayout tabLayout = mainActivity.findViewById(R.id.tabLayout_news_types);
                NewsTypesFragment newsTypesFragment = (NewsTypesFragment) fragmentManager.findFragmentById(R.id.host_fragment_container);
                if (all != all_origin) {
                    newsTypesFragment.setAll(all);
                    if (all) {
                        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_news_type_all), 0);
                    } else {
                        tabLayout.removeTabAt(0);
                    }
                }
                if (news != news_origin) {
                    newsTypesFragment.setNews(news);
                    if (news) {
                        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_news_type_news), all ? 1 : 0);
                    } else {
                        tabLayout.removeTabAt(all ? 1 : 0);
                    }
                }
                if (paper != paper_origin) {
                    newsTypesFragment.setPaper(paper);
                    if (paper) {
                        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_news_type_paper), (all ? 1 : 0) + (news ? 1 : 0));
                    } else {
                        tabLayout.removeTabAt((all ? 1 : 0) + (news ? 1 : 0));
                    }
                }
            }
        });

        Button button_all = view.findViewById(R.id.button_news_type_all);
        button_all.setBackgroundColor(requireActivity().getColor(all ? R.color.colorTypeSelected : R.color.colorTypeUnselected));
        button_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (all) {
                    view.setBackgroundColor(requireActivity().getColor(R.color.colorTypeUnselected));
                    all = false;
                } else {
                    view.setBackgroundColor(requireActivity().getColor(R.color.colorTypeSelected));
                    all = true;
                }
            }
        });

        Button button_news = view.findViewById(R.id.button_news_type_news);
        button_news.setBackgroundColor(requireActivity().getColor(news ? R.color.colorTypeSelected : R.color.colorTypeUnselected));
        button_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (news) {
                    view.setBackgroundColor(requireActivity().getColor(R.color.colorTypeUnselected));
                    news = false;
                } else {
                    view.setBackgroundColor(requireActivity().getColor(R.color.colorTypeSelected));
                    news = true;
                }
            }
        });

        Button button_paper = view.findViewById(R.id.button_news_type_paper);
        button_paper.setBackgroundColor(requireActivity().getColor(paper ? R.color.colorTypeSelected : R.color.colorTypeUnselected));
        button_paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (paper) {
                    view.setBackgroundColor(requireActivity().getColor(R.color.colorTypeUnselected));
                    paper = false;
                } else {
                    view.setBackgroundColor(requireActivity().getColor(R.color.colorTypeSelected));
                    paper = true;
                }
            }
        });
    }


}