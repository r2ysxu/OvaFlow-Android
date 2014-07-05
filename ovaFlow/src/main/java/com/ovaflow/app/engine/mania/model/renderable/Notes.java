package com.ovaflow.app.engine.mania.model.renderable;

import com.ovaflow.app.engine.mania.model.data.KeyNote;
import com.ovaflow.app.engine.mania.model.renderable.primitives.Square;

import java.util.LinkedList;
import java.util.List;

/**
 * Rendered object representing a note
 */
public class Notes extends Square {

    public static final int MAXNOTE = 5;
    public static float WIDTHSCL = 1f;
    public static float HEIGHTSCL = 0.1f;

    private List<KeyNote> keynotes;

    public Notes() {
        scaleDim(WIDTHSCL, HEIGHTSCL);
        keynotes = new LinkedList<KeyNote>();
    }

    public void addKeyNotes(List<KeyNote> keyNotes) {
        this.keynotes.addAll(keyNotes);
    }

    public void addKeyNote(KeyNote keyNote) {
        this.keynotes.add(keyNote);
    }

    private void determinePosition(short fret, float yPos) {
        float xPos = 0f;
        switch (fret) {
            case 0:
                xPos = -0.5f;
                break;
            case 1:
                xPos = -0.25f;
                break;
            case 2:
                xPos = 0;
                break;
            case 3:
                xPos = 0.25f;
                break;
            case 4:
                xPos = 0.5f;
                break;
        }
        setPosition(xPos, yPos);
    }

    public int[] checkCollide(int index, float missrange) {
        int score = 0, combo = 0;
        int[] retVal = new int[2];

        for (int i = 0; i < keynotes.size(); i++) {
            KeyNote n = keynotes.get(i);
            int num = n.checkTolerance(index, missrange);
            if (num > 0) {
                keynotes.remove(i);
                i--;
                score += num;
                combo++;
            } else {
                break;
            }
        }
        retVal[0] = score;
        retVal[1] = combo;
        return retVal;
    }

    private float range = 0;

    public void setMissRange(float missRange, float missHeight) {
        range = missRange - missHeight * coords[0] + (HEIGHTSCL * coords[6]);
    }

    public boolean checkMissed() {
        boolean missed = false;

        for (int i = 0; i < keynotes.size(); i++) {
            KeyNote n = keynotes.get(i);
            if (n.missed(-1f)) {
                keynotes.remove(i);
                i--;
                missed = true;
            } else {
                break;
            }
        }

        return missed;
    }

    public void draw(float[] mMVPmatrix) {
        for (int i = 0; i < keynotes.size(); i++) {
            KeyNote n = keynotes.get(i);
            determinePosition(n.getFret(), n.getDownPos());
            if (!n.missed(range)) {
                super.draw(mMVPmatrix);
            }
            n.fall();
        }
    }
}
