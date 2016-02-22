package com.example.alexander.animeupdatefreak.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alexander.animeupdatefreak.Data.AnimeShow;
import com.example.alexander.animeupdatefreak.R;
import com.example.alexander.animeupdatefreak.adapters.UpdateListAdapter;

public class ViewAnime extends AppCompatActivity {

    ViewHolder holder;
    AnimeShow show;
    String showInList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_anime_layout);

        Intent intent = getIntent();
        show = (AnimeShow) intent.getSerializableExtra("show");
        showInList = intent.getStringExtra("list");

        holder = new ViewHolder();
        initializeViews();

        Toolbar toolbar;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void initializeViews() {
        holder.animeName.setText(show.getAnimeName());
        holder.animeImage.setImageBitmap(show.getImage());
        holder.animeGenre.setText(show.getGenre());
        UpdateListAdapter adapter = new UpdateListAdapter(ViewAnime.this, show.getAvailableList(), show.getWatchedList());
        holder.availableList.setAdapter(adapter);
    }

    public void viewAnimeDescription(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Description");
        TextView text = new TextView(getBaseContext());
        text.setText(show.getDescription());
        text.setTextColor(Color.BLACK);
        text.setPadding(60, 10, 60, 10);
        dialog.setView(text);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.create();
        dialog.show();
    }

    private class ViewHolder {
        private ImageView animeImage;
        private TextView animeName;
        private TextView animeGenre;
        //private TextView animeDescription;
        private ListView availableList;
        private ListView watchedList;

        public ViewHolder() {
            animeImage = (ImageView) findViewById(R.id.anime_info_image);
            animeName = (TextView) findViewById(R.id.anime_info_name);
            animeGenre = (TextView) findViewById(R.id.anime_info_genre);
            //animeDescription = (TextView) findViewById(R.id.anime_info_description);
            availableList = (ListView) findViewById(R.id.available_episodes_list);
            //watchedList = (ListView) findViewById(R.id.watched_episodes_list);
        }
    }
}
