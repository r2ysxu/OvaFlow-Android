package com.ovaflow.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ovaflow.app.R;
import com.ovaflow.app.controller.PlaylistExpandListener;
import com.ovaflow.app.model.PlaylistInfo;
import com.ovaflow.app.model.SongInfo;
import com.ovaflow.app.requests.DownloadClientRequest;
import com.ovaflow.app.util.ExtraConstants;
import com.ovaflow.app.view.PlayListAdapter;

public class DownloadActivity extends Activity {

    private ListView songList;
    private Button backButton, cancelButton, dlButton;
    private TextView songView;
    private ProgressBar dlProgress;

    private PlayListAdapter mPlayListAdapter;
    private PlaylistExpandListener mPlayListListener;

    private String userToken;

    private DownloadClientRequest dlClient;
    private PlaylistInfo playlistInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        songList = (ListView) findViewById(R.id.dl_song_list);
        backButton = (Button) findViewById(R.id.dl_back_button);
        cancelButton = (Button) findViewById(R.id.dl_cancel_button);
        backButton = (Button) findViewById(R.id.dl_back_button);
        dlButton = (Button) findViewById(R.id.dl_dl_button);
        dlProgress = (ProgressBar) findViewById(R.id.dl_progress);
        songView = (TextView) findViewById(R.id.dl_name_text);

        dlClient = new DownloadClientRequest(this);

        unpackIntent();
        fillPlayList();
        addButtonListeners();
    }

    private void unpackIntent() {
        if (getIntent() != null) {
            Bundle extras = getIntent().getExtras();
            userToken = extras.getString(ExtraConstants.EXTRA_TOKEN);
            playlistInfo = (PlaylistInfo) extras.get(ExtraConstants.EXTRA_SONG_LIST);
        }
    }

    private void fillPlayList() {

        final ExpandableListView playlist = (ExpandableListView) findViewById(R.id.dl_song_list);

        mPlayListAdapter = new PlayListAdapter(DownloadActivity.this,
                playlistInfo);
        playlist.setAdapter(mPlayListAdapter);
        mPlayListListener = new PlaylistExpandListener(playlist);
        playlist.setOnGroupExpandListener(mPlayListListener);
    }

    private void addButtonListeners() {
        dlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selected = mPlayListListener.getSelectedGroup();
                if (selected != -1) {
                    Log.i("DownloadActivity", "Initiate Download");
                    SongInfo si = mPlayListAdapter.getSongInfo(selected);
                    dlClient.downloadSong(userToken, si.getSongId(), si.getName(), si.getArtist(), si.getAlbum(), dlProgress);
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlClient.cancel();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavUtils.navigateUpFromSameTask(DownloadActivity.this);
            }
        });
    }
}
