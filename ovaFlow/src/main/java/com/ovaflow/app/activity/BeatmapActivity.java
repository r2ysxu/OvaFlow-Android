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
import com.ovaflow.app.localstorage.SongFileLocator;
import com.ovaflow.app.model.BeatmapInfo;
import com.ovaflow.app.model.SongInfo;
import com.ovaflow.app.util.ExtraConstants;
import com.ovaflow.app.view.BeatmapAdapter;

public class BeatmapActivity extends Activity {

    private BeatmapAdapter mBeatmapAdapter;
    private AdapterView.OnItemClickListener onItemClickListener;
    private TextView songName, songInfoView, songDuration;
    private Button backButton, playButton;

    private int selectedBeatmapIndex = -1;

    private SongInfo songInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beatmap);

        songName = (TextView) findViewById(R.id.beatmap_song_name);
        songInfoView = (TextView) findViewById(R.id.beatmap_song_info);
        songDuration = (TextView) findViewById(R.id.beatmap_song_duration);

        backButton = (Button) findViewById(R.id.beatmap_back_button);
        playButton = (Button) findViewById(R.id.beatmap_play_button);

        unpackIntent();
        fillBeatmapList();
        addListener();
    }

    private void fillBeatmapList() {
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

    private void unpackIntent() {
        songInfo = (SongInfo) getIntent().getExtras().get(ExtraConstants.EXTRA_SONG_INFO);
        songName.setText(songInfo.getName());
        songInfoView.setText(songInfo.getArtist() + " - " + songInfo.getAlbum());
        songDuration.setText(songInfo.getDurationStr());

        SongFileLocator sfl = new SongFileLocator(this);

        mBeatmapAdapter = new BeatmapAdapter(this, BeatmapInfo.generateBeatmaps(
                sfl.fetchBeatmaps(songInfo.getSongId())));
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
                    selectBeatmap(mBeatmapAdapter.getBeatmapInfo(selectedBeatmapIndex), songInfo);
            }
        });
    }

    private void selectBeatmap(BeatmapInfo beatmapInfo, SongInfo songInfo) {
        Bundle extras = getIntent().getExtras();

        Intent intent = new Intent(this, GameManiaActivity.class);
        intent.putExtra(ExtraConstants.EXTRA_TOKEN, extras.getString(ExtraConstants.EXTRA_TOKEN));
        intent.putExtra(ExtraConstants.EXTRA_AVATARID, extras.getInt(ExtraConstants.EXTRA_AVATARID));
        intent.putExtra("SongId", songInfo.getSongId());
        intent.putExtra(ExtraConstants.EXTRA_BM_INFO, beatmapInfo);
        startActivity(intent);
    }
}
