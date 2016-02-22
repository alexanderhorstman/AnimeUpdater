package com.example.alexander.animeupdatefreak.Data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;
import java.util.ArrayList;

public class AnimeShow implements Serializable{


    private String animeName;
    private String url;
    private String genre;
    private String description;
    private byte[] image;
    private ArrayList<String> availableList;
    private ArrayList<String> watchedList;


    public AnimeShow(String animeName, String url) {
        this.animeName = animeName;
        this.url = url;
    }

    public void addToAvailableList(String ep) {
        availableList.add(ep);
    }

    public void addToWatchedList(String ep) {
        watchedList.add(ep);
    }

    public void moveFromAvailalbeToWatched(int index) {
        String ep = availableList.get(index);
        availableList.remove(index);
        watchedList.add(ep);
    }

    public String getAnimeName() {
        return animeName;
    }

    public String getUrl() {
        return url;
    }

    public String getGenre() {
        return genre;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList getAvailableList() {
        return availableList;
    }

    public ArrayList getWatchedList() {
        return watchedList;
    }

    public void setAvailableList(ArrayList<String> list) {
        availableList = new ArrayList<>();
        for(String string: list) {
            availableList.add(string);
        }
    }

    public void setWatchedList(ArrayList list) {
        watchedList = list;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }

    public void setImage(byte[] img) {
        image = img;
    }

    public Bitmap getImage() {
        if(image != null) {
            return BitmapFactory.decodeByteArray(image, 0, image.length);
        }
        return null;
    }
}
