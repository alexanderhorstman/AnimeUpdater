package com.example.alexander.animeupdatefreak;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.alexander.animeupdatefreak.adapters.AnimeListAdapter;

import java.util.ArrayList;

/**
 * Created by Alex Ander on 2/7/2016.
 */
public class AnimeWatchingListFragment extends ListFragment implements AdapterView.OnItemClickListener{

    AnimeListManager manager = AnimeListManager.getInstance();
    int listId = AnimeShow.LIST_ID_WATCHING;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        int listSize;
        listSize = manager.getWatchingList().size();
        ArrayList<String> animeNames = new ArrayList<>();
        for(int i = 0; i < listSize; i++) {
            animeNames.add(manager.getWatchingList().get(i).getAnimeName());
        }
        AnimeListAdapter adapter = new AnimeListAdapter(getContext(), animeNames, manager, listId);
        setListAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        //launch anime view activity
    }

    public void updateAdapter() {
        int listSize;
        listSize = manager.getWatchingList().size();
        ArrayList<String> animeNames = new ArrayList<>();
        for(int i = 0; i < listSize; i++) {
            animeNames.add(manager.getWatchingList().get(i).getAnimeName());
        }
        AnimeListAdapter adapter = new AnimeListAdapter(getContext(), animeNames, manager, listId);
        setListAdapter(adapter);
    }
}

