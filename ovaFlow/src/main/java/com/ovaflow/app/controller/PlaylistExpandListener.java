package com.ovaflow.app.controller;

import android.widget.ExpandableListView;

/**
 * Created by ArthurXu on 05/07/2014.
 */
public class PlaylistExpandListener implements ExpandableListView.OnGroupExpandListener {

    private int pGroup = -1;
    private ExpandableListView playlist;

    public PlaylistExpandListener(ExpandableListView playlist) {
        this.playlist = playlist;
    }

    @Override
    public void onGroupExpand(int i) {
        if (i != pGroup)
            playlist.collapseGroup(pGroup);
        pGroup = i;
    }

    public int getSelectedGroup() {
        return pGroup;
    }
}
