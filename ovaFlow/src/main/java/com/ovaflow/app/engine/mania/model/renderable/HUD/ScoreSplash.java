package com.ovaflow.app.engine.mania.model.renderable.hud;

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
    public static final int MISS = -1;

    private final int highTextureHandle;
    private final int medTextureHandle;
    private final int lowTextureHandle;
    private final int missTextureHandle;

    private int textureHandle;

    public ScoreSplash(Context context) {
        super(context);
        scaleDim(3f, 1f);
        setPosition(-0.05f, -0.5f);
        highTextureHandle = setTexture(R.drawable.splash_cool);
        medTextureHandle = setTexture(R.drawable.splash_good);
        lowTextureHandle = setTexture(R.drawable.splash_bad);
        missTextureHandle = setTexture(R.drawable.splash_miss);
    }

    public void setSplashType(int performance) {
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
            case MISS:
                textureHandle = missTextureHandle;
                break;
            default:
                textureHandle = -1;
                break;
        }
    }

    public void draw(float[] mMVPmatrix) {
        if (textureHandle >= 0) {
            useTexture(textureHandle);
            super.draw(mMVPmatrix);
        }
    }
}
