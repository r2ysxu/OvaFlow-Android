package com.ovaflow.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ovaflow.app.engine.mania.view.GameManiaSurfaceView;

public class GameManiaActivity extends Activity {

    private static final String TAG = GameManiaActivity.class.getName();

    private GameManiaSurfaceView mGLView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGLView = new GameManiaSurfaceView(this);
        setContentView(mGLView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "HERE");
        if (resultCode == 1) {
            Log.i(TAG, "Result Code: " + resultCode);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mGLView.pause();
    }
}