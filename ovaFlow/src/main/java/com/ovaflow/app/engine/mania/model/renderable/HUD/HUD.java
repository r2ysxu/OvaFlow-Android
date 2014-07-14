package com.ovaflow.app.engine.mania.model.renderable.hud;

import android.content.Context;

/**
 * Created by ArthurXu on 22/06/2014.
 */
public class HUD {

    private Context mActivityContext;

    private ScoreHUD score;
    private ComboHUD combo;
    private ScoreSplash scoreSplash;

    private long timer;

    public HUD(Context context) {
        mActivityContext = context;
        initHUD(context);

        scoreSplash = new ScoreSplash(mActivityContext);
    }

    private void initHUD(Context context) {
        score = new ScoreHUD(context);
        combo = new ComboHUD(context);
    }

    public void draw(float[] mMVPmatrix, int comboNum) {
        score.draw(mMVPmatrix);
        combo.setComboPts(comboNum);
        if (comboNum > 0) {
            combo.draw(mMVPmatrix);
        }
    }

    public void startSplash(int pts) {
        timer = 500;
        scoreSplash.setSplashType(pts);
    }

    public void drawHitSplash(float[] mMVPmatrix, int pts) {
        if (pts > 0 || timer > 0)
            scoreSplash.draw(mMVPmatrix);
        if (timer > 0)
            timer -= 10;

    }

    public void updateScore(int scorePts) {
        score.setScorePts(scorePts);
    }
}
