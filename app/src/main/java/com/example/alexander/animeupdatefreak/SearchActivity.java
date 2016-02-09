package com.example.alexander.animeupdatefreak;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alexander.animeupdatefreak.adapters.AnimeListAdapter;
import com.example.alexander.animeupdatefreak.adapters.SearchResultsAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class SearchActivity extends Activity {

    private EditText searchText;
    private ListView searchResults;
    final ArrayList<AnimeShow> animes = new ArrayList<>();
    final ArrayList<String> animeNames = new ArrayList<>();
    final String baseUrl = "http://www.animefreak.tv";
    String animeName;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity_layout);
        searchText = (EditText) findViewById(R.id.search_bar);
        searchResults = (ListView) findViewById(R.id.search_results);
        animeName = searchText.getText().toString();
        context = this;
    }

    public void searchForAnime(View view) {
        view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        new Title().execute();
    }

    private class Title extends AsyncTask<Void, Void, Void> {
        String title;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            animeName = searchText.getText().toString();
            animes.clear();
            animeNames.clear();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect(baseUrl).get();
                Elements sidebarAnimes = doc.select("#page > div.wrapper > div.lsidebar > div:nth-child(2) > div");
                Elements links = sidebarAnimes.select("a");
                for(Element element: links) {
                    String name = element.select("a").text();
                    if(name.contains(animeName)) {
                        System.out.println(element.attr("href"));
                        String url = baseUrl + element.attr("href");

                        AnimeShow show = new AnimeShow(name, AnimeShow.LIST_ID_INITIAL, R.drawable.image_not_found);
                        show.setUrl(url);
                        Document doc2 = Jsoup.connect(url).get();
                        Elements img = doc2.select("img");
                        // Locate the src attribute
                        String imgSrc = img.attr("src");
                        // Download image from URL
                        InputStream input = new java.net.URL(imgSrc).openStream();
                        // Decode Bitmap
                        Bitmap bitmap = BitmapFactory.decodeStream(input);
                        show.setImage(bitmap);
                        animes.add(show);
                        animeNames.add(name);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set title into TextView
            SearchResultsAdapter adapter = new SearchResultsAdapter(context, animeNames, AnimeListManager.getInstance(), animes);
            searchResults.setAdapter(adapter);
            searchResults.refreshDrawableState();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
