package com.ovaflow.app.engine.mania.view;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import com.ovaflow.app.engine.mania.model.renderable.Crossbar;
import com.ovaflow.app.engine.mania.model.renderable.Hitbox;
import com.ovaflow.app.engine.mania.model.renderable.Note;
import com.ovaflow.app.engine.mania.model.renderable.Slider;

import java.util.HashMap;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * GL Renderer for the Mania game mode
 */
public class GameManiaGLRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "GameManiaGLRenderer";
    public static final int MAXHITBOX = 5;

    private HashMap<Integer, List<Note>> notes;
    private Slider mSlider;
    private Crossbar mCrossbar;
    private Hitbox[] mHitboxs = new Hitbox[MAXHITBOX];

    private final float[] mViewMatrix = new float[16];
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];

    private long startTime;

    /**
     * Initializes all components used when the frame is created
     *
     * @param unused
     * @param config
     */
    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        //Initialize Objects
        mSlider = new Slider();
        mCrossbar = new Crossbar();
        notes = Note.generateNotes();
        for (int i = 0; i < MAXHITBOX; i++) {
            mHitboxs[i] = new Hitbox(i);
        }
    }

    @Override
    public void onDrawFrame(GL10 unused) {

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
        notes.get(100).get(0).draw(mMVPMatrix);
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

    public void buttonPressed(int index) {
        float[] color = {0.0f, 1.0f, 1.0f, 1.0f};
        mHitboxs[index].setColor(color);
    }

    public void buttonReleased(int index) {
        float color[] = {0.0f, 0.0f, 1.0f, 0.0f};
        mHitboxs[index].setColor(color);
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
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }
}