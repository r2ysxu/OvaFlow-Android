package com.ovaflow.app.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.ovaflow.app.R;
import com.ovaflow.app.model.PlaylistInfo;
import com.ovaflow.app.model.SongInfo;

/**
 * Created by ArthurXu on 05/07/2014.
 */
public class PlayListAdapter extends BaseExpandableListAdapter {

    private Context mActivityContext;
    private PlaylistInfo playlistInfo;

    public PlayListAdapter(Context context, PlaylistInfo playlistInfo) {
        this.mActivityContext = context;
        this.playlistInfo = playlistInfo;
    }

    @Override
    public int getGroupCount() {
        return playlistInfo.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i2) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i2) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mActivityContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.play_list_group, null);
        }

        TextView songName = (TextView) view.findViewById(R.id.play_list_song_name);
        TextView songInfo = (TextView) view.findViewById(R.id.play_list_song_info);

        SongInfo si = playlistInfo.getSongInfo(i);

        songName.setText(si.getName());
        songName.setTextColor(Color.WHITE);
        songInfo.setText(si.getArtist() + " - " + si.getAlbum());

        return view;
    }

    @Override
    public View getChildView(int i, int cPos, boolean lastChild, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mActivityContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.play_list_item, null);
        }

        TextView songLength = (TextView) view.findViewById(R.id.play_list_song_length);

        SongInfo si = playlistInfo.getSongInfo(i);

        songLength.setText("Duration: "+si.getDuration());

        return view;
    }

    public SongInfo getSongInfo (int i) {
        return playlistInfo.getSongInfo(i);
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return false;
    }
}
