package com.ovaflow.app.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ovaflow.app.R;

public class menuActivity extends Activity {

    private Button play;
    private Button avatar;
    private Button dl;
    private Button logout;
    private TextView userInfo;
    private Activity curActivity = this;

    private String ID;
    private int RMB;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        ID = intent.getStringExtra(MainActivity.EXTRA_ID);
        RMB = intent.getIntExtra(MainActivity.EXTRA_RMB, -1);

        userInfo = (TextView) findViewById(R.id.info);
        //userInfo.setText("User:"+ID);
        userInfo.setText("User:"+ID+" RMB:"+RMB);

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
    }


    public void sendMessage(View view) {
        Intent intent = new Intent(this, SongSelectActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */

}
