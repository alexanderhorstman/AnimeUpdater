package com.example.alexander.animeupdatefreak.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alexander.animeupdatefreak.Data.AnimeShow;
import com.example.alexander.animeupdatefreak.R;
import com.example.alexander.animeupdatefreak.Activities.ViewAnime;

import java.util.ArrayList;

public class AnimeListAdapter extends ArrayAdapter<String> {

    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<AnimeShow> currentList = new ArrayList<>();
    private String listId;

    public AnimeListAdapter(Context context, ArrayList names, ArrayList currentList, String listId) {
        super(context, R.layout.main_list_layout, names);
        this.names = names;
        this.currentList = currentList;
        this.listId = listId;
    }

    public View getView(final int position, final View convertView, ViewGroup parent) {
        if(currentList.size() >= 0 && position < currentList.size()) {
            final AnimeShow animeShow = currentList.get(position);
            View view = convertView;
            ViewHolder viewHolder = new ViewHolder();
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.main_list_layout, null);
                viewHolder.animeImage = (ImageView) view.findViewById(R.id.anime_image);
                viewHolder.animeName = (TextView) view.findViewById(R.id.anime_name);
                view.setTag(viewHolder);
            }
            else {
                viewHolder = (ViewHolder) view.getTag();
            }
            //set initial attributes for anime show
            viewHolder.animeName.setText(currentList.get(position).getAnimeName());
            Bitmap bitmap = currentList.get(position).getImage();
            if(bitmap == null) {
                viewHolder.animeImage.setImageResource(R.drawable.image_not_found);
            }
            else {
                viewHolder.animeImage.setImageBitmap(bitmap);
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), ViewAnime.class);
                    intent.putExtra("show", currentList.get(position));
                    intent.putExtra("list", listId);
                    getContext().startActivity(intent);
                }
            });
            return view;
        }
        else {
            return new View(getContext());
        }
    }

    private class ViewHolder {
        protected ImageView animeImage;
        protected TextView animeName;
    }
}

