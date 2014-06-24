package com.ovaflow.app.engine.mania.controller;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

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

    private boolean scoreChanged = true;
    private boolean comboChanged = true;

    private MediaPlayer player;

    public GameManiaController() {
        startGame();
    }

    public void startGame() {
        startTime = System.currentTimeMillis();
        score = 0;
    }

    public void playMusic(Context context) {
        try {
            AssetFileDescriptor afd = context.getAssets().openFd("we_will_rock_you.mp3");
            player = new MediaPlayer();
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.prepare();
            //player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pauseMusic() {
        player.pause();
    }

    public void resumeMusic() {
        player.start();
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
        if (pts > 0)
            scoreChanged = true;
        comboChanged = true;
        return score;
    }

    public boolean scoreChanged() {
        boolean ret = scoreChanged;
        scoreChanged = false;
        return ret;
    }

    public boolean comboChanged() {
        boolean ret = comboChanged;
        comboChanged = false;
        return ret;
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
