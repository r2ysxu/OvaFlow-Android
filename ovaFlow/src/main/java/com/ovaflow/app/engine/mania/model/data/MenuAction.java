package com.ovaflow.app.engine.mania.model.data;

/**
 * Created by ArthurXu on 04/07/2014.
 */
public class MenuAction {
    private int songId;
    private int type;

    public static MenuAction create(int songId, int type) {
        MenuAction ma = new MenuAction();
        ma.songId = songId;
        ma.type = type;
        return ma;
    }

    public int getSongId() {
        return songId;
    }

    public int getType() {
        return type;
    }
}
