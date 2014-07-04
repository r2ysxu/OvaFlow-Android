package com.ovaflow.app.engine.mania.view;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.ovaflow.app.R;
import com.ovaflow.app.engine.mania.controller.GameManiaController;
import com.ovaflow.app.engine.mania.model.data.KeyNote;
import com.ovaflow.app.engine.mania.model.renderable.Background;
import com.ovaflow.app.engine.mania.model.renderable.Clickables.Hitbox;
import com.ovaflow.app.engine.mania.model.renderable.Crossbar;
import com.ovaflow.app.engine.mania.model.renderable.HUD.HUD;
import com.ovaflow.app.engine.mania.model.renderable.Notes;

import java.util.List;

/**
 * Created by ArthurXu on 04/07/2014.
 */
public class GameManiaCanvas {

    private Context mActivityContext;

    private boolean gameStarted = false;

    private List<KeyNote> keynotes;
    private Crossbar mCrossbar;
    private Hitbox mHitboxs;
    private Notes currentNotes;
    private Background background;

    private HUD hud;

    private GameManiaController gmee;

    public GameManiaCanvas(Context context) {
        this.mActivityContext = context;
    }

    public void startGame() {
        if (!gameStarted) {
            gmee = new GameManiaController();
            gmee.playMusic(mActivityContext);
            gameStarted = true;
        }
    }

    private void drawNotes(float[] mMVPMatrix) {
        long elapsedTime = ((System.currentTimeMillis() - gmee.getStartTime()) / 100) * 100;

        if (!keynotes.isEmpty() && (Math.abs(keynotes.get(0).getTime() - elapsedTime) < 100)) {
            currentNotes.addKeyNote(keynotes.remove(0));
        }
        currentNotes.draw(mMVPMatrix);
        if (currentNotes.checkMissed(Crossbar.HITRANGE, Crossbar.HEIGHT)) {
            gmee.missedNote();
        }
    }

    private void drawHitAnimation(float[] mMVPmatrix) {
        int scoreChanged = gmee.scoreChanged();
        if (scoreChanged > 0) {
            hud.updateScore(gmee.getScore());
            hud.startSplash();
        }
        hud.drawHitSplash(mMVPmatrix, scoreChanged);
    }

    public void checkCollide(int index) {
        int[] vals = currentNotes.checkCollide(index, Crossbar.HITRANGE);
        gmee.addScore(vals[0], vals[1]);
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
        background = new Background(mActivityContext);
        mCrossbar = new Crossbar(mActivityContext);
        keynotes = KeyNote.generateNotes(mActivityContext, R.raw.default_beatmap);
        mHitboxs = new Hitbox();
        currentNotes = new Notes();
        hud = new HUD(mActivityContext);

        startGame();
    }


    public void drawFrame(float[] mMVPMatrix, float[] mProjectionMatrix, float[] mViewMatrix) {
        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        //Draw Everything
        background.draw(mMVPMatrix);
        mCrossbar.draw(mMVPMatrix);
        mHitboxs.draw(mMVPMatrix);
        if (gmee.getStartTime() > 0)
            drawNotes(mMVPMatrix);

        drawHitAnimation(mMVPMatrix);
        hud.draw(mMVPMatrix, gmee.getCombo());
    }
}
