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
import android.widget.Toast;

import com.example.alexander.animeupdatefreak.AnimeListManager;
import com.example.alexander.animeupdatefreak.AnimeShow;
import com.example.alexander.animeupdatefreak.R;

import java.util.ArrayList;

public class AnimeListAdapter extends ArrayAdapter<String> {

    private AnimeListManager manager;
    private ArrayList<AnimeShow> currentList;

    public AnimeListAdapter(Context context, ArrayList names, AnimeListManager animeManager, int listId) {
        super(context, R.layout.main_list_layout, names);
        this.manager = animeManager;
        if(listId == AnimeShow.LIST_ID_WATCHING) {
            currentList = animeManager.getWatchingList();
        }
        else if(listId == AnimeShow.LIST_ID_WATCH_LATER) {
            currentList = animeManager.getWatchLaterList();
        }
        else if(listId == AnimeShow.LIST_ID_FINISHED) {
            currentList = animeManager.getFinishedList();
        }
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
                viewHolder.listIdImage = (ImageView) view.findViewById(R.id.list_id_image);
                view.setTag(viewHolder);
            }
            else {
                viewHolder = (ViewHolder) view.getTag();
            }
            //set initial attributes for anime show
            viewHolder.listIdImage.setImageResource(currentList.get(position).getListIdImageId());
            viewHolder.animeName.setText(currentList.get(position).getAnimeName());
            Bitmap bitmap = currentList.get(position).getImage();
            if(bitmap == null) {
                viewHolder.animeImage.setImageResource(R.drawable.image_not_found);
            }
            else {
                viewHolder.animeImage.setImageBitmap(bitmap);
            }


            //set possible new lists to move anime to
            final String[] listChoices = {"Watching", "Watch Later", "Finished"};
            if (animeShow.getListId() == AnimeShow.LIST_ID_WATCHING) {
                listChoices[0] = "Remove from Watching";
            } else if (animeShow.getListId() == AnimeShow.LIST_ID_WATCH_LATER) {
                listChoices[1] = "Remove from Watch Later";
            } else if (animeShow.getListId() == AnimeShow.LIST_ID_FINISHED) {
                listChoices[2] = "Remove from Finished";
            }

            //set onClick for listIdImage
            viewHolder.listIdImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialogBox = new AlertDialog.Builder(getContext());
                    dialogBox.setTitle("Move anime to which list?");
                    dialogBox.setSingleChoiceItems(listChoices, -1, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int index) {
                            switch (index) {
                                case 0:
                                    if (animeShow.getListId() == AnimeShow.LIST_ID_WATCHING) {
                                        remove(currentList.get(position).getAnimeName());
                                        currentList.remove(position);
                                    }
                                    else {
                                        //add to watching list
                                        int temp = currentList.get(position).getListId();
                                        currentList.get(position).setListId(AnimeShow.LIST_ID_WATCHING);
                                        manager.getWatchingList().add(currentList.get(position));
                                        if (temp == AnimeShow.LIST_ID_WATCH_LATER) {
                                            manager.getWatchLaterList().remove(position);
                                        } else {
                                            manager.getFinishedList().remove(position);
                                        }
                                    }
                                    break;
                                case 1:
                                    if (animeShow.getListId() == AnimeShow.LIST_ID_WATCH_LATER) {
                                        //remove from watch later list
                                        manager.getWatchLaterList().remove(position);
                                        currentList = manager.getWatchLaterList();
                                    } else {
                                        //add to watch later list
                                        int temp = currentList.get(position).getListId();
                                        currentList.get(position).setListId(AnimeShow.LIST_ID_WATCH_LATER);
                                        manager.getWatchLaterList().add(currentList.get(position));
                                        if (temp == AnimeShow.LIST_ID_WATCHING) {
                                            manager.getWatchingList().remove(position);
                                        } else {
                                            manager.getFinishedList().remove(position);
                                        }
                                    }
                                    break;
                                case 2:
                                    if (animeShow.getListId() == AnimeShow.LIST_ID_FINISHED) {
                                        //remove from finished list
                                        manager.getFinishedList().remove(position);
                                        currentList = manager.getFinishedList();
                                    } else {
                                        //add to finished list
                                        int temp = currentList.get(position).getListId();
                                        currentList.get(position).setListId(AnimeShow.LIST_ID_FINISHED);
                                        manager.getFinishedList().add(currentList.get(position));
                                        if (temp == AnimeShow.LIST_ID_WATCH_LATER) {
                                            manager.getWatchLaterList().remove(position);
                                        } else {
                                            manager.getWatchingList().remove(position);
                                        }
                                    }
                                    break;
                            }
                            notifyDataSetChanged();
                            dialog.dismiss();

                        }
                    });
                    dialogBox.show();
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
        protected ImageView listIdImage;
    }
}

