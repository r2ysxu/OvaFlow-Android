package com.ovaflow.app.engine.mania.view;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import com.ovaflow.app.engine.mania.controller.GameManiaController;
import com.ovaflow.app.engine.mania.model.renderable.Crossbar;
import com.ovaflow.app.engine.mania.model.renderable.Hitbox;
import com.ovaflow.app.engine.mania.model.renderable.Note;
import com.ovaflow.app.engine.mania.model.renderable.primitives.TextureObject;

import java.util.LinkedList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * GL Renderer for the Mania game mode
 */
public class GameManiaGLRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "GameManiaGLRenderer";

    private Context mActivityContext;

    private GameManiaController gmee;
    private boolean gameStarted = false;

    private List<Note> notes;
    private Crossbar mCrossbar;
    private Hitbox mHitboxs;
    private List<Note> currentNotes;

    private TextureObject scoreHUD;

    private final float[] mViewMatrix = new float[16];
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];

    public GameManiaGLRenderer(Context context) {
        mActivityContext = context;
    }

    public void startGame() {
        if (!gameStarted) {
            gmee = new GameManiaController();
            gmee.playMusic(mActivityContext);
            gameStarted = true;
        }
    }

    public void restartGame() {
        if (gameStarted) {
            gmee = new GameManiaController();
            notes = Note.generateNotes();
        }
    }

    private void surfaceCreated() {
        // Set the background frame color
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glEnable(GLES20.GL_TEXTURE_2D);

        //Initialize Objects
        mCrossbar = new Crossbar();
        notes = Note.generateNotes();
        mHitboxs = new Hitbox();
        currentNotes = new LinkedList<Note>();
        startGame();

        scoreHUD = new TextureObject(mActivityContext);
        scoreHUD.setTextTexture(mActivityContext, "Score: 0");
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
            if (n.missedOut(Crossbar.HITRANGE, Crossbar.HEIGHT)) {
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
        mCrossbar.draw(mMVPMatrix);
        mHitboxs.draw(mMVPMatrix);
        if (gmee.getStartTime() > 0)
            drawNotes();
        scoreHUD.draw(mMVPMatrix);
    }

    public int checkCollide(int index) {
        int sum = 0;
        for (int i = 0; i < currentNotes.size(); i++) {
            Note n = currentNotes.get(i);
            int num = n.checkTolerance(Crossbar.HITRANGE);
            if (num > 0) {
                currentNotes.remove(i);
            }
            gmee.addScore(num);
            sum += num;
        }
        return sum;
    }

    public void buttonPressed(float x, float y) {
        int index = mHitboxs.contains(x, y);
        Log.i("Hitbox", "Index:" + index);
        if (index != -1) {
            checkCollide(index);
            mHitboxs.setPressed(index, true);
        }
    }

    public void buttonReleased(float x, float y) {
        int index = mHitboxs.contains(x, y);
        if (index != -1)
            mHitboxs.setPressed(index, false);
    }

    public void pause() {
        gmee.pauseMusic();
    }

    public void resume() {
        gmee.resumeMusic();
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
        Log.i("SurfaceChanged", "Ratio: " + ratio);

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -1, 1, -1, 1, 3, 7);
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
        int shaderHandle = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shaderHandle, shaderCode);
        GLES20.glCompileShader(shaderHandle);

        // Get the compilation status.
        final int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

        // If the compilation failed, delete the shader.
        if (compileStatus[0] == 0) {
            Log.e(TAG, "Error compiling shader: " + GLES20.glGetShaderInfoLog(shaderHandle));
            GLES20.glDeleteShader(shaderHandle);
            shaderHandle = 0;
        }

        return shaderHandle;
    }

    public static void checkGlError(String glOperation) {
        int error;
        if ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }
}