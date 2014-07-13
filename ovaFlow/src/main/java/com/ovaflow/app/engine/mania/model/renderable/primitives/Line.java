package com.ovaflow.app.engine.mania.model.renderable.primitives;

import android.opengl.GLES20;

import com.ovaflow.app.util.GameManiaGLUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by ArthurXu on 13/06/2014.
 */
public class Line extends RenderableObject {

    // number of coordinates per vertex in this array

    // Set color with red, green, blue and alpha (opacity) values
    float color[] = {0.0f, 0.0f, 0.0f, 1.0f};

    public Line() {
        initializeGL();
    }

    private void initializeGL() {
        float coords[] = {
                0.0f, 0.0f, 0.0f,
                1.0f, 0.0f, 0.0f
        };
        ByteBuffer bb = ByteBuffer.allocateDirect(
                coords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(coords);
        vertexBuffer.position(0);
    }

    @Override
    public void draw(float[] mvpMatrix) {
        super.initDraw();

        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        GameManiaGLUtil.checkGlError("glGetUniformLocation");
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        GameManiaGLUtil.checkGlError("glUniformMatrix4fv");
        GLES20.glDrawArrays(GLES20.GL_LINES, 0, 6 / COORDS_PER_VERTEX);
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}