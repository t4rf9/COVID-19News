package com.java.linzexi;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
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
     * @param time The time of the news.
     * @param source The source of the news.
     * @param title The title of the news.
     * @param content The content of the news.
     * @return A new instance of fragment NewsDetailFragment.
     */
    public static NewsDetailFragment newInstance(String time, String source, String title, String content) {
        NewsDetailFragment fragment = new NewsDetailFragment();
        Bundle args = new Bundle();
        args.putString(TIME, time);
        args.putString(SOURCE, source);
        args.putString(TITLE, title);
        args.putString(CONTENT, content);
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
        ((TextView) view.findViewById(R.id.textView_source)).setText(source);
        ((TextView) view.findViewById(R.id.textView_time)).setText(time);

        TextView textView_content = view.findViewById(R.id.textView_content);
        textView_content.setText(content);
        textView_content.setMovementMethod(ScrollingMovementMethod.getInstance());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}