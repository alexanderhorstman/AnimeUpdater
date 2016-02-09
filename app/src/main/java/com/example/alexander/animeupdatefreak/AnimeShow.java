package com.example.alexander.animeupdatefreak;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import java.io.Serializable;

public class AnimeShow implements Serializable{

    final static public int LIST_ID_WATCHING = 100;
    final static public int LIST_ID_WATCH_LATER = 200;
    final static public int LIST_ID_FINISHED = 300;
    final static public int LIST_ID_INITIAL = 0;

    private String animeName;
    private int listId;
    private int animeImageId;
    private int listIdImageId;
    private String url;
    private Bitmap image;


    public AnimeShow(String animeName, int listId, int animeImageId) {
        this.animeName = animeName;
        this.listId = listId;
        this.animeImageId = animeImageId;
        setListImage(listId);
    }

    public String getAnimeName() {
        return animeName;
    }

    public int getListId() {
        return listId;
    }

    public int getAnimeImageId() {
        return animeImageId;
    }

    public int getListIdImageId() {
        return listIdImageId;
    }

    public String getUrl() {
        return url;
    }

    public void setListId(int id) {
        if(id == LIST_ID_FINISHED) {
            listIdImageId = R.drawable.finished_list_image;
            listId = id;
        }
        else if(id == LIST_ID_WATCH_LATER) {
            listIdImageId = R.drawable.watch_later_list_image;
            listId = id;
        }
        else if(id == LIST_ID_WATCHING) {
            listIdImageId = R.drawable.watching_list_image;
            listId = id;
        }
    }

    private void setListImage(int imageId) {
        if(imageId == LIST_ID_WATCHING) {
            listIdImageId = R.drawable.watching_list_image;
        }
        else if(imageId == LIST_ID_WATCH_LATER) {
            listIdImageId = R.drawable.watch_later_list_image;
        }
        else if(imageId == LIST_ID_FINISHED) {
            listIdImageId = R.drawable.finished_list_image;
        }
        else {
            listIdImageId = R.drawable.initial_list_image;
        }
    }
    
    public void setUrl(String url) {
        this.url = url;
    }

    public void setImage(Bitmap img) {
        image = img;
    }

    public Bitmap getImage() {
        return image;
    }
}
