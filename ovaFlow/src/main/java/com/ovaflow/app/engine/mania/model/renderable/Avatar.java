package com.ovaflow.app.engine.mania.model.renderable;

import android.content.Context;

import com.ovaflow.app.R;
import com.ovaflow.app.engine.mania.model.renderable.primitives.TextureObject;

/**
 * Created by ArthurXu on 13/07/2014.
 */
public class Avatar extends TextureObject {

    private static final int REFRESHRATE = 500;

    private long refreshTime;
    private int[] spriteMappings = {R.drawable.sprite001_0_0, R.drawable.sprite001_0_1, R.drawable.sprite001_1, R.drawable.sprite001_2, R.drawable.sprite001_3, R.drawable.sprite001_4};
    private int state;

    public Avatar(Context context) {
        super(context);
        state = 0;
        float x = 0.6f, y = 0.6f;
        scaleDim(4f, 4f);
        setPosition(x, y);
        setTexture(spriteMappings[state]);
    }

    public Avatar(Context context, int id) {
        this(context);
        setAvatar(id);
    }

    private int[] spriteSets(int id) {
        int[] sprite1 = {R.drawable.sprite001_0_0, R.drawable.sprite001_0_1, R.drawable.sprite001_1, R.drawable.sprite001_2, R.drawable.sprite001_3, R.drawable.sprite001_4};
        int[] sprite2 = {R.drawable.sprite002_0_0, R.drawable.sprite002_0_1, R.drawable.sprite002_1, R.drawable.sprite002_2, R.drawable.sprite002_3, R.drawable.sprite002_4};
        switch (id) {
            case 1: return sprite1;
            case 2: return sprite2;
        }
        return spriteMappings;
    }

    public void setAvatar(int id) {
        spriteMappings = spriteSets(id);
    }

    public void setPose(int pts) {
        switch (pts) {
            case 100:
                setTexture(spriteMappings[2]);
                break;
            case 50:
                setTexture(spriteMappings[3]);
                break;
            case 30:
                setTexture(spriteMappings[4]);
                break;
            case -1:
                setTexture(spriteMappings[5]);
                break;
        }
    }

    public void draw(float[] m) {
        super.draw(m);
        long currentTime = System.currentTimeMillis();
        if (currentTime - refreshTime > REFRESHRATE) {
            state = (state + 1) % 2;
            setTexture(spriteMappings[state]);
            refreshTime = currentTime;
        }
    }
}
