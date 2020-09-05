package com.java.linzexi;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.Navigation;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            private int[][] actionId = new int[][]{
                    {0, R.id.action_NewsTypesFragment_to_epiDataFragment, R.id.action_NewsTypesFragment_to_epiGraphFragment, R.id.action_NewsTypesFragment_to_scholarsFragment},
                    {R.id.action_epiDataFragment_to_NewsTypesFragment, 0, R.id.action_epiDataFragment_to_epiGraphFragment, R.id.action_epiDataFragment_to_scholarsFragment},
                    {R.id.action_epiGraphFragment_to_NewsTypesFragment, R.id.action_epiGraphFragment_to_epiDataFragment, 0, R.id.action_epiGraphFragment_to_scholarsFragment},
                    {R.id.action_scholarsFragment_to_NewsTypesFragment, R.id.action_scholarsFragment_to_epiDataFragment, R.id.action_scholarsFragment_to_epiGraphFragment, 0}
            };

            private int[] nextAction;

            @SuppressLint("ResourceAsColor")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Navigation.findNavController(findViewById(R.id.nav_host_fragment)).navigate(nextAction[tab.getPosition()]);
            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                nextAction = actionId[tab.getPosition()];
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}