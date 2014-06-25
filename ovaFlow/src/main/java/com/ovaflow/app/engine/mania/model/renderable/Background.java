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
        scaleDim(2f, 2f);
        setPosition(1f, -1f);
        setTexture(context, R.drawable.mania_background);
    }
}
