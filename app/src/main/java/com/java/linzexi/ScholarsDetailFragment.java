package com.java.linzexi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;


public class ScholarsDetailFragment extends Fragment {
    // the fragment initialization parameters
    private static final String NAME = "name";
    private static final String NAME_ZH = "name_zh";
    private static final String INTRODUCTION = "introduction";
    private static final String AVATAR = "avatar";
    private static final String POSITION = "position";

    private String name;
    private String name_zh;
    private String introduction;
    private String avatar;
    private String position;


    public ScholarsDetailFragment() {
        // Required empty public constructor
    }

    public static ScholarsDetailFragment newInstance(String name, String name_zh, String introduction, String avatar, String position) {
        ScholarsDetailFragment fragment = new ScholarsDetailFragment();
        Bundle args = new Bundle();
        args.putString(NAME, name);
        args.putString(NAME_ZH, name_zh);
        args.putString(INTRODUCTION, introduction);
        args.putString(AVATAR, avatar);
        args.putString(POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(NAME);
            name_zh = getArguments().getString(NAME_ZH);
            introduction = getArguments().getString(INTRODUCTION);
            avatar = getArguments().getString(AVATAR);
            position = getArguments().getString(POSITION);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scholars_detail, container, false);
        if (name_zh == null)
            name_zh = "";
        if (!name_zh.equals(""))
            ((TextView) view.findViewById(R.id.detail_name)).setText(name_zh);
        else
            ((TextView) view.findViewById(R.id.detail_name)).setText(name);
        ((TextView) view.findViewById(R.id.detail_position)).setText(position);
        ImageView imageView = view.findViewById(R.id.detail_photo);
        Glide.with(container.getContext()).load(avatar).into(imageView);

        TextView textView_content = view.findViewById(R.id.detail_introduction);
        textView_content.setText(introduction);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
