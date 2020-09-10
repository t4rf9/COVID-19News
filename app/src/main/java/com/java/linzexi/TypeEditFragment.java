package com.java.linzexi;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

    private static final String NEWS = "NEWS";
    private static final String PAPER = "PAPER";

    private boolean news = true;
    private boolean paper = true;

    private boolean news_origin = true;
    private boolean paper_origin = true;

    public TypeEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param news  whether tab news is enabled.
     * @param paper whether tab paper is enabled.
     * @return A new instance of fragment TypeSelectFragment.
     */
    public static TypeEditFragment newInstance(boolean news, boolean paper) {
        TypeEditFragment fragment = new TypeEditFragment();
        Bundle args = new Bundle();
        args.putBoolean(NEWS, news);
        args.putBoolean(PAPER, paper);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            news = getArguments().getBoolean(NEWS);
            paper = getArguments().getBoolean(PAPER);

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
        final Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        final Animation still = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
        shake.setRepeatMode(Animation.REVERSE);
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

                if (news != news_origin) {
                    newsTypesFragment.setNews(news);
                    if (news) {
                        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_news_type_news), 0);
                    } else {
                        tabLayout.removeTabAt(0);
                    }
                }
                if (paper != paper_origin) {
                    newsTypesFragment.setPaper(paper);
                    if (paper) {
                        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_news_type_paper), (news ? 1 : 0));
                    } else {
                        tabLayout.removeTabAt(news ? 1 : 0);
                    }
                }
            }
        });

        final Button button_news = view.findViewById(R.id.button_news_type_news);
        if(news)
            button_news.setAnimation(shake);
        button_news.setBackgroundColor(requireActivity().getColor(news ? R.color.colorTypeSelected : R.color.colorTypeUnselected));
        button_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (news) {
                    view.setBackgroundColor(requireActivity().getColor(R.color.colorTypeUnselected));
                    button_news.setAnimation(still);
                    news = false;
                } else {
                    view.setBackgroundColor(requireActivity().getColor(R.color.colorTypeSelected));
                    button_news.setAnimation(shake);
                    news = true;
                }
            }
        });

        final Button button_paper = view.findViewById(R.id.button_news_type_paper);
        if(paper)
            button_paper.setAnimation(shake);
        button_paper.setBackgroundColor(requireActivity().getColor(paper ? R.color.colorTypeSelected : R.color.colorTypeUnselected));
        button_paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (paper) {
                    view.setBackgroundColor(requireActivity().getColor(R.color.colorTypeUnselected));
                    button_paper.setAnimation(still);
                    paper = false;
                } else {
                    view.setBackgroundColor(requireActivity().getColor(R.color.colorTypeSelected));
                    button_paper.setAnimation(shake);
                    paper = true;
                }
            }
        });
    }


}