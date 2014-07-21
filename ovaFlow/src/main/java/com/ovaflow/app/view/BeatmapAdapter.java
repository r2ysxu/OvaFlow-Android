package com.ovaflow.app.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ovaflow.app.R;
import com.ovaflow.app.model.BeatmapInfo;

/**
 * Created by ArthurXu on 05/07/2014.
 */
public class BeatmapAdapter extends ArrayAdapter<BeatmapInfo> {

    BeatmapInfo[] beatmaps;

    public BeatmapAdapter(Context context, BeatmapInfo[] beatmaps) {
        super(context, R.layout.play_list_beatmap, beatmaps);
        this.beatmaps = beatmaps;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.play_list_beatmap, null);
        }

        TextView beatmapName = (TextView) view.findViewById(R.id.beatmap_name);
        beatmapName.setTextColor(Color.WHITE);
        ProgressBar diff = (ProgressBar) view.findViewById(R.id.beatmap_diff);
        diff.setProgress(beatmaps[position].getDifficulty());
       // RatingBar difficulty = (RatingBar) view.findViewById(R.id.beatmap_difficulty);
        //difficulty.setEnabled(false);
        //difficulty.setNumStars(4);
        beatmapName.setText(beatmaps[position].getName());

        return view;
    }

    public BeatmapInfo getBeatmapInfo(int i) {
        return beatmaps[i];
    }
}
