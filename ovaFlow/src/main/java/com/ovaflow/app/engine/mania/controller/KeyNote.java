package com.ovaflow.app.engine.mania.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ArthurXu on 24/06/2014.
 */
public class KeyNote {

    public static final float MAXTOLERANCE = 0.02f;
    public static final float MEDIUMTOLERANCE = 0.05f;
    public static final float LOWTOLERANCE = 0.1f;

    private long time;
    private short type;
    private float fallspeed;
    private short fret;

    private float downPos;

    public static List<KeyNote> generateNotes() {

        //HardCoded for testing
        List<KeyNote> noteList = new LinkedList<KeyNote>();
        noteList.add(new KeyNote(1000, 0.005f, (short) 0, (short) 0));
        noteList.add(new KeyNote(1000, 0.005f, (short) 1, (short) 0));

        Collections.sort(noteList, new Comparator<KeyNote>() {
            @Override
            public int compare(KeyNote note, KeyNote note2) {
                long comp = (note.getTime() - note2.getTime());
                if (comp == 0) return 0;
                else if (comp < 0) return -1;
                else return 1;
            }
        });

        return noteList;
    }

    public KeyNote(long time, float fallSpeed, short fret, short typeFlag) {
        this.time = time;
        this.fret = fret;
        this.fallspeed = fallSpeed;
        this.type = typeFlag;
    }

    public int checkTolerance(int fret, float x) {
        if (this.fret != fret)
            return 0;
        float diff = Math.abs(x - downPos);
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

    public boolean missed(float missrange, float height) {
        boolean val = downPos < missrange - height;
        return val;
    }

    public float getDownPos() {
        return downPos;
    }

    public float fall() {
        downPos -= fallspeed;
        return downPos;
    }

    public short getFret() {
        return fret;
    }

    public long getTime() {
        return time;
    }
}