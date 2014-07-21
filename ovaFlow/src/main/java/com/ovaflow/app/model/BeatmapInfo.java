package com.ovaflow.app.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ArthurXu on 06/07/2014.
 */
public class BeatmapInfo implements Serializable {

    private String name;
    private int difficulty;
    private int id;
    private String songFileLocation;
    private String beatmapFileLocation;

    public static BeatmapInfo[] generateBeatmaps() {
        BeatmapInfo[] beatmapInfos = new BeatmapInfo[3];
        for (int i = 0; i < beatmapInfos.length; i++) {
            beatmapInfos[i] = new BeatmapInfo(i, "Beatmap " + i, (int)(Math.random() * 70) + 30);
        }
        return beatmapInfos;
    }

    public static BeatmapInfo[] generateBeatmaps(List<BeatmapInfo> bis) {
        return bis.toArray(new BeatmapInfo[0]);
    }

    public BeatmapInfo(int id, String name, int difficulty) {
        this.id = id;
        this.name = name;
        this.difficulty = difficulty;
    }

    public String getName() {
        return name;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getId() {
        return id;
    }
}
