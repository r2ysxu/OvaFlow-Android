package com.ovaflow.app.model;

import java.io.Serializable;

/**
 * Created by ArthurXu on 06/07/2014.
 */
public class BeatmapInfo implements Serializable {

    private String name;
    private int difficulty;
    private String songFileLocation;
    private String beatmapFileLocation;

    public static BeatmapInfo[] generateBeatmaps() {
        BeatmapInfo[] beatmapInfos = new BeatmapInfo[3];
        for (int i = 0; i < beatmapInfos.length; i++) {
            beatmapInfos[i] = new BeatmapInfo("Beatmap " + i, (int)(Math.random() * 70) + 30);
        }
        return beatmapInfos;
    }

    public BeatmapInfo(String name, int difficulty) {
        this.name = name;
        this.difficulty = difficulty;
    }

    public String getName() {
        return name;
    }

    public int getDifficulty() {
        return difficulty;
    }
}
