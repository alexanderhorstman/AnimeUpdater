package com.example.alexander.animeupdatefreak.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alexander.animeupdatefreak.Data.AnimeShow;
import com.example.alexander.animeupdatefreak.Managers.AnimeListManager;
import com.example.alexander.animeupdatefreak.R;
import com.example.alexander.animeupdatefreak.adapters.SearchResultsAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.ArrayList;


public class SearchActivity extends Activity {

    private EditText searchText;
    private ListView searchResults;
    final ArrayList<AnimeShow> animes = new ArrayList<>();
    final ArrayList<String> animeNames = new ArrayList<>();
    final String baseUrl = "http://www.animefreak.tv";
    String animeName;
    Context context;
    boolean dataChanged = false;
    final ArrayList<String> fullEpisodeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity_layout);
        searchText = (EditText) findViewById(R.id.search_bar);
        searchResults = (ListView) findViewById(R.id.search_results);
        context = this;
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchForAnime(null);
                    return true;
                }
                return false;
            }
        });
    }

    public void searchForAnime(View view) {
        view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        animeName = searchText.getText().toString().trim();
        new Search().execute();

    }

    private class Search extends AsyncTask<Void, Void, Void> {

        private ProgressDialog dialog;

        protected Search() {
            dialog = new ProgressDialog(context);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setTitle("Searching...");
            dialog.setMessage("Finding anime with name: " + animeName);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            animes.clear();
            animeNames.clear();
            SearchResultsAdapter adapter = new SearchResultsAdapter(context, animeNames, AnimeListManager.getInstance(), animes);
            searchResults.setAdapter(adapter);
            searchResults.refreshDrawableState();
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                //get anime names that contain the search word
                Document doc = Jsoup.connect(baseUrl).get();
                //get anime list in sidebar
                Elements sidebarAnimes = doc.select("#page > div.wrapper > div.lsidebar > div:nth-child(2) > div");
                //get links from sidebar list
                Elements links = sidebarAnimes.select("a");
                for(Element element: links) {
                    String name = element.select("a").text();
                    if(name.contains(animeName)) {
                        //construct url for show
                        String url = baseUrl + element.attr("href");
                        //create show
                        AnimeShow show = new AnimeShow(name, url);

                        Document doc2 = Jsoup.connect(url).get();
                        //get genre
                        Elements genre = doc2.select("#primary > div > div.node > div.content > blockquote:nth-child(5) > p ");
                        String genreText = genre.text();
                        String[] tokens = genreText.split("\\s");
                        genreText = "";
                        int i = 0;
                        while(i < tokens.length && !tokens[i].equals("Episodes:")) {
                            genreText += tokens[i] + " ";
                            i++;
                        }
                        //get description
                        Elements description = doc2.select("#primary > div > div.node > div.content > blockquote:nth-child(10) > p");
                        //get image for show
                        Elements img = doc2.select("img");
                        // Locate the src attribute
                        String imgSrc = img.attr("src");
                        // Download image from URL
                        InputStream input = new java.net.URL(imgSrc).openStream();
                        // Decode Bitmap -------------------------------------------------Could definitely do this better
                        Bitmap bitmap = BitmapFactory.decodeStream(input);
                        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                        //get list of episodes
                        fullEpisodeList.clear();
                        Elements episodes = doc2.select("#page > div.wrapper > div.lsidebar > div:nth-child(2) > div");
                        Elements epLinks = episodes.select("a");
                        for(Element ep : epLinks) {
                            String epName = ep.select("a").text();
                            fullEpisodeList.add(epName);
                        }
                        //set show attributes
                        show.setImage(outStream.toByteArray());
                        show.setGenre(genreText);
                        show.setDescription(description.text());
                        show.setAvailableList(fullEpisodeList);
                        //add shows to show list and name list
                        animes.add(show);
                        animeNames.add(name);
                    }
                }
            }
            catch(SocketTimeoutException s) {

            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            dialog.dismiss();
            // Set title into TextView
            SearchResultsAdapter adapter = new SearchResultsAdapter(context, animeNames, AnimeListManager.getInstance(), animes);
            searchResults.setAdapter(adapter);
            searchResults.refreshDrawableState();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        if(dataChanged) {
            setResult(RESULT_OK, intent);
        }
        else {
            setResult(RESULT_CANCELED, intent);
        }
        finish();
    }
}
