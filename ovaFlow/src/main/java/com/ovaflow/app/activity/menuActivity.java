package com.ovaflow.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ovaflow.app.R;
import com.ovaflow.app.requests.SongClientRequest;
import com.ovaflow.app.util.ExtraConstants;

public class MenuActivity extends Activity {

    private Button playButton;
    private Button avatarButton;
    private Button downloadButton;
    private Button logoutButton;

    private String token;
    private String username;
    private int rmbNum;
    private int avatarId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        playButton = (Button) findViewById(R.id.menu_play_button);
        avatarButton = (Button) findViewById(R.id.menu_avatar_button);
        downloadButton = (Button) findViewById(R.id.menu_dl_button);
        logoutButton = (Button) findViewById(R.id.menu_logout_button);
        unpackIntent();
        addListeners();
    }

    private void unpackIntent() {
        if (getIntent() != null) {
            Bundle extras = getIntent().getExtras();
            token = extras.getString(ExtraConstants.EXTRA_TOKEN);
            username = extras.getString(ExtraConstants.EXTRA_ID);
            rmbNum = extras.getInt(ExtraConstants.EXTRA_RMB);
            avatarId = extras.getInt(ExtraConstants.EXTRA_AVATARID);

            ((TextView) findViewById(R.id.menu_username_text)).setText(username);
            ((TextView) findViewById(R.id.menu_rmb_text)).setText("Rythm Point: " + rmbNum);
        }
    }

    private void addListeners() {
        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(MenuActivity.this);
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, SongSelectActivity.class);
                startActivity(intent);
            }
        });
        avatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ShopAvatarActivity.class);
                intent.putExtra(ExtraConstants.EXTRA_ID, username);
                intent.putExtra(ExtraConstants.EXTRA_RMB, rmbNum);
                intent.putExtra(ExtraConstants.EXTRA_AVATARID, avatarId);
                startActivity(intent);
            }
        });
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SongClientRequest songClient = new SongClientRequest(MenuActivity.this);
                songClient.fetchSongList(token);
            }
        });
    }
}
