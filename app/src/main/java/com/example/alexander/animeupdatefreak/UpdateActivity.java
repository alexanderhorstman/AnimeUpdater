package com.example.alexander.animeupdatefreak;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import com.example.alexander.animeupdatefreak.adapters.UpdateListAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class UpdateActivity extends Activity {

    ListView listView;
    ArrayList<String> urls;
    ArrayList<String> episodes;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        urls = (ArrayList) getIntent().getSerializableExtra("watchingList");
        setContentView(R.layout.update_activity_layout);
        context = this;
        listView = (ListView) findViewById(R.id.updates_list);

        new Title().execute();
    }

    private class Title extends AsyncTask<Void, Void, Void> {
        String title;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
               episodes = new ArrayList<>();
                for(String url: urls) {
                        Document doc = Jsoup.connect(url).get();
                        Elements sidebarAnimes = doc.select("#page > div.wrapper > div.lsidebar > div:nth-child(2) > div");
                        Elements links = sidebarAnimes.select("a");
                        for(Element element: links) {
                            String name = element.select("a").text();
                            episodes.add(name);
                        }
                }
            }
            catch (IOException f) {
                f.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set title into TextView
            //UpdateListAdapter adapter = new UpdateListAdapter(context, episodes);
            //listView.setAdapter(adapter);
        }
    }
}
