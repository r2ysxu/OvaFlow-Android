package com.ovaflow.app.engine.mania.model.renderable.Clickables;

import com.ovaflow.app.engine.mania.model.renderable.primitives.Square;

/**
 * Created by ArthurXu on 13/06/2014.
 */
public class Hitbox extends Square {

    public static float WIDTHSCL = 1.0f;
    public static float HEIGHTSCL = 1.0f;
    public static float YPOS = -0.9f;

    public static final int MAXHITBOX = 5;

    private boolean[] pressed = new boolean[MAXHITBOX];

    private float[] downColor = {0.0f, 1.0f, 1.0f, 1.0f};
    private float[] upColor = {0.0f, 0.0f, 1.0f, 1.0f};

    public Hitbox() {
        scaleDim(WIDTHSCL, HEIGHTSCL);
    }

    public int contains(float x, float y) {
        float uX = -0.5f;

        for (int i = 0; i < MAXHITBOX; i++) {
            float left = (uX + HEIGHTSCL * coords[0]);
            float right = (uX + HEIGHTSCL * coords[6]);
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
            if (pressed[i])
                setColor(downColor);
            else
                setColor(upColor);
            setPosition(x, YPOS);
            super.draw(mMVPmatrix);
            x += 0.25;
        }
    }
}
