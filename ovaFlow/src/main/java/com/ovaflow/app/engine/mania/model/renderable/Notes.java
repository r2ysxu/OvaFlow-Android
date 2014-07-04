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
    public static float WIDTHSCL = 0.1f;
    public static float HEIGHTSCL = 1.0f;

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

    private void determinePosition(short fret, float xPos) {
        float yPos = 0f;
        switch (fret) {
            case 0:
                yPos = -0.5f;
                break;
            case 1:
                yPos = -0.25f;
                break;
            case 2:
                yPos = 0;
                break;
            case 3:
                yPos = 0.25f;
                break;
            case 4:
                yPos = 0.5f;
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

    public boolean checkMissed(float missrange, float height) {
        boolean missed = false;

        for (int i = 0; i < keynotes.size(); i++) {
            KeyNote n = keynotes.get(i);
            if (n.missed(missrange, height * coords[6] + (WIDTHSCL * coords[6]))) {
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
            super.draw(mMVPmatrix);
            n.fall();
        }
    }
}
