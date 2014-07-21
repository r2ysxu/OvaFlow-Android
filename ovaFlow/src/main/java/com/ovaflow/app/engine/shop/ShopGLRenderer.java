package com.ovaflow.app.engine.shop;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.ovaflow.app.engine.mania.model.renderable.Avatar;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by ArthurXu on 20/07/2014.
 */
public class ShopGLRenderer implements GLSurfaceView.Renderer {

    private Context mActivityContext;

    private final float[] mViewMatrix = new float[16];
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];

    private Avatar avatar;

    public ShopGLRenderer(Context context) {
        mActivityContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

        // Set the background frame color
        GLES20.glClearColor(1f, 0f, 0f, 0f);

        // Set the camera position
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        avatar = new Avatar(mActivityContext);
        avatar.scaleDim(10f, 10f);
        avatar.setPosition(0f, 0f);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        avatar.draw(mMVPMatrix);
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        // Adjust the viewport based on geometry changes,
        // such as screen rotation
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -1, 1, -1, 1, 3, 7);
    }

    public Avatar getAvatar() {
        return avatar;
    }
}
