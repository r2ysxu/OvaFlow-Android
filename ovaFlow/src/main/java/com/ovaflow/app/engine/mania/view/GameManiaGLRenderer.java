package com.ovaflow.app.engine.mania.view;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import com.ovaflow.app.engine.mania.controller.GameManiaEngine;
import com.ovaflow.app.engine.mania.model.renderable.Crossbar;
import com.ovaflow.app.engine.mania.model.renderable.Hitbox;
import com.ovaflow.app.engine.mania.model.renderable.Note;
import com.ovaflow.app.engine.mania.model.renderable.Slider;

import java.util.LinkedList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * GL Renderer for the Mania game mode
 */
public class GameManiaGLRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "GameManiaGLRenderer";
    public static final int MAXHITBOX = 5;

    private GameManiaEngine gmee;
    private boolean gameStarted = false;

    private List<Note> notes;
    private Slider mSlider;
    private Crossbar mCrossbar;
    private Hitbox[] mHitboxs = new Hitbox[MAXHITBOX];
    private List<Note> currentNotes;

    private final float[] mViewMatrix = new float[16];
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];

    public void startGame() {
        if (!gameStarted)
            gmee = new GameManiaEngine();
    }

    private void surfaceCreated() {
        // Set the background frame color
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        //Initialize Objects
        mSlider = new Slider();
        mCrossbar = new Crossbar();
        notes = Note.generateNotes();
        for (int i = 0; i < MAXHITBOX; i++) {
            mHitboxs[i] = new Hitbox(i);
        }
        currentNotes = new LinkedList<Note>();
    }

    private void drawNotes() {
        long elapsedTime = ((System.currentTimeMillis() - gmee.getStartTime()) / 100) * 100;

        if (!notes.isEmpty() && (Math.abs(notes.get(0).getTime() - elapsedTime) < 100)) {
            currentNotes.add(notes.remove(0));
        }

        for (int i = 0; i < currentNotes.size(); i++) {
            Note n = currentNotes.get(i);
            n.draw(mMVPMatrix);
            n.fallDown();
            if (n.missedOut()) {
                currentNotes.remove(i);
                i--;
            }
        }
    }

    private void drawFrame() {
        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Set the camera position
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        mSlider.draw(mMVPMatrix);
        mCrossbar.draw(mMVPMatrix);
        for (int i = 0; i < MAXHITBOX; i++)
            mHitboxs[i].draw(mMVPMatrix);
        if (gmee.getStartTime() > 0)
            drawNotes();
    }

    public int checkCollide() {
        int sum = 0;
        for (int i = 0; i < currentNotes.size(); i++) {
            Note n = currentNotes.get(i);
            sum += n.checkTolerance(Hitbox.YPOS);
        }
        return sum;
    }

    public void buttonPressed(int index) {
        float[] color = {0.0f, 1.0f, 1.0f, 1.0f};
        mHitboxs[index].setColor(color);
        gmee.addScore(checkCollide());
    }

    public void buttonReleased(int index) {
        float color[] = {0.0f, 0.0f, 1.0f, 0.0f};
        mHitboxs[index].setColor(color);
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        surfaceCreated();
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        drawFrame();
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        // Adjust the viewport based on geometry changes,
        // such as screen rotation
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    /**
     * Utility method for compiling a OpenGL shader.
     * <p/>
     * <p><strong>Note:</strong> When developing shaders, use the checkGlError()
     * method to debug shader coding errors.</p>
     *
     * @param type       - Vertex or fragment shader type.
     * @param shaderCode - String containing the shader code.
     * @return - Returns an id for the shader.
     */
    public static int loadShader(int type, String shaderCode) {

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    public static void checkGlError(String glOperation) {
        int error;
        if ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }
}