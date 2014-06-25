package com.ovaflow.app.engine.mania.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * Created by ArthurXu on 09/06/2014.
 */
public class GameManiaSurfaceView extends GLSurfaceView {

    private final GameManiaGLRenderer mRenderer;
    //private final GameManiaTouchInputListener mInputListener;

    public GameManiaSurfaceView(Context context) {
        super(context);

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        mRenderer = new GameManiaGLRenderer(context);
        //mInputListener = new GameManiaTouchInputListener(mRenderer);

        setRenderer(mRenderer);
        //setOnTouchListener(mInputListener);

        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent me) {
        float x = me.getX();
        float y = me.getY();

        float adjX = (2 * (x - getWidth() / 2) / getWidth());
        float adjY = (2 * -(y - getHeight() / 2) / getHeight());

        if (me.getAction() == MotionEvent.ACTION_DOWN) {
            //mRenderer.buttonPressed(adjX, adjY);
        } else if (me.getAction() == MotionEvent.ACTION_UP) {
            //mRenderer.buttonReleased(adjX, adjY);
        }
        return true;
    }

    public void pause() {
        mRenderer.pause();
    }

    public void resume() {
        mRenderer.resume();
    }
}
