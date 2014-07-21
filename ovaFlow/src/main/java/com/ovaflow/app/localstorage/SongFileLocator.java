package com.ovaflow.app.localstorage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.ovaflow.app.model.SongInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ArthurXu on 20/07/2014.
 */
public class SongFileLocator extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "SongBeatmapDB";

    public static final String SONG_TABLE_NAME = "songinfo";
    public static final String BEATMAP_TABLE_NAME = "beatmapinfo";

    public SongFileLocator(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + SONG_TABLE_NAME + " (id INTEGER PRIMARY KEY NOT NULL, songname TEXT, songartist TEXT, songalbum TEXT, songpath TEXT)");
        db.execSQL("CREATE TABLE " + BEATMAP_TABLE_NAME + " (id INTEGER PRIMARY KEY, songId INTEGER, beatmapname TEXT, difficulty INTEGER, FOREIGN KEY(beatmapname) REFERENCES songinfo(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }

    public List<SongInfo> fetchAllSongData() {
        Cursor rc = getReadableDatabase().query(false, SONG_TABLE_NAME, null, null, null, null, null, null, null);
        List<SongInfo> songInfos = new ArrayList<SongInfo>();
        while(rc.moveToNext()) {
            songInfos.add(new SongInfo(rc.getInt(1), 0, rc.getString(2), "Artist", "Album"));
        }
        return songInfos;
    }

    public void storeSongData(int id, String songName, String artist, String album, String songPath) {
        SQLiteStatement stmt = getWritableDatabase().compileStatement(
                "INSERT INTO " + SONG_TABLE_NAME + " ( ?, ?, ?, ?, ? );");
        stmt.bindLong(1, id);
        stmt.bindString(2, songName);
        stmt.bindString(3, artist);
        stmt.bindString(4, album);
        stmt.bindString(5, songPath);
        stmt.execute();
    }
}
