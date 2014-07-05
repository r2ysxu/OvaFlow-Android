package com.ovaflow.app.engine.mania.controller;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by ArthurXu on 09/06/2014.
 */
public class GameManiaController {

    public static final int MAXMULTIPLIER = 3;
    public static final int COMBOMULTREQ = 5;

    private long startTime;
    private long endTime;
    private int score;
    private int combo = 0;
    private int multiplier = 1;

    private int scoreChanged;

    private MediaPlayer player;

    private int songId;
    private String songFileName = "we_will_rock_you.mp3";

    public GameManiaController() {
    }

    public void startGame(int songId) {
        this.songId = songId;
        startTime = System.currentTimeMillis();
        score = 0;
    }

    public void playMusic(Context context) {
        try {
            AssetFileDescriptor afd = context.getAssets().openFd(songFileName);
            player = new MediaPlayer();
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.prepare();
            player.start();
            endTime = player.getDuration();
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

    public int addScore(int pts, int cbs) {
        score += pts * multiplier;
        combo += cbs;
        if (pts > 0) {
            if ((combo / COMBOMULTREQ) < MAXMULTIPLIER) {
                multiplier = (int) Math.pow(2, combo / COMBOMULTREQ);
            }
        }
        if (pts > 0)
            scoreChanged = pts;
        return score;
    }

    public void missedNote() {
        combo = 0;
        multiplier = 1;
    }

    public int scoreChanged() {
        int ret = scoreChanged;
        scoreChanged = 0;
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

    public boolean songEnded() {
        return System.currentTimeMillis() - startTime > endTime + 1000;
    }
}
