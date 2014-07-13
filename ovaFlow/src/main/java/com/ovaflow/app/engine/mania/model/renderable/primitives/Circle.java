package com.ovaflow.app.engine.mania.model.renderable.primitives;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.ovaflow.app.util.GameManiaGLUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Rendered Object representing a Circle
 */
public class Circle extends RenderableObject {

    private int VERTICES = 360;
    private float color[] = {0.0f, 0.0f, 0.0f, 0.0f};

    public Circle() {
        initalizeGL();
    }

    private void initalizeGL() {
        float coords[] = new float[VERTICES * 3];
        float theta = 0;

        for (int i = 0; i < VERTICES * 3; i += 3) {
            coords[i] = (float) Math.cos(theta);
            coords[i + 1] = (float) Math.sin(theta);
            coords[i + 2] = 0.0f;
            theta += Math.PI / 90;
        }

        ByteBuffer bb = ByteBuffer.allocateDirect(coords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(coords);
        vertexBuffer.position(0);

        getGLProgram();
    }

    @Override
    public void draw(float[] mvpMatrix) {
        super.initDraw();
        float[] mMatrix = new float[16];

        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        GameManiaGLUtil.checkGlError("glGetUniformLocation");

        Matrix.translateM(mMatrix, 0, mvpMatrix, 0, 0.0f, -0.8f, 0.0f);
        Matrix.scaleM(mMatrix, 0, 1.2f, 1.2f, 1.0f);

        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMatrix, 0);
        GameManiaGLUtil.checkGlError("glUniformMatrix4fv");

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, VERTICES);

        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}