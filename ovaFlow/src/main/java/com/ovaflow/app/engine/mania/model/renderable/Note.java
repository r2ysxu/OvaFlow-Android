package com.ovaflow.app.engine.mania.model.renderable;

import com.ovaflow.app.engine.mania.model.renderable.primitives.Square;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Rendered object representing a note
 */
public class Note extends Square {

    public static final int MAXNOTE = 5;

    private short fret;
    private short type;
    private int fallspeed;

    public static HashMap<Integer, List<Note>> generateNotes() {
        HashMap<Integer, List<Note>> notes = new HashMap<Integer, List<Note>>();

        //HardCoded for testing
        List<Note> noteList = new ArrayList<Note>(MAXNOTE);
        noteList.add(new Note(10, (short) 0, (short) 0));
        noteList.add(new Note(10, (short) 1, (short) 0));
        notes.put(100, noteList);

        return notes;
    }

    public Note(int fallSpeed, short fret, short typeFlag) {
        super();
        this.fallspeed = fallSpeed;
        this.fret = fret;
        this.type = typeFlag;
        scaleDim(0.7f, 0.1f);
        setPosition(0.07f, 0.0f);
    }
}
