package com.java.linzexi;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class ClusteringDetailActivity extends AppCompatActivity {
    List<String> events = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clustering_detail);
        Intent intent = getIntent();
        events.addAll(intent.getStringArrayListExtra("events"));

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_clustering_detail, ClusteringDetailFragment.newInstance(events));
        fragmentTransaction.commit();
    }
}
