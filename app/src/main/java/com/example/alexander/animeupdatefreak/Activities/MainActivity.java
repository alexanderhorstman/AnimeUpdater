package com.example.alexander.animeupdatefreak.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.alexander.animeupdatefreak.R;
import com.example.alexander.animeupdatefreak.adapters.AnimePagerAdapter;

public class MainActivity extends AppCompatActivity {

    private ViewHolder holder;
    private ArrayAdapter<String> drawerAdapter;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        holder = new ViewHolder();
        setupDrawer();
        setAppBarColor();
        setupViewPager();

        //set default list selection
        selectList(findViewById(R.id.watching_button));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        holder.drawerLayout.closeDrawers();
        AnimePagerAdapter animePagerAdapter = new AnimePagerAdapter(getSupportFragmentManager());
        holder.pager.setAdapter(animePagerAdapter);
        selectList(findViewById(R.id.watching_button));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return true;
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void setupDrawer() {
        String[] options = { "Search", "Check For Updates" };
        drawerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, options);
        holder.drawerList.setAdapter(drawerAdapter);

        holder.drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int item = view.getId();
                if (position == 0) {
                    //start search activity
                    Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                    startActivityForResult(intent, 1);
                } else if (position == 1) {
                    //start asynch task
                    //close drawer
                    //start progressDialog
                    //show updated shows
                }
            }
        });
        setSupportActionBar(holder.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(this, holder.drawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        drawerToggle.setDrawerIndicatorEnabled(true);
        holder.drawerLayout.setDrawerListener(drawerToggle);
    }

    public void selectList(View view) {
        int id = view.getId();
        int primary = ContextCompat.getColor(this, R.color.colorPrimary);
        int white = ContextCompat.getColor(this, android.R.color.white);
        if(id == R.id.watching_button) {
            //set correct tab
            holder.pager.setCurrentItem(0);
            //make sure other two tabs are default colors
            holder.watchLaterButton.setBackgroundColor(primary);
            holder.watchLaterButton.setTextColor(white);
            holder.finishedButton.setBackgroundColor(primary);
            holder.finishedButton.setTextColor(white);
            //change button colors to show that the list is selected
            holder.watchingButton.setBackgroundColor(white);
            holder.watchingButton.setTextColor(primary);
        }
        else if(id == R.id.watch_later_button) {
            //set correct tab
            holder.pager.setCurrentItem(1);
            //change button colors to show that the list is selected
            //make sure other two tabs are default colors
            holder.watchingButton.setBackgroundColor(primary);
            holder.watchingButton.setTextColor(white);
            holder.finishedButton.setBackgroundColor(primary);
            holder.finishedButton.setTextColor(white);
            //change button colors to show that the list is selected
            holder.watchLaterButton.setBackgroundColor(white);
            holder.watchLaterButton.setTextColor(primary);
        }
        else if(id == R.id.finished_button) {
            //set correct tab
            holder.pager.setCurrentItem(2);
            //change button colors to show that the list is selected
            //make sure other two tabs are default colors
            holder.watchLaterButton.setBackgroundColor(primary);
            holder.watchLaterButton.setTextColor(white);
            holder.watchingButton.setBackgroundColor(primary);
            holder.watchingButton.setTextColor(white);
            //change button colors to show that the list is selected
            holder.finishedButton.setBackgroundColor(white);
            holder.finishedButton.setTextColor(primary);
        }
    }

    private void setAppBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
    }

    private void setupViewPager() {
        AnimePagerAdapter animePagerAdapter = new AnimePagerAdapter(getSupportFragmentManager());
        holder.pager.setAdapter(animePagerAdapter);
        holder.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                if(position == 0) {
                    selectList(findViewById(R.id.watching_button));
                }
                else if(position ==1) {
                    selectList(findViewById(R.id.watch_later_button));
                }
                else if(position == 2) {
                    selectList(findViewById(R.id.finished_button));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }


    public class ViewHolder {
        protected Button watchingButton;
        protected Button watchLaterButton;
        protected Button finishedButton;
        protected ViewPager pager;
        protected Toolbar toolbar;
        protected ListView drawerList;
        protected DrawerLayout drawerLayout;

        public ViewHolder() {
            watchingButton = (Button)findViewById(R.id.watching_button);
            watchLaterButton = (Button)findViewById(R.id.watch_later_button);
            finishedButton = (Button)findViewById(R.id.finished_button);
            pager = (ViewPager)findViewById(R.id.pager);
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            drawerList = (ListView) findViewById(R.id.navList);
            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        }
    }
}
