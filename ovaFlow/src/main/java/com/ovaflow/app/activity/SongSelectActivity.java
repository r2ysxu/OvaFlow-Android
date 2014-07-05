package com.ovaflow.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.ovaflow.app.R;
import com.ovaflow.app.controller.PlaylistExpandListener;
import com.ovaflow.app.model.PlaylistInfo;
import com.ovaflow.app.view.PlayListAdapter;

public class SongSelectActivity extends Activity {

    PlayListAdapter mPlayListAdapter;
    PlaylistExpandListener mPlayListListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_select);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.song_select, menu);
        fillPlayList();
        addButtonListener();
        return true;
    }

    private void fillPlayList() {
        final ExpandableListView playlist = (ExpandableListView) findViewById(R.id.play_list);
        mPlayListAdapter = new PlayListAdapter(this, PlaylistInfo.generatePlaylist());
        mPlayListListener = new PlaylistExpandListener(playlist);

        playlist.setAdapter(mPlayListAdapter);
        playlist.setOnGroupExpandListener(mPlayListListener);
    }

    private void addButtonListener() {
        Button playButton = (Button) findViewById(R.id.play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSong();
            }
        });
    }

    private void playSong() {
        Intent intent = new Intent(this, GameManiaActivity.class);
        intent.putExtra("SongId", mPlayListListener.getSelectedGroup());
        startActivityForResult(intent, 1);
    }
}
