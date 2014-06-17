package com.ovaflow.app.engine.mania.model.renderable;

import com.ovaflow.app.engine.mania.model.renderable.primitives.Square;

/**
 * Created by ArthurXu on 13/06/2014.
 */
public class Hitbox extends Square {

    public static float WIDTH = 1.0f;
    public static float HEIGHT = 1.0f;
    public static float YPOS = -0.7f;

    public Hitbox(int index) {
        float color[] = {0.0f, 0.0f, 1.0f, 0.0f};
        setColor(color);
        scaleDim(WIDTH, HEIGHT);
        determinePosition(index);
    }

    private void determinePosition(int index) {
        float x = 0.0f;

        switch (index) {
            case 0:
                x = -0.5f;
                break;
            case 1:
                x = -0.25f;
                break;
            case 2:
                x = 0;
                break;
            case 3:
                x = 0.25f;
                break;
            case 4:
                x = 0.5f;
                break;
        }
        setPosition(x, YPOS);
    }
}
