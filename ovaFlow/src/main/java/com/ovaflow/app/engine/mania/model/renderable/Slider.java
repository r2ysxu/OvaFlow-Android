package com.ovaflow.app.engine.mania.model.renderable;

import com.ovaflow.app.engine.mania.model.renderable.primitives.Square;

/**
 * Created by ArthurXu on 12/06/2014.
 */
public class Slider extends Square {

    public Slider() {
        float color[] = {0.0f, 0.0f, 0.0f, 0.0f};
        setColor(color);
        scaleDim(7f, 6f);
    }

    public float getSliderWidth() {
        return mWidth * coords[6];
    }
}
