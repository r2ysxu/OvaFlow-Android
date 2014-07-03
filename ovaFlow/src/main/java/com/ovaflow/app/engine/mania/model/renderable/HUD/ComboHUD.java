package com.ovaflow.app.engine.mania.model.renderable.HUD;

import android.content.Context;
import android.graphics.Typeface;

import com.ovaflow.app.engine.mania.model.renderable.primitives.TextureObject;

/**
 * Created by ArthurXu on 02/07/2014.
 */
public class ComboHUD {

    private TextureObject comboB;
    private int[] comboHandles;
    private int comboPts;

    public ComboHUD(Context context) {
        comboB = new TextureObject(context);
        comboHandles = new int[10];
        initScoreboard(context);
    }

    private void initScoreboard(Context context) {
        comboHandles[0] = comboB.setTextTexture(context, "0", Typeface.create("Helvetica", Typeface.BOLD));
        comboHandles[1] = comboB.setTextTexture(context, "1", Typeface.create("Helvetica", Typeface.BOLD));
        comboHandles[2] = comboB.setTextTexture(context, "2", Typeface.create("Helvetica", Typeface.BOLD));
        comboHandles[3] = comboB.setTextTexture(context, "3", Typeface.create("Helvetica", Typeface.BOLD));
        comboHandles[4] = comboB.setTextTexture(context, "4", Typeface.create("Helvetica", Typeface.BOLD));
        comboHandles[5] = comboB.setTextTexture(context, "5", Typeface.create("Helvetica", Typeface.BOLD));
        comboHandles[6] = comboB.setTextTexture(context, "6", Typeface.create("Helvetica", Typeface.BOLD));
        comboHandles[7] = comboB.setTextTexture(context, "7", Typeface.create("Helvetica", Typeface.BOLD));
        comboHandles[8] = comboB.setTextTexture(context, "8", Typeface.create("Helvetica", Typeface.BOLD));
        comboHandles[9] = comboB.setTextTexture(context, "9", Typeface.create("Helvetica", Typeface.BOLD));
    }

    public void draw(float[] mMVPmatrix) {
        float x = 0f, y = -0.24f;
        int score = comboPts;

        comboB.scaleDim(1f, 3f);

        while (score >= 1) {
            score = score / 10;
            y -= 0.05f/2;
        }

        score = comboPts;

        while (score >= 1) {
            int digit = score % 10;
            score = score / 10;
            comboB.useTexture(comboHandles[digit]);
            comboB.setPosition(x, y);
            comboB.draw(mMVPmatrix);
            y += 0.05f;
        }
    }

    public void setComboPts(int comboPts) {
        this.comboPts = comboPts;
    }
}
