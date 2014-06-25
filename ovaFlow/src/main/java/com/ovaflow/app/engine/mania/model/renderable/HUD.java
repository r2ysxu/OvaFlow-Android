package com.ovaflow.app.engine.mania.model.renderable;

import android.content.Context;

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
        score.setTextTexture(mActivityContext, "Score: 0");
        score.scaleDim(0.1f, 1f);
        score.setPosition(1f, 0f);
    }

    private void initComboHUD(Context context) {
        combo = new TextureObject(context);
        combo.setTextTexture(mActivityContext, "0");
        combo.scaleDim(0.1f, 1f);
        combo.setPosition(0f, -1f);
    }

    public void draw(float[] mMVPmatrix, int comboNum, boolean comboFlag) {
        score.draw(mMVPmatrix);
        if (comboFlag)
            combo.setTextTexture(mActivityContext, "" + comboNum);
        //if (comboNum > 0) {
        combo.draw(mMVPmatrix);
        //}
    }

    public void startSplash() {
        timer = 500;
    }

    public void drawHitSplash(float[] mMVPmatrix, int pts) {
        if (pts > 0 || timer > 0)
            scoreSplash.draw(mMVPmatrix, pts);
        if (timer > 0)
            timer -= 10;
    }

    public void updateScore(String scoreStr) {
        score.setTextTexture(mActivityContext, scoreStr);
    }
}
