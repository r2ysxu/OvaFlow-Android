package com.ovaflow.app.engine.mania.model.renderable;

import android.content.Context;

import com.ovaflow.app.R;
import com.ovaflow.app.engine.mania.model.renderable.primitives.TextureObject;

/**
 * Created by ArthurXu on 24/06/2014.
 */
public class ScoreSplash extends TextureObject {

    public static final int PERFECT = 100;
    public static final int GOOD = 50;
    public static final int BAD = 30;
    public static final int MISS = 0;

    private final int highTextureHandle;
    private final int medTextureHandle;
    private final int lowTextureHandle;
    private final int missTextureHandle;

    public ScoreSplash(Context context) {
        super(context);
        scaleDim(0.5f, 1f);
        setPosition(-0.05f, -0.5f);
        highTextureHandle = setTexture(context, R.drawable.smiley2);
        medTextureHandle = setTexture(context, R.drawable.smiley2);
        lowTextureHandle = setTexture(context, R.drawable.smiley2);
        missTextureHandle = setTexture(context, R.drawable.smiley2);
    }

    public void draw(float[] mMVPmatrix, int performance) {
        int textureHandle;
        switch (performance) {
            case PERFECT:
                textureHandle = highTextureHandle;
                break;
            case GOOD:
                textureHandle = medTextureHandle;
                break;
            case BAD:
                textureHandle = lowTextureHandle;
                break;
            default:
                textureHandle = missTextureHandle;
                break;
        }
        useTexture(textureHandle);
        //super.draw(mMVPmatrix);
    }
}
