package com.ovaflow.app.engine.mania.view;

import android.content.Context;

import com.ovaflow.app.engine.mania.controller.GameManiaController;
import com.ovaflow.app.engine.mania.model.data.ScoreType;
import com.ovaflow.app.engine.mania.model.renderable.Avatar;
import com.ovaflow.app.engine.mania.model.renderable.Crossbar;
import com.ovaflow.app.engine.mania.model.renderable.Notes;
import com.ovaflow.app.engine.mania.model.renderable.clickables.Hitbox;
import com.ovaflow.app.engine.mania.model.renderable.hud.HUD;

/**
 * Created by ArthurXu on 04/07/2014.
 */
public class GameManiaCanvas {

    private Context mActivityContext;

    private boolean gameStarted = false;

    private Crossbar mCrossbar;
    private Hitbox mHitboxs;
    private Notes currentNotes;
    private Avatar avatar;

    private HUD hud;

    private final GameManiaController gmee;

    public GameManiaCanvas(Context context, GameManiaController gmee) {
        this.mActivityContext = context;
        this.gmee = gmee;
    }

    public void startGame() {
        if (!gameStarted) {
            gmee.playMusic(mActivityContext);
            gmee.startGame(0);
            gameStarted = true;
        }
    }

    private void drawAvatar(float[] mMVPmatrix) {
        avatar.draw(mMVPmatrix);
    }

    private void drawNotes(float[] mMVPMatrix) {
        long elapsedTime = (gmee.getTime() / 100) * 100;

        if (!gmee.getKeynotes().isEmpty() && (Math.abs(gmee.getKeynotes().get(0).getTime() - elapsedTime) < 100)) {
            currentNotes.addKeyNote(gmee.getKeynotes().remove(0));
        }
        currentNotes.draw(mMVPMatrix);
        if (currentNotes.checkMissed()) {
            gmee.missedNote();
            hud.startSplash(-1);
            avatar.setPose(-1);
        }
    }

    private void drawHitAnimation(float[] mMVPmatrix) {
        int scoreChanged = gmee.scoreChanged();
        if (scoreChanged > 0) {
            hud.updateScore(gmee.getScore());
            hud.startSplash(scoreChanged);
            avatar.setPose(scoreChanged);
        }
        hud.drawHitSplash(mMVPmatrix, scoreChanged);
    }

    public void checkCollide(int index) {
        ScoreType st = currentNotes.checkCollide(index, Crossbar.HITRANGE);
        gmee.addScore(st);
    }

    public void buttonPressed(float x, float y) {
        int index = mHitboxs.contains(x, y);
        if (index != -1) {
            checkCollide(index);
            mHitboxs.setPressed(index, true);
        }
    }

    public void buttonReleased(float x, float y) {
        int index = mHitboxs.contains(x, y);
        if (index != -1)
            mHitboxs.setPressed(index, false);
    }

    public void onSurfaceCreated() {
        //Initialize Objects
        mCrossbar = new Crossbar(mActivityContext);
        mHitboxs = new Hitbox(mActivityContext);
        currentNotes = new Notes();
        hud = new HUD(mActivityContext);
        avatar = new Avatar(mActivityContext);
        currentNotes.setMissRange(Crossbar.HITRANGE, Crossbar.HEIGHT);
    }


    public boolean drawFrame(float[] mMVPMatrix) {
        mCrossbar.draw(mMVPMatrix);
        mHitboxs.draw(mMVPMatrix);
        if (gmee.getStartTime() > 0)
            drawNotes(mMVPMatrix);

        drawHitAnimation(mMVPMatrix);
        drawAvatar(mMVPMatrix);
        hud.draw(mMVPMatrix, gmee.getCombo());

        return gmee.songEnded();
    }

    public ScoreType getScoreType() {
        return gmee.getScoreType();
    }

    public void pauseGame() {
        gmee.pauseMusic();
    }

    public void resumeGame() {
        gmee.resumeMusic();
    }
}
