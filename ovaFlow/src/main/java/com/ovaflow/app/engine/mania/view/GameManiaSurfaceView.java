package com.ovaflow.app.engine.mania.view;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.ovaflow.app.engine.mania.controller.GameManiaTouchInputListener;

/**
 * Created by ArthurXu on 09/06/2014.
 */
public class GameManiaSurfaceView extends GLSurfaceView {

    private final GameManiaGLRenderer mRenderer;
    private final GameManiaTouchInputListener mInputListener;

    public GameManiaSurfaceView(Context context) {
        super(context);

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        mRenderer = new GameManiaGLRenderer(context);
        mInputListener = new GameManiaTouchInputListener(mRenderer);

        setRenderer(mRenderer);
        setOnTouchListener(mInputListener);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }
}
