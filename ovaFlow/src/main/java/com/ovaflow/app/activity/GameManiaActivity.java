package com.ovaflow.app.activity;

import android.app.Activity;
import android.os.Bundle;

import com.ovaflow.app.engine.mania.view.GameManiaSurfaceView;

public class GameManiaActivity extends Activity {

    private GameManiaSurfaceView mGLView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGLView = new GameManiaSurfaceView(this);
        setContentView(mGLView);
    }

    @Override
    public void onPause() {
        super.onPause();
        mGLView.pause();
    }
}