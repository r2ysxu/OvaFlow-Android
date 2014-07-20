package com.ovaflow.app.engine.shop;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.ovaflow.app.engine.mania.model.renderable.Avatar;

/**
 * Created by ArthurXu on 20/07/2014.
 */
public class ShopSurfaceView extends GLSurfaceView  {

    private final ShopGLRenderer sRenderer;

    public ShopSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);
        sRenderer = new ShopGLRenderer(context);
        setRenderer(sRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    public boolean setAvatar(int id) {
        Avatar avatar = sRenderer.getAvatar();
        if (avatar != null) {
            avatar.setAvatar(id);
            return true;
        }
        return false;
    }
}