package com.example.alexander.animeupdatefreak.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alexander.animeupdatefreak.AnimeListManager;
import com.example.alexander.animeupdatefreak.AnimeShow;
import com.example.alexander.animeupdatefreak.R;
import com.example.alexander.animeupdatefreak.SearchActivity;

import java.util.ArrayList;


public class SearchResultsAdapter extends ArrayAdapter<String>{

    AnimeListManager manager;
    ArrayList<AnimeShow> results;
    Context context;

    public SearchResultsAdapter(Context context, ArrayList names, AnimeListManager manager, ArrayList results) {
        super(context, R.layout.main_list_layout, names);
        this.manager = manager;
        this.results = results;
        this.context = context;
    }

    public View getView(final int position, final View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder = new ViewHolder();
        if(position < results.size() && position >= 0) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.main_list_layout, null);
                viewHolder.animeImage = (ImageView) view.findViewById(R.id.anime_image);
                viewHolder.animeName = (TextView) view.findViewById(R.id.anime_name);
                viewHolder.listIdImage = (ImageView) view.findViewById(R.id.list_id_image);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.listIdImage.setImageResource(results.get(position).getListIdImageId());
            viewHolder.animeName.setText(results.get(position).getAnimeName());
            Bitmap bitmap = results.get(position).getImage();
            if(bitmap == null) {
                viewHolder.animeImage.setImageResource(R.drawable.image_not_found);
            }
            else {
                viewHolder.animeImage.setImageBitmap(bitmap);
            }

            //set possible new lists to move anime to
            final String[] listChoices = {"Watching", "Watch Later", "Finished"};

            //set onClick for listIdImage
            viewHolder.listIdImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialogBox = new AlertDialog.Builder(context);
                    dialogBox.setTitle("Move anime to which list?");
                    dialogBox.setSingleChoiceItems(listChoices, -1, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int index) {
                            switch (index) {
                                case 0:
                                    results.get(position).setListId(AnimeShow.LIST_ID_WATCHING);
                                    manager.getWatchingList().add(results.get(position));
                                    results.remove(position);
                                    break;
                                case 1:
                                    results.get(position).setListId(AnimeShow.LIST_ID_WATCH_LATER);
                                    manager.getWatchLaterList().add(results.get(position));
                                    results.remove(position);
                                    break;
                                case 2:
                                    results.get(position).setListId(AnimeShow.LIST_ID_FINISHED);
                                    manager.getFinishedList().add(results.get(position));
                                    results.remove(position);
                                    break;
                            }
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    });
                    dialogBox.create();
                    dialogBox.show();
                }
            });
        }
        return view;
    }

    private class ViewHolder {
        private ImageView animeImage;
        private TextView animeName;
        private ImageView listIdImage;
    }
}
