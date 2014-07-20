package com.ovaflow.app.controller;

import android.widget.ExpandableListView;

/**
 * Created by ArthurXu on 05/07/2014.
 */
public class PlaylistExpandListener implements ExpandableListView.OnGroupExpandListener {

    private int pGroup = -1;
    private ExpandableListView expandableListView;

    public PlaylistExpandListener(ExpandableListView expandableListView) {
        this.expandableListView = expandableListView;
    }

    public int getSelectedGroup() {
        return pGroup;
    }

    @Override
    public void onGroupExpand(int i) {
        if (i != pGroup)
            expandableListView.collapseGroup(pGroup);
        pGroup = i;
    }
}