package com.ovaflow.app.requests;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

import com.ovaflow.app.localstorage.SongFileLocator;
import com.ovaflow.app.model.BeatmapInfo;
import com.ovaflow.app.model.SongInfo;
import com.ovaflow.app.util.StringUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ArthurXu on 20/07/2014.
 */
public class DownloadClientRequest extends ClientRequest {

    private static final String downloadSongStr = "/OvaflowServer/rest/ovf/downloadsong";
    private static final String downloadBMStr = "/OvaflowServer/rest/ovf/downloadbm";
    private static final String fetchBMStr = "/OvaflowServer/rest/ovf/bm";

    private SongInfo songInfo;
    private BeatmapInfo beatmapInfo;
    private String token;

    private ProgressBar dlProgress;

    private int state = 0;

    public DownloadClientRequest(Context context) {
        super(context);
    }

    public boolean downloadSong(String token, int id, String songname, String artist, String album, long duration, ProgressBar progress) {
        String[] paramKeys = {"usr", "id"};
        String[] paramValues = {token, id + ""};
        this.dlProgress = progress;
        this.token = token;

        songInfo = new SongInfo(id, duration, songname, artist, album);
        state = 0;
        String stringUrl = ClientRequestInfo.generateRequest(downloadSongStr, paramKeys, paramValues);
        String fileName = id + "_song.mp3";
        boolean value = sendFileRequest(stringUrl, storageLocStr + fileName);
        return value;
    }

    private void downloadSongResponse(String result) {
        if (StringUtil.hasValue(result)) {
            if (result.contains("Success")) {
                String[] paramKeys = {"id"};
                String[] paramValues = {songInfo.getSongId() + ""};
                SongFileLocator sfl = new SongFileLocator(getContext());
                sfl.storeSongData(songInfo, storageLocStr);

                //Fetch Beatmap ids
                String stringUrl = ClientRequestInfo.generateRequest(fetchBMStr, paramKeys, paramValues);
                state = 1;
                super.sendRequest(stringUrl);
            }
        }
    }

    private void fetchBeatmapResponse(String result) {
        try {
            Log.i("Result", result);
            JSONObject jsonResult = new JSONObject(result);

            JSONArray songList = jsonResult.getJSONArray("SongList");

            for (int i = 0; i < songList.length(); i++) {
                JSONObject obj = songList.getJSONObject(i);
                int id = obj.getInt("Id");
                String name = obj.getString("BeatMapName");

                String[] paramKeys = {"usr", "id"};
                String[] paramValues = {token, id + ""};

                String fileName = songInfo.getSongId() + "_" + id + "_beatmap.txt";

                SongFileLocator sfl = new SongFileLocator(getContext());
                sfl.storeBMData(id, songInfo.getSongId(), name, 0, storageLocStr + fileName);
                String stringUrl = ClientRequestInfo.generateRequest(downloadBMStr, paramKeys, paramValues);
                Log.i("DlRequest", stringUrl);
                state = 2;
                super.sendFileRequest(stringUrl, storageLocStr + fileName);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void downloadBeatmapResponse(String result) {
        if (StringUtil.hasValue(result)) {
            if (result.contains("Success")) {
            }
        }
    }

    @Override
    protected void getRequest(String result) {
        switch (state) {
            case 0:
                downloadSongResponse(result);
                break;
            case 1:
                fetchBeatmapResponse(result);
                break;
            case 2:
                downloadBeatmapResponse(result);
                break;
        }
    }

    public void publishProgress(int perc) {
        dlProgress.setProgress(perc);
    }
}
