package com.ovaflow.app.engine.shop;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by ArthurXu on 20/07/2014.
 */
public class ShopSurfaceView extends GLSurfaceView  {

    private final ShopGLRenderer sRenderer;

    public ShopSurfaceView(Context context) {
        super(context);
        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);
        sRenderer = new ShopGLRenderer(context);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }
}