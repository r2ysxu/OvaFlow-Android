package com.ovaflow.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.ovaflow.app.R;
import com.ovaflow.app.controller.PlaylistExpandListener;
import com.ovaflow.app.localstorage.SongFileLocator;
import com.ovaflow.app.model.PlaylistInfo;
import com.ovaflow.app.view.PlayListAdapter;

public class SongSelectActivity extends Activity {

    private PlayListAdapter mPlayListAdapter;
    private PlaylistExpandListener mPlayListListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_select);
        fillPlayList();
        addButtonListener();
    }

    private void fillPlayList() {
        final ExpandableListView playlist = (ExpandableListView) findViewById(R.id.play_list);
        playlist.setChoiceMode(ExpandableListView.CHOICE_MODE_SINGLE);

        SongFileLocator sfl = new SongFileLocator(this);

        mPlayListAdapter = new PlayListAdapter(this, PlaylistInfo.generatePlaylist(sfl.fetchAllSongData()));
        mPlayListListener = new PlaylistExpandListener(playlist);

        playlist.setAdapter(mPlayListAdapter);
        playlist.setOnGroupExpandListener(mPlayListListener);
    }

    private void addButtonListener() {
        Button playButton = (Button) findViewById(R.id.play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPlayListListener.getSelectedGroup() > -1) {
                    selectSong();
                }
            }
        });
        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavUtils.navigateUpFromSameTask(SongSelectActivity.this);
            }
        });
    }

    private void selectSong() {
        Intent intent = new Intent(this, BeatmapActivity.class);
        intent.putExtra("Song", mPlayListAdapter.getSongInfo(mPlayListListener.getSelectedGroup()));
        startActivity(intent);
    }
}
