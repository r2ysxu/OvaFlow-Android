package com.ovaflow.app.engine.mania.model.renderable;

import android.content.Context;

import com.ovaflow.app.R;
import com.ovaflow.app.engine.mania.model.renderable.primitives.TextureObject;

/**
 * Created by ArthurXu on 13/06/2014.
 */
public class Crossbar extends TextureObject {

    public static final float HITRANGE = -0.7f;
    public static final float HEIGHT = 0.15f;

    public Crossbar(Context context) {
        super(context);
        scaleDim(HEIGHT, 7f);
        setPosition(-0.7f, 0.0f);
        setTexture(context, R.drawable.mania_crossbar);
    }
}
