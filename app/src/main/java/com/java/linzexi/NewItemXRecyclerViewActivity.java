package com.java.linzexi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class NewItemXRecyclerViewActivity extends AppCompatActivity {

    private RecyclerView ReView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item_x_recycler_view);
        ReView = (RecyclerView) findViewById(R.id.rv_main);
        ReView.setLayoutManager(new LinearLayoutManager(NewItemXRecyclerViewActivity.this));
        ReView.setAdapter(new NewsItemXRecyclerViewAdapter(NewItemXRecyclerViewActivity.this));
    }
}