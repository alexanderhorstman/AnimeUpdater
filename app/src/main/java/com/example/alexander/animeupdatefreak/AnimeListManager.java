package com.example.alexander.animeupdatefreak;

import java.util.ArrayList;

public class AnimeListManager {

    private static AnimeListManager manager = null;

    private ArrayList<AnimeShow> watchingList;
    private ArrayList<AnimeShow> watchLaterList;
    private ArrayList<AnimeShow> finishedList;

    private AnimeListManager() {
        loadLists();
        if(watchingList == null) {
            watchingList = new ArrayList<>();
        }
        if(watchLaterList == null) {
            watchLaterList = new ArrayList<>();
        }
        if(finishedList == null) {
            finishedList = new ArrayList<>();
        }
    }

    public static AnimeListManager getInstance() {
        if(manager == null) {
            manager = new AnimeListManager();
        }
        return manager;
    }

    public ArrayList<AnimeShow> getWatchingList() {
        return watchingList;
    }

    public ArrayList<AnimeShow> getWatchLaterList() {
        return watchLaterList;
    }

    public ArrayList<AnimeShow> getFinishedList() {
        return finishedList;
    }

    private void loadLists() {
        //attempt to load the lists from file
    }

    public boolean saveLists() {
        try {
            //attempt to save list to file
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
}
