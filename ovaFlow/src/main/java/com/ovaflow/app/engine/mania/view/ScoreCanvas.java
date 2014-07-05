package com.ovaflow.app.engine.mania.view;

import android.content.Context;

import com.ovaflow.app.engine.mania.controller.GameManiaController;
import com.ovaflow.app.engine.mania.model.renderable.HUD.numberHUD;
import com.ovaflow.app.engine.mania.model.renderable.primitives.TextureObject;

/**
 * Created by ArthurXu on 05/07/2014.
 */
public class ScoreCanvas {
    private Context mActivityContext;
    private GameManiaController gmee;

    private TextureObject background;
    private numberHUD scoreHUD;
    private numberHUD comboHUD;

    public ScoreCanvas(Context context, GameManiaController gmee) {
        mActivityContext = context;
        this.gmee = gmee;
    }

    public void onSurfaceCreated() {
        background = new TextureObject(mActivityContext);
        scoreHUD = new numberHUD(mActivityContext);
        comboHUD = new numberHUD(mActivityContext);
    }

    public void draw(float[] mMVPmatrix) {
        scoreHUD.draw();
    }
}
