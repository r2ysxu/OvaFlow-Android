package com.ovaflow.app.engine.mania.controller;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    private float downPos = 1f;

    public static List<KeyNote> generateNotes(Context context, int resourceId) {

        final InputStream inputStream = context.getResources().openRawResource(
                resourceId);
        final InputStreamReader inputStreamReader = new InputStreamReader(
                inputStream);
        final BufferedReader bufferedReader = new BufferedReader(
                inputStreamReader);

        //HardCoded for testing
        float fallspeed = 0.005f;
        short t = 0;

        List<KeyNote> noteList = new LinkedList<KeyNote>();

        String nextLine;

        try {
            while ((nextLine = bufferedReader.readLine()) != null) {
                String[] beats = nextLine.split("\t");
                long time = Long.parseLong(beats[0]);
                short fret = Short.parseShort(beats[1]);
                noteList.add(new KeyNote(time, fallspeed, fret, t));
            }
        } catch (IOException e) {
            return null;
        }

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