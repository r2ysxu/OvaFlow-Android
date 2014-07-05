package com.ovaflow.app.engine.mania.model.renderable;

import android.content.Context;

import com.ovaflow.app.engine.mania.model.renderable.primitives.TextureObject;

/**
 * Created by ArthurXu on 24/06/2014.
 */
public class Background extends TextureObject {

    public Background(Context context, int resourceId) {
        super(context);
        scaleDim(10f, 10f);
        setPosition(0f, 0f);
        setTexture(context, resourceId);
    }
}
