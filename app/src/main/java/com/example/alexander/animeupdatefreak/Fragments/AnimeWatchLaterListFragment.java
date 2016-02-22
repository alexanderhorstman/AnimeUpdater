package com.example.alexander.animeupdatefreak.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.alexander.animeupdatefreak.Managers.AnimeListManager;
import com.example.alexander.animeupdatefreak.R;
import com.example.alexander.animeupdatefreak.adapters.AnimeListAdapter;

import java.util.ArrayList;

/**
 * Created by Alex Ander on 2/7/2016.
 */
public class AnimeWatchLaterListFragment extends ListFragment implements AdapterView.OnItemClickListener{

    AnimeListManager manager = AnimeListManager.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int listSize;
        listSize = manager.getWatchLaterList().size();
        ArrayList<String> animeNames = new ArrayList<>();
        for(int i = 0; i < listSize; i++) {
            animeNames.add(manager.getWatchLaterList().get(i).getAnimeName());
        }
        AnimeListAdapter adapter = new AnimeListAdapter(getContext(), animeNames, manager.getWatchLaterList(), "watch later");
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        //launch anime view activity
    }

    public void updateAdapter(Context context) {
        int listSize;
        listSize = manager.getWatchLaterList().size();
        ArrayList<String> animeNames = new ArrayList<>();
        for(int i = 0; i < listSize; i++) {
            animeNames.add(manager.getWatchLaterList().get(i).getAnimeName());
        }
        AnimeListAdapter adapter = new AnimeListAdapter(context, animeNames, manager.getWatchLaterList(), "watch later");
        setListAdapter(adapter);
    }
}
