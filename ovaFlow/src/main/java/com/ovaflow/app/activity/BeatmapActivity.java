package com.ovaflow.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ovaflow.app.R;
import com.ovaflow.app.model.BeatmapInfo;
import com.ovaflow.app.model.SongInfo;
import com.ovaflow.app.view.BeatmapAdapter;

public class BeatmapActivity extends Activity {

    private BeatmapAdapter mBeatmapAdapter;
    private AdapterView.OnItemClickListener onItemClickListener;
    private TextView songName, songInfo, songDuration;
    private Button backButton, playButton;

    private int selectedBeatmapIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beatmap);

        songName = (TextView) findViewById(R.id.beatmap_song_name);
        songInfo = (TextView) findViewById(R.id.beatmap_song_info);
        songDuration = (TextView) findViewById(R.id.beatmap_song_duration);

        backButton = (Button) findViewById(R.id.beatmap_back_button);
        playButton = (Button) findViewById(R.id.beatmap_play_button);

        setupResults();
        fillBeatmapList();
        addListener();
    }

    private void fillBeatmapList() {
        mBeatmapAdapter = new BeatmapAdapter(this, BeatmapInfo.generateBeatmaps());
        ListView beatmap = (ListView) findViewById(R.id.beatmap_list);
        beatmap.setAdapter(mBeatmapAdapter);
        onItemClickListener = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedBeatmapIndex = i;
            }
        };
        beatmap.setOnItemClickListener(onItemClickListener);
    }

    private void setupResults() {
        SongInfo si = (SongInfo) getIntent().getExtras().get("Song");
        songName.setText(si.getName());
        songInfo.setText(si.getArtist() + " - " + si.getAlbum());
        songDuration.setText(si.getDurationStr());
    }

    private void addListener() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavUtils.navigateUpFromSameTask(BeatmapActivity.this);
            }
        });
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedBeatmapIndex > -1)
                    selectBeatmap(mBeatmapAdapter.getBeatmapInfo(selectedBeatmapIndex));
            }
        });
    }

    private void selectBeatmap(BeatmapInfo beatmapInfo) {
        Intent intent = new Intent(this, GameManiaActivity.class);
        intent.putExtra("Beatmap", beatmapInfo);
        startActivity(intent);
    }
}
