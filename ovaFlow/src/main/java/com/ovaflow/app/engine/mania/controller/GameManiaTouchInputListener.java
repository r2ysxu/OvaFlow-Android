package com.ovaflow.app.engine.mania.controller;

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

        float adjX = (2 * (x - view.getWidth() / 2) / view.getWidth());
        float adjY = (2 * -(y - view.getHeight() / 2) / view.getHeight());

        if (me.getAction() == MotionEvent.ACTION_DOWN) {
            //if (adjY < -0.9f)
            //    mRenderer.restartGame();
            //mRenderer.buttonPressed(adjX, adjY);
        } else if (me.getAction() == MotionEvent.ACTION_UP) {
            //mRenderer.buttonReleased(adjX, adjY);
        }
        return true;
    }
}
