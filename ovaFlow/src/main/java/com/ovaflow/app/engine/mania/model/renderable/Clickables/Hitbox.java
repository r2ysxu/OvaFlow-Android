package com.ovaflow.app.engine.mania.model.renderable.clickables;

import android.content.Context;

import com.ovaflow.app.R;
import com.ovaflow.app.engine.mania.model.renderable.primitives.TextureObject;

/**
 * Created by ArthurXu on 13/06/2014.
 */
public class Hitbox extends TextureObject {

    private Context mActivityContext;

    public static float WIDTHSCL = 1.0f;
    public static float HEIGHTSCL = 1.0f;
    public static float YPOS = -0.9f;

    public static final int MAXHITBOX = 5;

    private boolean[] pressed = new boolean[MAXHITBOX];

    private float[] downColor = {0.0f, 1.0f, 1.0f, 1.0f};
    private float[] upColor = {0.0f, 0.0f, 1.0f, 1.0f};

    public Hitbox(Context context) {
        super(context);
        mActivityContext = context;
        scaleDim(WIDTHSCL, HEIGHTSCL);
    }

    public int contains(float x, float y) {
        float uX = -0.5f;

        for (int i = 0; i < MAXHITBOX; i++) {
            float left = (uX - HEIGHTSCL * 0.1f);
            float right = (uX + HEIGHTSCL * 0.1f);
            if (x >= left && x <= right) {
                //if (y >= mY && y <= (mY + HEIGHTSCL * coords[6])) {
                return i;
                //}
            }
            uX += 0.25;
        }
        return -1;
    }

    public void setPressed(int index, boolean val) {
        pressed[index] = val;
    }

    public void draw(float[] mMVPmatrix) {
        float x = -0.5f;

        for (int i = 0; i < MAXHITBOX; i++) {
            if (pressed[i]) {
                if (i == 2) setTexture(mActivityContext, R.drawable.hitbox_3_down);
                else if (i == 1 || i == 3) setTexture(mActivityContext, R.drawable.hitbox_2_down);
                else setTexture(mActivityContext, R.drawable.hitbox_1_down);
            } else {
                if (i == 2) setTexture(mActivityContext, R.drawable.hitbox_3_up);
                else if (i == 1 || i == 3) setTexture(mActivityContext, R.drawable.hitbox_2_up);
                else setTexture(mActivityContext, R.drawable.hitbox_1_up);
            }
            setPosition(x, YPOS);
            super.draw(mMVPmatrix);
            x += 0.25;
        }
    }
}
