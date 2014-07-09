package com.ovaflow.app.engine.mania.model.renderable.hud;

import android.content.Context;
import android.graphics.Typeface;

import com.ovaflow.app.engine.mania.model.renderable.primitives.TextureObject;

/**
 * Created by ArthurXu on 02/07/2014.
 */
public class ScoreHUD {

    private TextureObject scoreB;
    private int[] scoreHandles;
    private int scorePts;

    public ScoreHUD(Context context) {
        scoreB = new TextureObject(context);
        scoreHandles = new int[11];
        initScoreboard(context);
    }

    private void initScoreboard(Context context) {
        scoreB.setTextContext(Typeface.create("Helvetica",  Typeface.BOLD), 20, 0xff, 0xff, 0xff, 0xff);
        for (int i = 0; i < scoreHandles.length -1; i ++) {
            scoreHandles[i] = scoreB.setTextTexture(context, i+"");
        }
        scoreHandles[10] = scoreB.setTextTexture(context, "Score: ");
    }

    public void draw(float[] mMVPmatrix) {
        float x = -0.7f, y = 0.9f;
        int score = scorePts;

        scoreB.scaleDim(2f, 1f);
        scoreB.useTexture(scoreHandles[10]);
        scoreB.setPosition(x, y);
        scoreB.draw(mMVPmatrix);
        x = x + 0.25f;
        while (score >= 1) {
            score = score / 10;
            x += 0.05f;
        }

        score = scorePts;

        while (score >= 1) {
            int digit = score % 10;
            score = score / 10;
            scoreB.useTexture(scoreHandles[digit]);
            scoreB.setPosition(x, y);
            scoreB.draw(mMVPmatrix);
            x -= 0.05f;
        }
    }

    public void setScorePts(int scorePts) {
        this.scorePts = scorePts;
    }
}
