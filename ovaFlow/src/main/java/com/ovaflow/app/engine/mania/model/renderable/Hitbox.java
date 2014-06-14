package com.ovaflow.app.engine.mania.model.renderable;

import com.ovaflow.app.engine.mania.model.renderable.primitives.Square;

/**
 * Created by ArthurXu on 13/06/2014.
 */
public class Hitbox extends Square {

    public Hitbox(int index) {
        float color[] = {0.0f, 0.0f, 1.0f, 0.0f};
        setColor(color);
        scaleDim(1f, 1f);
        determinePosition(index);
    }

    private void determinePosition(int index) {
        float x = 0.0f, y = -0.7f;

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
        setPosition(x, y);
    }
}
