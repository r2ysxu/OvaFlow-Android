package com.ovaflow.app.engine.mania.model.renderable;

import android.util.Log;

import com.ovaflow.app.engine.mania.model.renderable.primitives.Square;

/**
 * Created by ArthurXu on 13/06/2014.
 */
public class Hitbox extends Square {

    public static float WIDTHSCL = 1.0f;
    public static float HEIGHTSCL = 1.0f;
    public static float XPOS = -0.9f;

    public Hitbox(int index) {
        float color[] = {0.0f, 0.0f, 1.0f, 0.0f};
        setColor(color);
        scaleDim(WIDTHSCL, HEIGHTSCL);
        determinePosition(index);
    }

    public boolean contains(float x, float y) {
        float top = (mY + HEIGHTSCL * coords[0]);
        float bottom = (mY + HEIGHTSCL * coords[6]);
        Log.i("Contains", " [" + top + ", " + bottom +"]");
        if (y >= top && y <= bottom) {
            //if (y >= mY && y <= (mY + HEIGHTSCL * coords[6])) {
                return true;
            //}
        }

        return false;
    }

    private void determinePosition(int index) {
        float y = 0.0f;

        switch (index) {
            case 0:
                y = -0.5f;
                break;
            case 1:
                y = -0.25f;
                break;
            case 2:
                y = 0;
                break;
            case 3:
                y = 0.25f;
                break;
            case 4:
                y = 0.5f;
                break;
        }
        setPosition(XPOS, y);
    }
}
