package com.ovaflow.app.engine.mania.model.renderable.HUD;

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
        scoreHandles[0] = scoreB.setTextTexture(context, "0", Typeface.create("Helvetica", Typeface.BOLD));
        scoreHandles[1] = scoreB.setTextTexture(context, "1", Typeface.create("Helvetica", Typeface.BOLD));
        scoreHandles[2] = scoreB.setTextTexture(context, "2", Typeface.create("Helvetica", Typeface.BOLD));
        scoreHandles[3] = scoreB.setTextTexture(context, "3", Typeface.create("Helvetica", Typeface.BOLD));
        scoreHandles[4] = scoreB.setTextTexture(context, "4", Typeface.create("Helvetica", Typeface.BOLD));
        scoreHandles[5] = scoreB.setTextTexture(context, "5", Typeface.create("Helvetica", Typeface.BOLD));
        scoreHandles[6] = scoreB.setTextTexture(context, "6", Typeface.create("Helvetica", Typeface.BOLD));
        scoreHandles[7] = scoreB.setTextTexture(context, "7", Typeface.create("Helvetica", Typeface.BOLD));
        scoreHandles[8] = scoreB.setTextTexture(context, "8", Typeface.create("Helvetica", Typeface.BOLD));
        scoreHandles[9] = scoreB.setTextTexture(context, "9", Typeface.create("Helvetica", Typeface.BOLD));
        scoreHandles[10] = scoreB.setTextTexture(context, "Score", Typeface.create("Helvetica", Typeface.BOLD));
    }

    public void draw(float[] mMVPmatrix) {
        float x = 0.9f, y = 0.7f;
        int score = scorePts;

        scoreB.scaleDim(1f, 3f);
        scoreB.useTexture(scoreHandles[10]);
        scoreB.setPosition(x, y);
        scoreB.draw(mMVPmatrix);
        y = y - 0.25f;
        while (score >= 1) {
            score = score / 10;
            y -= 0.05f;
        }

        score = scorePts;

        while (score >= 1) {
            int digit = score % 10;
            score = score / 10;
            scoreB.useTexture(scoreHandles[digit]);
            scoreB.setPosition(x, y);
            scoreB.draw(mMVPmatrix);
            y += 0.05f;
        }
    }

    public void setScorePts(int scorePts) {
        this.scorePts = scorePts;
    }
}
