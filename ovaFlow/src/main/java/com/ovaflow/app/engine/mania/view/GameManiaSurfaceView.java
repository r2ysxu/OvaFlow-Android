package com.ovaflow.app.engine.mania.view;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.ovaflow.app.engine.mania.controller.GameManiaEngine;

/**
 * Created by ArthurXu on 09/06/2014.
 */
public class GameManiaSurfaceView extends GLSurfaceView {

    private final GameManiaEngine gmEngine;

    public GameManiaSurfaceView(Context context) {
        super(context);

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        gmEngine = new GameManiaEngine();
        setRenderer(gmEngine.getRenderer());
        setOnTouchListener(gmEngine.getInputListener());
        gmEngine.startGame();

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
