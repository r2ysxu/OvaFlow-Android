package com.ovaflow.app.engine.mania.model.renderable.primitives;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;

import com.ovaflow.app.R;
import com.ovaflow.app.engine.mania.view.GameManiaGLRenderer;
import com.ovaflow.app.util.RawResourceReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by ArthurXu on 21/06/2014.
 */
public class TextureObject {

    protected final String vertexShaderCode;
    protected final String fragmentShaderCode;
    static final int COORDS_PER_VERTEX = 3;
    protected final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex
    protected int mProgram;

    protected FloatBuffer vertexBuffer;
    protected int mPositionHandle;
    protected int mMVPMatrixHandle;

    //Object Related
    private ShortBuffer indexBuffer;
    private final short drawOrder[] = {0, 1, 2, 0, 2, 3}; // order to draw vertices

    //Matrix Related
    protected float mWidth = 1.0f, mHeight = 1.0f;
    protected float mX = 0.0f, mY = 0.0f;

    //Texture Related
    private int mTextureHandle;
    private int mTextureDataHandle;
    private FloatBuffer mTextureCoordinates;
    private int mTextureUniformHandle;

    public static int loadTexture(Bitmap bitmap) {
        final int[] textureHandle = new int[1];

        GLES20.glGenTextures(1, textureHandle, 0);

        if (textureHandle[0] != 0) {
            // Bind to the texture in OpenGL
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);

            // Set filtering
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

            // Load the bitmap into the bound texture.
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        }
        if (textureHandle[0] == 0) {
            throw new RuntimeException("Error loading texture.");
        }
        return textureHandle[0];
    }

    public TextureObject(Context context) {
        vertexShaderCode = RawResourceReader.readTextFileFromRawResource(context, R.raw.vertex_shader_code);
        fragmentShaderCode = RawResourceReader.readTextFileFromRawResource(context, R.raw.fragment_shader_code);

        initializeBuffers();
        initializeTexture(context);
        getGLProgram(context);
    }

    protected void getGLProgram(Context context) {

        // Load vShader, and fShader
        int vertexShader = GameManiaGLRenderer.loadShader(
                GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = GameManiaGLRenderer.loadShader(
                GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);


        //mProgram = ShaderHelper.createAndLinkProgram(vertexShader, fragmentShader,
        //        new String[]{"a_Position", "a_Color", "a_Normal", "a_TexCoordinate"});

        //Attach GL, vShader and fShader Program
        mProgram = GLES20.glCreateProgram();

        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);

        //GLES20.glBindAttribLocation(mProgram, 0, "a_Position");
        //GLES20.glBindAttribLocation(mProgram, 0, "a_TexCoordinate");

        GLES20.glLinkProgram(mProgram);
    }

    private void initializeBuffers() {
        final float[] coords = {
                -0.1f, 0.1f, 0.0f,   // top left
                -0.1f, -0.1f, 0.0f,   // bottom left
                0.1f, -0.1f, 0.0f,   // bottom right
                0.1f, 0.1f, 0.0f}; // top right

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
    }

    private void initializeTexture(Context context) {
        float[] textureCoordinateData = {
                0f, 0f,
                0f, 1f,
                1f, 1f,
                1f, 0f};

        mTextureCoordinates = ByteBuffer.allocateDirect(textureCoordinateData.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mTextureCoordinates.put(textureCoordinateData).position(0);
        GameManiaGLRenderer.checkGlError("initTexture");
    }

    public int setTexture(Context context, final int resourceId) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;   // No pre-scaling=
        // Read in the resource
        final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);
        mTextureDataHandle = loadTexture(bitmap);
        bitmap.recycle();
        GameManiaGLRenderer.checkGlError("setTexture()");
        return mTextureDataHandle;
    }

    public void useTexture(int textureHandle) {
        mTextureDataHandle = textureHandle;
    }

    public void setTextTexture(Context context, String word, Typeface tf) {
        // Create an empty, mutable bitmap
        Bitmap bitmap = Bitmap.createBitmap(256, 32, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        bitmap.eraseColor(0); //Set Transparency
        Drawable background = context.getResources().getDrawable(R.drawable.text_bg);
        background.setBounds(0, 0, 256, 32);
        background.draw(canvas);

        canvas.scale(1f, -1f, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        canvas.rotate(180f, bitmap.getWidth() / 2, bitmap.getHeight() / 2);

        //Paint Text
        Paint textPaint = new Paint();
        textPaint.setTextSize(20);
        textPaint.setAntiAlias(true);
        textPaint.setARGB(0xff, 0x00, 0x00, 0x00);
        textPaint.setTypeface(tf);
        canvas.drawText(word, 10, 25, textPaint);

        //Load and Cleanup
        mTextureDataHandle = loadTexture(bitmap);
        bitmap.recycle();
    }

    public void scaleDim(float width, float height) {
        this.mWidth = width;
        this.mHeight = height;
    }

    public void setPosition(float x, float y) {
        this.mX = x / mWidth;
        this.mY = y / mHeight;
    }

    int drawCount = 0;

    public void draw(float[] mvpMatrix) {
        float[] mat = new float[16];
        GLES20.glUseProgram(mProgram);

        // Getting Attribute Handlers
        mTextureUniformHandle = GLES20.glGetUniformLocation(mProgram, "u_Texture");
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "a_Position");
        mTextureHandle = GLES20.glGetAttribLocation(mProgram, "a_TexCoordinate");
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "u_MVPMatrix");
        GameManiaGLRenderer.checkGlError("Obtaining Attribute Handlers " + drawCount);

        //Bind Texture
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureDataHandle);
        GLES20.glUniform1i(mTextureUniformHandle, 0);
        GameManiaGLRenderer.checkGlError("Binding Texture " + drawCount);

        // Vertext info
        vertexBuffer.position(0);
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GameManiaGLRenderer.checkGlError("Loading Vertex Info " + drawCount);

        mTextureCoordinates.position(0);
        GLES20.glVertexAttribPointer(mTextureHandle, 2, GLES20.GL_FLOAT, false, 0,
                mTextureCoordinates);
        GLES20.glEnableVertexAttribArray(mTextureHandle);
        GameManiaGLRenderer.checkGlError("Loading Texture Info " + drawCount);

        Matrix.setIdentityM(mat, 0);
        Matrix.scaleM(mat, 0, mWidth, mHeight, 0.0f);
        Matrix.translateM(mat, 0, mX, mY, 0.0f);
        Matrix.rotateM(mat, 0, 90f, 0f, 0f, 1f);

        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mat, 0);
        GameManiaGLRenderer.checkGlError("glUniformMatrix4fv");

        //Actual Drawing
        GLES20.glDrawElements(
                GLES20.GL_TRIANGLES, drawOrder.length,
                GLES20.GL_UNSIGNED_SHORT, indexBuffer);
        GameManiaGLRenderer.checkGlError("Drawing " + drawCount);

        // Disable vertex arrays
        GLES20.glDisableVertexAttribArray(mPositionHandle);
        //GLES20.glDisableVertexAttribArray(mTextureHandle);
        GameManiaGLRenderer.checkGlError("endDraw " + drawCount);
        drawCount++;
    }
}
