package com.ovaflow.app.localstorage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.ovaflow.app.model.BeatmapInfo;
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
        db.execSQL("CREATE TABLE " + BEATMAP_TABLE_NAME + " (id INTEGER PRIMARY KEY, songId INTEGER, beatmapname TEXT, difficulty INTEGER, beatmappath TEXT, FOREIGN KEY(beatmapname) REFERENCES songinfo(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }

    public List<SongInfo> fetchAllSongData() {
        Cursor rc = getReadableDatabase().rawQuery("SELECT * FROM " + SONG_TABLE_NAME, new String[0]);
        List<SongInfo> songInfos = new ArrayList<SongInfo>();
        rc.moveToFirst();
        while (!rc.isAfterLast()) {
            SongInfo si = new SongInfo(rc.getInt(0), 0, rc.getString(1), rc.getString(2), rc.getString(3));
            songInfos.add(si);
            rc.moveToNext();
        }
        return songInfos;
    }

    public String fetchSongPath(int songId) {
        String[] args = {songId + ""};
        Cursor rc = getReadableDatabase().rawQuery("SELECT songpath FROM " + SONG_TABLE_NAME + " WHERE id = ? ", args);
        if (rc.moveToFirst()) {
            return rc.getString(0);
        }
        return null;
    }

    public String fetchBeatmapPath(int songId, int beatmapId) {
        String[] args = {songId + "", beatmapId + ""};
        Cursor rc = getReadableDatabase().rawQuery("SELECT beatmappath FROM " + BEATMAP_TABLE_NAME + " WHERE songId = ? AND id = ?", args);
        if (rc.moveToFirst()) {
            return rc.getString(0);
        }
        return null;
    }

    public List<BeatmapInfo> fetchBeatmaps(int songId) {
        String[] args = {songId + ""};
        Cursor rc = getReadableDatabase().rawQuery("SELECT * FROM " + BEATMAP_TABLE_NAME + " WHERE songId = ?", args);
        List<BeatmapInfo> bmInfos = new ArrayList<BeatmapInfo>();
        rc.moveToFirst();
        while (!rc.isAfterLast()) {
            BeatmapInfo bi = new BeatmapInfo(rc.getInt(0), rc.getString(1), (int) (Math.random() * 90) + 5);
            bmInfos.add(bi);
            rc.moveToNext();
        }
        return bmInfos;
    }

    public BeatmapInfo fetchBeatmap(int songId, int beatmapId) {
        String[] args = {songId + "", beatmapId + ""};
        Cursor rc = getReadableDatabase().rawQuery("SELECT * FROM " + BEATMAP_TABLE_NAME + " WHERE id = ? AND songId = ?", args);
        if (rc.moveToNext()) {
            int id = rc.getInt(1);
            String name = rc.getString(3);
            int diff = rc.getInt(4);
            return new BeatmapInfo(id, name, diff);
        }
        return null;
    }

    public void storeBMData(BeatmapInfo binfo, int songId, String storageLocStr) {
        storeBMData(binfo.getId(), songId, binfo.getName(), binfo.getDifficulty(), songId + "_" + binfo.getId() + "_beatmap.txt");
    }

    public void storeBMData(int id, int songId, String bmName, int diffculty, String bmPath) {
        SQLiteStatement stmt = getWritableDatabase().compileStatement(
                "INSERT OR REPLACE INTO " + BEATMAP_TABLE_NAME + "(id, songId, beatmapname, difficulty, beatmappath) VALUES ( ?, ?, ?, ?, ? );");
        stmt.bindLong(1, id);
        stmt.bindLong(2, songId);
        stmt.bindString(3, bmName);
        stmt.bindLong(4, diffculty);
        stmt.bindString(5, bmPath);
        stmt.execute();
    }

    public void storeSongData(SongInfo songInfo, String storageLocStr) {
        storeSongData(songInfo.getSongId(), songInfo.getName(), songInfo.getArtist(),
                songInfo.getAlbum(), storageLocStr + songInfo.getSongId() + "_song.mp3");
    }

    public void storeSongData(int id, String songName, String artist, String album, String songPath) {
        SQLiteStatement stmt = getWritableDatabase().compileStatement(
                "INSERT OR REPLACE INTO " + SONG_TABLE_NAME + "(id, songname, songartist, songalbum, songpath) VALUES ( ?, ?, ?, ?, ? );");
        stmt.bindLong(1, id);
        stmt.bindString(2, songName);
        stmt.bindString(3, artist);
        stmt.bindString(4, album);
        stmt.bindString(5, songPath);
        stmt.execute();
    }
}
