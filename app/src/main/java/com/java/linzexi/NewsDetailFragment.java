package com.java.linzexi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.java.linzexi.database.NewsEntity;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsDetailFragment extends Fragment {

    // the fragment initialization parameters
    private static final String TIME = "time";
    private static final String SOURCE = "source";
    private static final String TITLE = "title";
    private static final String CONTENT = "content";

    private String time;
    private String source;
    private String title;
    private String content;


    public NewsDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param newsEntity the news entity to display.
     * @return A new instance of fragment NewsDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsDetailFragment newInstance(NewsEntity newsEntity) {
        NewsDetailFragment fragment = new NewsDetailFragment();
        Bundle args = new Bundle();
        args.putString(TIME, newsEntity.getTime());
        args.putString(SOURCE, newsEntity.getSource());
        args.putString(TITLE, newsEntity.getTitle());
        args.putString(CONTENT, newsEntity.getContent());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            time = getArguments().getString(TIME);
            source = getArguments().getString(SOURCE);
            title = getArguments().getString(TITLE);
            content = getArguments().getString(CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_detail, container, false);
        ((TextView) view.findViewById(R.id.textView_title)).setText(title);
        ((TextView) view.findViewById(R.id.textView_content)).setText(content);
        ((TextView) view.findViewById(R.id.textView_source)).setText(source);
        ((TextView) view.findViewById(R.id.textView_time)).setText(time);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        requireActivity().findViewById(R.id.tabLayout).setVisibility(View.VISIBLE);
    }
}