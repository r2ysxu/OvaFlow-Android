package com.ovaflow.app.engine.mania.controller;

import android.util.Log;

/**
 * Created by ArthurXu on 09/06/2014.
 */
public class GameManiaController {

    public static final int MAXMULTIPLIER = 3;
    public static final int COMBOMULTREQ = 5;

    private long startTime;
    private int score;
    private int combo = 0;
    private int multiplier = 1;

    public GameManiaController() {
        startGame();
    }

    public void startGame() {
        startTime = System.currentTimeMillis();
        score = 0;
    }

    public long getStartTime() {
        return startTime;
    }

    public int addScore(int pts) {
        score += pts * multiplier;
        if (pts > 0) {
            combo++;
            if ((combo / COMBOMULTREQ) < MAXMULTIPLIER) {
                multiplier = (int) Math.pow(2, combo / COMBOMULTREQ);
            }
        } else {
            combo = 0;
            multiplier = 1;
        }
        Log.i("Score", "Score: " + score);
        return score;
    }

    public int getCombo() {
        return combo;
    }

    public int getScore() {
        return score;
    }

    public int getMultiplier() {
        return multiplier;
    }
}
