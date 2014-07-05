package com.ovaflow.app.engine.mania.model.renderable.Clickables;

import android.content.Context;

import com.ovaflow.app.engine.mania.model.data.ButtonInfo;
import com.ovaflow.app.engine.mania.model.renderable.primitives.TextureObject;

/**
 * Created by ArthurXu on 04/07/2014.
 */
public class Buttons extends TextureObject {

    private ButtonInfo[] buttonInfos;
    private Context mActivityContext;

    private static final float c = 0.1f;

    public Buttons(Context context, ButtonInfo[] buttonInfos) {
        super(context);
        this.mActivityContext = context;
        this.buttonInfos = buttonInfos;
    }

    public int contains(float x, float y) {
        int i = 0;

        for (ButtonInfo n : buttonInfos) {
            float top = (n.getPosY() - n.getScaleY() * c);
            float bottom = (n.getPosY() + n.getScaleY() * c);
            float left = (n.getPosX() - n.getScaleX() * c);
            float right = (n.getPosX() + n.getScaleX() * c);
            if (y >= top && y <= bottom) {
                //if (y >= mY && y <= (mY + HEIGHTSCL * coords[6])) {
                return i;
                //}
            }
            i++;
        }
        return -1;
    }

    public void draw(float[] mMVPmatrix) {
        for (ButtonInfo n : buttonInfos) {
            scaleDim(n.getScaleX(), n.getScaleY());
            setPosition(n.getPosX(), n.getPosY());
            setTexture(mActivityContext, n.getImageId());
            super.draw(mMVPmatrix);
        }
    }
}