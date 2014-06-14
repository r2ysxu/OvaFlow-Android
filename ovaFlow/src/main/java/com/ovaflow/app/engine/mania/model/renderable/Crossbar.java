package com.ovaflow.app.engine.mania.model.renderable;

import com.ovaflow.app.engine.mania.model.renderable.primitives.Square;

/**
 * Created by ArthurXu on 13/06/2014.
 */
public class Crossbar extends Square {

    public Crossbar() {
        float color[] = {1.0f, 0.0f, 0.0f, 0.0f};
        setColor(color);
        scaleDim(7f, 0.12f);
        setPosition(0.0f, -0.7f);
    }
}
