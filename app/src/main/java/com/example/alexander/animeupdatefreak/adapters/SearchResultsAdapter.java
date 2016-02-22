package com.example.alexander.animeupdatefreak.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alexander.animeupdatefreak.Managers.AnimeListManager;
import com.example.alexander.animeupdatefreak.Data.AnimeShow;
import com.example.alexander.animeupdatefreak.R;

import java.util.ArrayList;


public class SearchResultsAdapter extends ArrayAdapter<String>{

    AnimeListManager manager;
    ArrayList<AnimeShow> results;
    ArrayList<String> names;
    Context context;

    public SearchResultsAdapter(Context context, ArrayList names, AnimeListManager manager, ArrayList results) {
        super(context, R.layout.main_list_layout, names);
        this.manager = manager;
        this.results = results;
        this.names = names;
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
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
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
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialogBox = new AlertDialog.Builder(context);
                    dialogBox.setTitle("Move anime to which list?");
                    dialogBox.setSingleChoiceItems(listChoices, -1, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int index) {
                            switch (index) {
                                case 0:
                                    manager.getWatchingList().add(results.get(position));
                                    results.remove(position);
                                    names.remove(position);
                                    break;
                                case 1:
                                    manager.getWatchLaterList().add(results.get(position));
                                    results.remove(position);
                                    names.remove(position);
                                    break;
                                case 2:
                                    manager.getFinishedList().add(results.get(position));
                                    results.remove(position);
                                    names.remove(position);
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
    }
}
