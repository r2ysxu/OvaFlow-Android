package com.ovaflow.app.engine.mania.view;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import com.ovaflow.app.R;
import com.ovaflow.app.engine.mania.controller.GameManiaController;
import com.ovaflow.app.engine.mania.controller.KeyNote;
import com.ovaflow.app.engine.mania.model.renderable.Background;
import com.ovaflow.app.engine.mania.model.renderable.Crossbar;
import com.ovaflow.app.engine.mania.model.renderable.HUD;
import com.ovaflow.app.engine.mania.model.renderable.Hitbox;
import com.ovaflow.app.engine.mania.model.renderable.Notes;

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

    private List<KeyNote> keynotes;
    private Crossbar mCrossbar;
    private Hitbox mHitboxs;
    private Notes currentNotes;
    private Background background;

    private boolean mButtonPressed = false;
    private float mButtonPressedX;
    private float mButtonPressedY;

    private HUD hud;

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

    private void drawNotes() {
        long elapsedTime = ((System.currentTimeMillis() - gmee.getStartTime()) / 100) * 100;

        if (!keynotes.isEmpty() && (Math.abs(keynotes.get(0).getTime() - elapsedTime) < 100)) {
            currentNotes.addKeyNote(keynotes.remove(0));
        }
        currentNotes.draw(mMVPMatrix);
        if (currentNotes.checkMissed(Crossbar.HITRANGE, Crossbar.HEIGHT)) {
            gmee.missedNote();
        }
    }

    private void drawHitAnimation(float[] mMVPmatrix) {
        int scoreChanged = gmee.scoreChanged();
        if (scoreChanged > 0) {
            hud.updateScore("Score:" + gmee.getScore());
            hud.startSplash();
        }
        hud.drawHitSplash(mMVPmatrix, scoreChanged);
    }

    public void checkCollide(int index) {
        int[] vals = currentNotes.checkCollide(index, Crossbar.HITRANGE);
        gmee.addScore(vals[0], vals[1]);
    }

    public void buttonPressed(float x, float y) {
        mButtonPressed = true;
        mButtonPressedX = x;
        mButtonPressedY = y;
        /*int index = mHitboxs.contains(x, y);
        if (index != -1) {
            checkCollide(index);
            mHitboxs.setPressed(index, true);
        }*/
    }

    public void buttonReleased(float x, float y) {
        mButtonPressed = false;
        /*int index = mHitboxs.contains(x, y);
        if (index != -1)
            mHitboxs.setPressed(index, false);*/
    }

    public void pause() {
        gmee.pauseMusic();
    }

    public void resume() {
        gmee.resumeMusic();
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {

        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glEnable(GLES20.GL_CULL_FACE);

        // Set the background frame color
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        //Initialize Objects
        background = new Background(mActivityContext);
        mCrossbar = new Crossbar(mActivityContext);
        keynotes = KeyNote.generateNotes(mActivityContext, R.raw.default_beatmap);
        mHitboxs = new Hitbox();
        currentNotes = new Notes();
        //hud = new HUD(mActivityContext);
        
        startGame();

    }

    public void drawFrame() {
        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Set the camera position
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        //Draw Everything
        //background.draw(mMVPMatrix);
        mCrossbar.draw(mMVPMatrix);
        mHitboxs.draw(mMVPMatrix);
        if (gmee.getStartTime() > 0)
            drawNotes();

        //Detect Button Press
                /*int index = mHitboxs.contains(x, y);
        if (index != -1) {
            checkCollide(index);
            mHitboxs.setPressed(index, true);
        }*/

        //drawHitAnimation(mMVPMatrix);
        //hud.draw(mMVPMatrix, gmee.getCombo(), gmee.comboChanged());
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