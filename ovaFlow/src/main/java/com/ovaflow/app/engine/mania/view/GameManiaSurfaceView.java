package com.ovaflow.app.engine.mania.view;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.ovaflow.app.engine.mania.controller.GameManiaController;
import com.ovaflow.app.engine.mania.controller.GameManiaTouchInputListener;

/**
 * Created by ArthurXu on 09/06/2014.
 */
public class GameManiaSurfaceView extends GLSurfaceView {

    private final GameManiaGLRenderer gmRenderer;
    private final GameManiaTouchInputListener mInputListener;
    private final GameManiaController gmController;

    public GameManiaSurfaceView(Context context) {
        super(context);

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        gmController = new GameManiaController();
        gmRenderer = new GameManiaGLRenderer(context, gmController);
        mInputListener = new GameManiaTouchInputListener(gmRenderer);

        setRenderer(gmRenderer);
        setOnTouchListener(mInputListener);

        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

    }

    public void pause() {
        gmRenderer.pause();
    }

    public void resume() {
        gmRenderer.resume();
    }
}
