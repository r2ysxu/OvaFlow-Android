package com.ovaflow.app.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ArthurXu on 04/07/2014.
 */
public class SongInfo implements Serializable {

    private long duration;
    private String name;
    private String artist;
    private String album;
    private int songId;

    public SongInfo(int songId, long duration, String name, String artist, String album) {
        this.duration = duration;
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.songId = songId;
    }

    public long getDuration() {
        return duration;
    }

    public String getDurationStr() {
        Date date = new Date(duration);
        DateFormat formatter = new SimpleDateFormat("mm:ss");
        if (duration >= 3600000)
            formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(date);
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public int getSongId() {
        return songId;
    }

}
