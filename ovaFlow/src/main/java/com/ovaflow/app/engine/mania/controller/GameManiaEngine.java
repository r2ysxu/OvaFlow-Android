package com.ovaflow.app.engine.mania.controller;

/**
 * Created by ArthurXu on 09/06/2014.
 */
public class GameManiaEngine {

    private long startTime;
    private int score;

    public GameManiaEngine() {
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
        score += pts;
        return score;
    }
}
