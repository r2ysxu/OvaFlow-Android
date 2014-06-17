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
    public static float WIDTH = 1.0f;
    public static float HEIGHT = 1.0f;

    private long time;
    private short type;
    private float fallspeed;

    private float xPos = 0.07f, yPos = 0.0f;

    public static List<Note> generateNotes() {
        //HashMap<Integer, List<Note>> notes = new HashMap<Integer, List<Note>>();

        //HardCoded for testing
        List<Note> noteList = new LinkedList<Note>();
        noteList.add(new Note(1000, 0.001f, (short) 0, (short) 0));
        noteList.add(new Note(1000, 0.001f, (short) 1, (short) 0));
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
        scaleDim(WIDTH, 0.1f);
        determinePosition(fret);
    }

    private void determinePosition(short fret) {
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

    public void fallDown() {
        yPos -= fallspeed;
        setPosition(xPos, yPos);
    }

    public boolean missedOut() {
        return yPos < -1.1f;
    }

    public long getTime() {
        return time;
    }

    public int checkTolerance(float y) {
        float diff = y - yPos;
        int tol = 0;
        if (diff < 0.001)
            tol = 100;
        else if (diff < 0.005) {
            tol = 50;
        } else if (diff < 0.01) {
            tol = 30;
        }
        return tol;
    }
}
