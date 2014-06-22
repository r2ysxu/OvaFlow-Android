package com.ovaflow.app.engine.mania.model.renderable.primitives;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.ovaflow.app.engine.mania.view.GameManiaGLRenderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

/**
 * Simple Square
 */
public class Square extends RenderableObject {

    private ShortBuffer indexBuffer;
    private final short drawOrder[] = {0, 1, 2, 0, 2, 3}; // order to draw vertices

    protected float color[] = {0.63671875f, 0.76953125f, 0.22265625f, 0.0f};
    protected float[] matrix = new float[16];
    protected float mWidth = 1.0f, mHeight = 1.0f;
    protected float mX, mY;
    protected final float[] coords = {
            -0.1f, 0.1f, 0.0f,   // top left
            -0.1f, -0.1f, 0.0f,   // bottom left
            0.1f, -0.1f, 0.0f,   // bottom right
            0.1f, 0.1f, 0.0f}; // top right

    public Square() {
        initializeGL();
    }

    private void initializeGL() {
        ByteBuffer bb = ByteBuffer.allocateDirect(
                coords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(coords);
        vertexBuffer.position(0);

        ByteBuffer dlb = ByteBuffer.allocateDirect(
                drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        indexBuffer = dlb.asShortBuffer();
        indexBuffer.put(drawOrder);
        indexBuffer.position(0);

        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(coords);
        vertexBuffer.position(0);

        getGLProgram();
    }

    public void setColor(float[] color) {
        this.color = color;
    }

    public void scaleDim(float width, float height) {
        this.mWidth = width;
        this.mHeight = height;
    }

    public void setPosition(float x, float y) {
        this.mX = x / mWidth;
        this.mY = y / mHeight;
    }

    @Override
    public void draw(final float[] mvpMatrix) {
        super.initDraw();
        float[] mat = new float[16];

        //System.arraycopy( mvpMatrix, 0, mat, 0, mvpMatrix.length );

        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        GameManiaGLRenderer.checkGlError("glGetUniformLocation");

        Matrix.setIdentityM(mat, 0);
        Matrix.scaleM(mat, 0, mWidth, mHeight, 0.0f);
        Matrix.translateM(mat, 0, mX, mY, 0.0f);


        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mat, 0);
        GameManiaGLRenderer.checkGlError("glUniformMatrix4fv");

        //Actual Drawing
        GLES20.glDrawElements(
                GLES20.GL_TRIANGLES, drawOrder.length,
                GLES20.GL_UNSIGNED_SHORT, indexBuffer);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}