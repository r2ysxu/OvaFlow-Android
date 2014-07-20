package com.ovaflow.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.ovaflow.app.R;
import com.ovaflow.app.engine.shop.ShopSurfaceView;
import com.ovaflow.app.util.ExtraConstants;

public class ShopAvatarActivity extends Activity {

    private String username;
    private int rmbNum;
    private int avatarId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_avatar);
        unpackIntent();
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
