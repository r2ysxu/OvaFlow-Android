package com.ovaflow.app.requests;

import android.content.Context;
import android.content.Intent;

import com.ovaflow.app.activity.DownloadActivity;
import com.ovaflow.app.model.PlaylistInfo;
import com.ovaflow.app.model.SongInfo;
import com.ovaflow.app.util.ExtraConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ArthurXu on 20/07/2014.
 */
public class SongClientRequest extends ClientRequest {

    private static final String songListUrl = "/OvaflowServer/rest/ovf/song";
    private List<SongInfo> songInfoList;
    private String token;

    public SongClientRequest(Context context) {
        super(context);
    }

    public void fetchSongList(String userToken) {
        String stringUrl = ClientRequestInfo.generateRequest(songListUrl, new String[0], new String[0]);
        this.token = userToken;
        super.sendRequest(stringUrl);
    }

    public List<SongInfo> getSongInfoList() {
        return songInfoList;
    }

    @Override
    protected void getRequest(String result) {
        songInfoList = new ArrayList<SongInfo>();

        try {
            JSONObject jsonResult = new JSONObject(result);

            JSONArray songList = jsonResult.getJSONArray("SongList");

            for (int i = 0; i < songList.length(); i++) {
                JSONObject obj = songList.getJSONObject(i);
                songInfoList.add(new SongInfo(obj.getInt("Id"), 0, obj.getString("SongName"),
                        obj.getString("Singer"), ""));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Intent intent = new Intent(getContext(), DownloadActivity.class);
        intent.putExtra(ExtraConstants.EXTRA_ID, token);
        intent.putExtra(ExtraConstants.EXTRA_SONG_LIST,
                PlaylistInfo.generatePlaylist(songInfoList));
        getContext().startActivity(intent);
    }
}
