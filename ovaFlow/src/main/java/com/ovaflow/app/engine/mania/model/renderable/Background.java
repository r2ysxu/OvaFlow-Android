package com.ovaflow.app.engine.mania.model.renderable;

import android.content.Context;

import com.ovaflow.app.R;
import com.ovaflow.app.engine.mania.model.renderable.primitives.TextureObject;

/**
 * Created by ArthurXu on 24/06/2014.
 */
public class Background extends TextureObject {

    public Background(Context context) {
        super(context);
        scaleDim(6f, 6f);
        setPosition(0f, 0f);
        setTexture(context, R.drawable.mania_background);
    }
}
