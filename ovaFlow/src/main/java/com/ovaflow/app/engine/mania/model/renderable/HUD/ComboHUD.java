package com.ovaflow.app.engine.mania.model.renderable.hud;

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
        comboB.setTextContext(Typeface.create("Helvetica",  Typeface.BOLD), 20, 0xff, 0xff, 0xff, 0xff);
        for (int i = 0; i < comboHandles.length; i ++) {
            comboHandles[i] = comboB.setTextTexture(context, i+"");
        }
    }

    public void draw(float[] mMVPmatrix) {
        float x = 0f, y = 0f;
        int score = comboPts;

        comboB.scaleDim(3f, 1f);

        while (score >= 1) {
            score = score / 10;
            x += 0.05f/2;
        }

        score = comboPts;

        while (score >= 1) {
            int digit = score % 10;
            score = score / 10;
            comboB.useTexture(comboHandles[digit]);
            comboB.setPosition(x, y);
            comboB.draw(mMVPmatrix);
            x -= 0.05f;
        }
    }

    public void setComboPts(int comboPts) {
        this.comboPts = comboPts;
    }
}
