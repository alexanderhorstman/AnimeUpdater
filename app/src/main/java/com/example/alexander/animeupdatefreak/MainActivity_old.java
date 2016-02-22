package com.example.alexander.animeupdatefreak;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.alexander.animeupdatefreak.Activities.SearchActivity;
import com.example.alexander.animeupdatefreak.Data.AnimeShow;
import com.example.alexander.animeupdatefreak.Fragments.AnimeWatchingListFragment;
import com.example.alexander.animeupdatefreak.Managers.AnimeListManager;
import com.example.alexander.animeupdatefreak.adapters.AnimePagerAdapter;

import java.util.ArrayList;

public class MainActivity_old extends AppCompatActivity {

    private FragmentPagerAdapter fragmentPagerAdapter;
    private ViewPager viewPager;
    private AnimeListManager manager = AnimeListManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_old);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.main_tabs);

        viewPager = (ViewPager) findViewById(R.id.pager);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        fragmentPagerAdapter = new AnimePagerAdapter(getSupportFragmentManager());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.pager, fragmentPagerAdapter.getItem(0));

        //add swipe listener
        //viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        /*
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                if(position == 0) {
                    ft.replace(R.id.pager, fragmentPagerAdapter.getItem(0));
                    ((AnimeWatchingListFragment)(fragmentPagerAdapter.getItem(0))).updateAdapter(getApplicationContext());
                }
                else if(position == 1) {
                    ft.replace(R.id.pager, fragmentPagerAdapter.getItem(1));
                    ((AnimeWatchLaterListFragment)(fragmentPagerAdapter.getItem(1))).updateAdapter(getApplicationContext());
                }
                else if(position == 2) {
                    ft.replace(R.id.pager, fragmentPagerAdapter.getItem(2));
                    ((AnimeFinishedListFragment)(fragmentPagerAdapter.getItem(2))).updateAdapter(getApplicationContext());
                }
                ft.commit();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        */
        /*
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                if(tab.getPosition() == 0) {
                    ft.replace(R.id.pager, fragmentPagerAdapter.getItem(0));
                    ((AnimeWatchingListFragment)(fragmentPagerAdapter.getItem(0))).updateAdapter(getApplicationContext());
                }
                else if(tab.getPosition() == 1) {
                    ft.replace(R.id.pager, fragmentPagerAdapter.getItem(1));
                    ((AnimeWatchLaterListFragment)(fragmentPagerAdapter.getItem(1))).updateAdapter(getApplicationContext());
                }
                else if(tab.getPosition() == 2) {
                    ft.replace(R.id.pager, fragmentPagerAdapter.getItem(2));
                    ((AnimeFinishedListFragment)(fragmentPagerAdapter.getItem(2))).updateAdapter(getApplicationContext());
                }
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                if(tab.getPosition() == 0) {
                    //ft.detach(fragmentPagerAdapter.getItem(0));
                }
                else if(tab.getPosition() == 1) {
                    //ft.detach(fragmentPagerAdapter.getItem(1));
                }
                else if(tab.getPosition() == 2) {
                    //ft.detach(fragmentPagerAdapter.getItem(2));
                }
                //ft.commit();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                if(tab.getPosition() == 0) {
                    //ft.replace(R.id.pager, fragmentPagerAdapter.getItem(0));
                }
                else if(tab.getPosition() == 1) {
                    //ft.replace(R.id.pager, fragmentPagerAdapter.getItem(1));
                }
                else if(tab.getPosition() == 2) {
                    //ft.replace(R.id.pager, fragmentPagerAdapter.getItem(2));
                }
                //ft.commit();
            }
        });
        */


        // Set up the ViewPager with the sections adapter.

        viewPager.setAdapter(fragmentPagerAdapter);


        //add tabs to activity

        tabLayout.setupWithViewPager(viewPager);

        //load lists from file


    }
/*
    @Override
    protected void onResume() {
        super.onResume();
        if(executeOnResume) {
            ((AnimeWatchingListFragment)fragmentPagerAdapter.getItem(0)).updateAdapter(getBaseContext());
        }
        else {
            executeOnResume = true;
        }
        //((AnimePagerAdapter) fragmentPagerAdapter).updateFragments();
    }
*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ((AnimeWatchingListFragment) fragmentPagerAdapter.getItem(0)).updateAdapter(getApplicationContext());
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
        if (id == R.id.updates_menu) {
            Intent intent = new Intent(this, UpdateActivity.class);
            ArrayList<String> urls = new ArrayList<>();
            for(AnimeShow show: manager.getWatchingList()) {
                urls.add(show.getUrl());
            }
            intent.putExtra("watchingList", urls);
            startActivity(intent);
            return true;
        }
        else if(id == R.id.search_menu) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivityForResult(intent, 1);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
