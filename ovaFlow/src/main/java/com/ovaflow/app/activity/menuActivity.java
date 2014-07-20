package com.ovaflow.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ovaflow.app.R;
import com.ovaflow.app.requests.LoginClientRequest;

public class MenuActivity extends Activity {

    private Button play;
    private Button avatar;
    private Button dl;
    private Button logout;
    private TextView userInfo;
    private Activity curActivity = this;

    private String ID;
    private int RMB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        ID = intent.getStringExtra(LoginClientRequest.EXTRA_ID);
        RMB = intent.getIntExtra(LoginClientRequest.EXTRA_RMB, -1);

        //userInfo = (TextView) findViewById(R.id.info);
        //userInfo.setText("User:"+ID);
        //userInfo.setText("User:"+ID+" RMB:"+RMB);

        play = (Button) findViewById(R.id.play);
        avatar = (Button) findViewById(R.id.avatar);
        dl = (Button) findViewById(R.id.dl);
        logout = (Button) findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //go back to previous activity(log in page)
                NavUtils.navigateUpFromSameTask(curActivity);
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendMessage(v);
            }
        });

        if (getIntent() != null) {
            Bundle extras = getIntent().getExtras();
            String username = extras.getString(LoginClientRequest.EXTRA_ID);
            int rmb = extras.getInt(LoginClientRequest.EXTRA_RMB);
            int avatarID = extras.getInt(LoginClientRequest.EXTRA_AVATARID);

            ((TextView) findViewById(R.id.menu_username_text)).setText(username);
            ((TextView) findViewById(R.id.menu_rmb_text)).setText("Rythm Point: " + rmb);
        }
    }


    public void sendMessage(View view) {
        Intent intent = new Intent(this, SongSelectActivity.class);
        startActivity(intent);
    }
}
