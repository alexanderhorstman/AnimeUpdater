package com.example.alexander.animeupdatefreak.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.example.alexander.animeupdatefreak.Fragments.AnimeFinishedListFragment;
import com.example.alexander.animeupdatefreak.Fragments.AnimeWatchLaterListFragment;
import com.example.alexander.animeupdatefreak.Fragments.AnimeWatchingListFragment;

public class AnimePagerAdapter extends FragmentPagerAdapter {

    private AnimeWatchingListFragment watching;
    private AnimeWatchLaterListFragment watchLater;
    private AnimeFinishedListFragment finished;

    public AnimePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            watching = new AnimeWatchingListFragment();
            return watching;
        }
        else if(position == 1) {
            watchLater = new AnimeWatchLaterListFragment();
            return watchLater;
        }
        else if(position == 2) {
            finished = new AnimeFinishedListFragment();
            return finished;
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Watching";
            case 1:
                return "Watch Later";
            case 2:
                return "Finished";
        }
        return null;
    }
}
