package com.ovaflow.app.engine.mania.controller;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import com.ovaflow.app.engine.mania.model.data.ScoreType;

import java.io.IOException;

/**
 * Created by ArthurXu on 09/06/2014.
 */
public class GameManiaController {

    public static final int MAXMULTIPLIER = 3;
    public static final int COMBOMULTREQ = 5;

    private long startTime;
    private long endTime = Long.MAX_VALUE;
    private int combo = 0;
    private int multiplier = 1;

    private ScoreType scoreType;
    private int scoreChanged;
    private MediaPlayer player;

    private boolean playing = false;

    private int songId;
    private String songFileName = "we_will_rock_you.mp3";

    public GameManiaController() {
        scoreType = new ScoreType();
    }

    public void startGame(int songId) {
        playing = true;
        this.songId = songId;
        startTime = System.currentTimeMillis();
    }

    public void playMusic(Context context) {
        try {
            AssetFileDescriptor afd = context.getAssets().openFd(songFileName);
            player = new MediaPlayer();
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.prepare();
            player.start();
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    playing = false;
                }
            });
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

    public long getTime() {
        return player.getCurrentPosition();
    }

    public int addScore(ScoreType st) {
        int pts = st.getPts() * multiplier;

        scoreType.add(st);
        scoreType.addScore(pts);
        combo += st.getCombo();
        st.resetCombo();
        if (st.getPts() > 0) {
            if ((combo / COMBOMULTREQ) < MAXMULTIPLIER) {
                multiplier = (int) Math.pow(2, combo / COMBOMULTREQ);
            }
        }
        scoreChanged = st.getPts();
        return pts;
    }

    public void missedNote() {
        combo = 0;
        multiplier = 1;
        scoreChanged();
        scoreType.addPts(-1);
    }

    public int scoreChanged() {
        int ret = scoreChanged;
        scoreChanged = 0;
        return ret;
    }

    public ScoreType getScoreType() {
        return scoreType;
    }

    public int getCombo() {
        return combo;
    }

    public int getScore() {
        return scoreType.getScore();
    }

    public int getMultiplier() {
        return multiplier;
    }

    public boolean songEnded() {
        return playing;
    }
}
