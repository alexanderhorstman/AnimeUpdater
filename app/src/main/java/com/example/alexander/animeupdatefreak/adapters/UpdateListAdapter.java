package com.example.alexander.animeupdatefreak.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.alexander.animeupdatefreak.R;

import java.util.ArrayList;

public class UpdateListAdapter extends ArrayAdapter<String> {

    //ArrayList<AnimeShow> animes;
    ArrayList<String> episodes;
    ArrayList<String> watchedEpisodes;
    Context context;
    TextView episodeName;

    public UpdateListAdapter(Context context, ArrayList episodes, ArrayList watchEpisodes) {
        super(context, R.layout.update_activity_layout, episodes);
        //this.animes = animes;
        this.context = context;
        this.episodes = episodes;
        this.watchedEpisodes = watchEpisodes;
    }

    public View getView(final int position, final View convertView, ViewGroup parent) {
        //View view = convertView;
        //LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //view = inflater.inflate(R.layout.update_list_layout, null);
        episodeName = (TextView) convertView.findViewById(R.id.episode_name);

        episodeName.setText(episodes.get(position));

        final String[] listChoices = {"Mark as Watched", "Cancel"};
        episodeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBox = new AlertDialog.Builder(context);
                dialogBox.setTitle("Watch this episode?");
                dialogBox.setSingleChoiceItems(listChoices, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int index) {
                        switch (index) {
                            case 0:
                                setEpisodeAsWatched(position);
                                //-----------------------------------------------------------Change how this works
                                //-----------------------------------------------------------Not sure how I want it yet
                                //watchedEpisodes.add(episodes.get(position));
                                //episodes.remove(position);
                                //remove(episodes.get(position));
                                break;
                            case 1:
                                //do nothing
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

        return episodeName;
    }

    private void setEpisodeAsWatched(int index) {
        episodeName.setPaintFlags(episodeName.getPaintFlags() ^ Paint.STRIKE_THRU_TEXT_FLAG);
    }

}
