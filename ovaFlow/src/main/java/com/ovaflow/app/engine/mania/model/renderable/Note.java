package com.ovaflow.app.engine.mania.model.renderable;

import com.ovaflow.app.engine.mania.model.renderable.primitives.Square;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Rendered object representing a note
 */
public class Note extends Square {

    public static final int MAXNOTE = 5;
    public static float WIDTHSCL = 0.1f;
    public static float HEIGHTSCL = 1.0f;

    private static final float MAXTOLERANCE = 0.02f;
    private static final float MEDIUMTOLERANCE = 0.05f;
    private static final float LOWTOLERANCE = 0.1f;

    private long time;
    private short type;
    private float fallspeed;

    private float xPos = 0.0f, yPos;

    public static List<Note> generateNotes() {
        //HashMap<Integer, List<Note>> notes = new HashMap<Integer, List<Note>>();

        //HardCoded for testing
        List<Note> noteList = new LinkedList<Note>();
        noteList.add(new Note(1000, 0.005f, (short) 0, (short) 0));
        noteList.add(new Note(1000, 0.005f, (short) 1, (short) 0));
        //notes.put(1000, noteList);

        Collections.sort(noteList, new Comparator<Note>() {
            @Override
            public int compare(Note note, Note note2) {
                long comp = (note.getTime() - note2.getTime());
                if (comp == 0) return 0;
                else if (comp < 0) return -1;
                else return 1;
            }
        });

        return noteList;
    }

    public Note(long time, float fallSpeed, short fret, short typeFlag) {
        this.time = time;
        this.fallspeed = fallSpeed;
        this.type = typeFlag;
        scaleDim(WIDTHSCL, HEIGHTSCL);
        determinePosition(fret);
    }

    private void determinePosition(short fret) {
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

    public void fallDown() {
        xPos -= fallspeed;
        setPosition(xPos, yPos);
    }

    public boolean missedOut(float missrange, float height) {
        return xPos < missrange - height * coords[6] + (WIDTHSCL * coords[6]);
    }

    public long getTime() {
        return time;
    }

    public int checkTolerance(float x) {
        float diff = Math.abs(x - xPos);
        int tol = 0;
        if (diff < MAXTOLERANCE) {
            tol = 100;
        } else if (diff < MEDIUMTOLERANCE) {
            tol = 50;
        } else if (diff < LOWTOLERANCE) {
            tol = 30;
        }
        return tol;
    }
}
