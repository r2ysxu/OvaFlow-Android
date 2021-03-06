package com.ovaflow.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.ovaflow.app.engine.mania.view.GameManiaSurfaceView;
import com.ovaflow.app.model.BeatmapInfo;
import com.ovaflow.app.util.ExtraConstants;

public class GameManiaActivity extends Activity {

    private static final String TAG = GameManiaActivity.class.getName();

    private GameManiaSurfaceView mGLView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int songId = getIntent().getExtras().getInt("SongId");
        BeatmapInfo beatmapInfo = (BeatmapInfo) getIntent().getExtras().get(ExtraConstants.EXTRA_BM_INFO);
        int avatarId = getIntent().getExtras().getInt(ExtraConstants.EXTRA_AVATARID);

        Log.i("AVATAR ID:", avatarId +"");

        mGLView = new GameManiaSurfaceView(this, songId, beatmapInfo, avatarId);
        setContentView(mGLView);
    }

    @Override
    public void onPause() {
        super.onPause();
        mGLView.pause();
    }
}