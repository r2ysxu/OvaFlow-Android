package com.ovaflow.app.engine.mania.controller;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.ovaflow.app.engine.mania.view.GameManiaGLRenderer;

/**
 * Created by ArthurXu on 09/06/2014.
 */
public class GameManiaTouchInputListener implements View.OnTouchListener {

    private final GameManiaGLRenderer mRenderer;

    public GameManiaTouchInputListener(GameManiaGLRenderer mRenderer) {
        this.mRenderer = mRenderer;
    }

    @Override
    public boolean onTouch(View view, MotionEvent me) {
        float x = me.getX();
        float y = me.getY();

        mRenderer.startGame();

        Log.i("Pos", me.getAction() + "(" + x + ", " + y + ")");
        if (me.getAction() == MotionEvent.ACTION_DOWN) {
            mRenderer.buttonPressed(0);
        } else if (me.getAction() == MotionEvent.ACTION_UP) {
            mRenderer.buttonReleased(0);
        }
        return false;
    }
}
