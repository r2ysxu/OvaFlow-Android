package com.ovaflow.app.engine.mania.model.renderable;

import android.content.Context;
import android.graphics.Typeface;

import com.ovaflow.app.engine.mania.model.renderable.primitives.TextureObject;

/**
 * Created by ArthurXu on 22/06/2014.
 */
public class HUD {

    private Context mActivityContext;

    private TextureObject score;
    private TextureObject combo;
    private ScoreSplash scoreSplash;

    private long timer;

    public HUD(Context context) {
        mActivityContext = context;
        initScoreHUD(context);
        initComboHUD(context);

        scoreSplash = new ScoreSplash(mActivityContext);
    }

    private void initScoreHUD(Context context) {
        score = new TextureObject(context);
        Typeface tf = Typeface.create("Helvetica", Typeface.BOLD);
        score.setTextTexture(mActivityContext, "Score: 0", tf);
        score.scaleDim(1f, 3f);
        score.setPosition(0.9f, 0.7f);
    }

    private void initComboHUD(Context context) {
        combo = new TextureObject(context);
        Typeface tf = Typeface.create("Helvetica", Typeface.BOLD);
        combo.setTextTexture(mActivityContext, "0", tf);
        combo.scaleDim(1f, 3f);
        combo.setPosition(0f, -0.24f);
    }

    public void draw(float[] mMVPmatrix, int comboNum, boolean comboFlag) {
        score.draw(mMVPmatrix);
        if (comboFlag) {
            Typeface tf = Typeface.create("Helvetica", Typeface.BOLD);
            combo.setTextTexture(mActivityContext, "" + comboNum, tf);
        }
        //if (comboNum > 0) {
        combo.draw(mMVPmatrix);
        //}
    }

    public void startSplash() {
        timer = 500;
    }

    public void drawHitSplash(float[] mMVPmatrix, int pts) {
        if (pts > 0 || timer > 0)
            //scoreSplash.draw(mMVPmatrix, pts);
        if (timer > 0)
            timer -= 10;
    }

    public void updateScore(String scoreStr) {
        Typeface tf = Typeface.create("Helvetica",Typeface.BOLD);
        score.setTextTexture(mActivityContext, scoreStr, tf);
    }
}
