package com.java.linzexi;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
            private Fragment[] fragments = new Fragment[4];

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                int pos = tab.getPosition();
                if (fragments[pos] == null) {
                    switch (pos) {
                        case 1:
                            fragments[pos] = EpiDataFragment.newInstance();
                            break;
                        case 2:
                            fragments[pos] = EpiGraphFragment.newInstance();
                            break;
                        case 3:
                            fragments[pos] = ScholarsFragment.newInstance();
                            break;
                        default:
                            fragments[pos] = NewsTypesFragment.newInstance();
                            break;
                    }
                }
                fragmentTransaction.replace(R.id.host_fragment_container, fragments[pos]);
                fragmentTransaction.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (fragments[tab.getPosition()] == null)
                    onTabSelected(tab);
            }
        });

        /* make sure onTabSelected is called on start */
        tabLayout.selectTab(tabLayout.getTabAt(0));
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


        return super.onOptionsItemSelected(item);
    }


}