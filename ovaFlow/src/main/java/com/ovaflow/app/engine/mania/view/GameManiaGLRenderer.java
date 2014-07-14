package com.ovaflow.app.engine.mania.view;

import android.content.Context;
import android.content.Intent;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import com.ovaflow.app.R;
import com.ovaflow.app.activity.SummaryActivity;
import com.ovaflow.app.engine.mania.controller.GameManiaController;
import com.ovaflow.app.engine.mania.model.renderable.Background;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * GL Renderer for the Mania game mode
 */
public class GameManiaGLRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "GameManiaGLRenderer";

    private Context mActivityContext;

    private final GameManiaCanvas gmc;

    private final float[] mViewMatrix = new float[16];
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];

    private Background background;

    public GameManiaGLRenderer(Context context, GameManiaController gmee) {
        mActivityContext = context;
        gmc = new GameManiaCanvas(context, gmee);
    }

    public void buttonPressed(float x, float y) {
        gmc.buttonPressed(x, y);
    }

    public void buttonReleased(float x, float y) {
        gmc.buttonReleased(x, y);
    }

    public void pause() {
        gmc.pauseGame();
    }

    public void resume() {
        gmc.resumeGame();
    }

    public boolean enterGameplay() {
        gmc.startGame();
        return true;
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {

        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

        // Set the background frame color
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        // Set the camera position
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);


        background = new Background(mActivityContext, R.drawable.mania_background);
        gmc.onSurfaceCreated();
        enterGameplay();
    }

    private long songEndDelayTimer = Long.MAX_VALUE;

    @Override
    public void onDrawFrame(GL10 unused) {
        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        //Draw Everything
        background.draw(mMVPMatrix);
        if (gmc.drawFrame(mMVPMatrix))
            songEndDelayTimer = System.currentTimeMillis();
        if (System.currentTimeMillis() > songEndDelayTimer + 1000)
            startSummaryActivity();
    }

    public void startSummaryActivity() {
        Intent intent = new Intent(mActivityContext, SummaryActivity.class);
        intent.putExtra("ScoreType", gmc.getScoreType());
        mActivityContext.startActivity(intent);
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        // Adjust the viewport based on geometry changes,
        // such as screen rotation
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;
        Log.i("SurfaceChanged", "Ratio: " + ratio);

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -1, 1, -1, 1, 3, 7);
    }
}