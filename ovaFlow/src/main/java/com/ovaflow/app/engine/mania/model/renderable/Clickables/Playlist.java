package com.ovaflow.app.engine.mania.model.renderable.Clickables;

import android.content.Context;
import android.graphics.Typeface;

import com.ovaflow.app.engine.mania.model.data.PlaylistInfo;
import com.ovaflow.app.engine.mania.model.data.SongInfo;
import com.ovaflow.app.engine.mania.model.renderable.primitives.TextureObject;

/**
 * Created by ArthurXu on 04/07/2014.
 */
public class Playlist extends TextureObject {

    private Context mActivityContext;
    private PlaylistInfo playlistInfo;

    public Playlist(Context context, PlaylistInfo playlistInfo) {
        super(context);
        this.mActivityContext = context;
        this.playlistInfo = playlistInfo;
    }

    private void drawSongFrame(float[] mMVPmatrix, int index) {
        float x = -0.7f, y = 0.9f;
        SongInfo s = playlistInfo.getSongInfo(index);

        x += index * 0.1f;
        y -= index * 0.2f;
        setTextContext(Typeface.create("Helvetica",  Typeface.BOLD), 40, 0xff, 0xff, 0xff, 0xff);
        setTextBitmapSize(512, 64);
        //Name
        scaleDim(3f, 0.7f);
        setPosition(x , y);
        setTextTexture(mActivityContext, s.getName());
        super.draw(mMVPmatrix);
        //Artist & Album
        setTextContext(Typeface.create("Helvetica",  Typeface.NORMAL), 40, 0xff, 0xff, 0xff, 0xff);
        setTextBitmapSize(512, 64);

        y -= 0.08f;
        String additionInfo = s.getArtist() + " - " + s.getAlbum();
        scaleDim(3f, 0.7f);
        setPosition(x , y);
        setTextTexture(mActivityContext, additionInfo);
        super.draw(mMVPmatrix);
    }

    public int getSelectedSongId() {
        return 1;
    }

    public void draw(float[] mMVPmatrix) {
        for (int i = 0; i < playlistInfo.size(); i++) {
            drawSongFrame(mMVPmatrix, i);
        }
    }
}
