package com.ovaflow.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ovaflow.app.R;
import com.ovaflow.app.engine.shop.ShopSurfaceView;
import com.ovaflow.app.util.ExtraConstants;
import com.ovaflow.app.view.AvatarAdapter;

public class ShopAvatarActivity extends Activity {

    private String username;
    private int rmbNum;
    private int avatarId;

    AvatarAdapter avatarAdapter;
    ShopSurfaceView currentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_avatar);
        unpackIntent();
        populateListView();
        addListener();
    }

    private void addListener() {

    }

    private void populateListView() {
        currentView = (ShopSurfaceView) findViewById(R.id.shop_avatar_current_view);

        ListView sc = (ListView) findViewById(R.id.shop_avatar_list);
        avatarAdapter = new AvatarAdapter(this);
        sc.setAdapter(avatarAdapter);
        sc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentView.setAvatar(i);
            }
        });
    }

    private void unpackIntent() {
        if (getIntent() != null) {
            Bundle extras = getIntent().getExtras();
            username = extras.getString(ExtraConstants.EXTRA_ID);
            rmbNum = extras.getInt(ExtraConstants.EXTRA_RMB);
            avatarId = extras.getInt(ExtraConstants.EXTRA_AVATARID);

            ((TextView) findViewById(R.id.shop_avatar_name)).setText(username);
            ((TextView) findViewById(R.id.shop_avatar_rmb)).setText("Rythm Points: " + rmbNum);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!((ShopSurfaceView) findViewById(R.id.shop_avatar_current_view)).setAvatar(avatarId));
                }
            });
            thread.start();
        }
    }
}
