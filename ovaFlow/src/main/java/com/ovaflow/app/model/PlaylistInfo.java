package com.ovaflow.app.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ArthurXu on 04/07/2014.
 */
public class PlaylistInfo {

    private List<SongInfo> songs;
    private int pointer;

    public static final PlaylistInfo generatePlaylist() {
        List<SongInfo> songs = new ArrayList<SongInfo>();

        songs.add(new SongInfo(1, 72000, "We Will Rock You Child version", "Queens", "We Will Rock You"));
        songs.add(new SongInfo(2, 10000, "We are the Champions", "Queens", "We Will Rock You"));
        songs.add(new SongInfo(3, 10000, "Another One Bites the Dust", "Queens", "We Will Rock You"));
        songs.add(new SongInfo(4, 10000, "Sera Sera", "Shakira", "Oral Fixation Vol. 2"));
        songs.add(new SongInfo(5, 10000, "Rabiosa", "Shakira", "Sale el Sol"));

        return new PlaylistInfo(songs);
    }

    private PlaylistInfo(List<SongInfo> songs) {
        this.songs = songs;
        pointer = (int) Math.random() * (songs.size() - 1);
    }

    public SongInfo getSongInfo(int index) {
        return songs.get(index);
    }

    public int size() {
        return songs.size();
    }
}
