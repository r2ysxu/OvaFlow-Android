package com.ovaflow.app.requests;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.ProgressBar;

import com.ovaflow.app.localstorage.SongFileLocator;
import com.ovaflow.app.util.StringUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ArthurXu on 20/07/2014.
 */
public class DownloadClientRequest extends ClientRequest {

    private static final String downloadStr = "/OvaflowServer/rest/ovf/downloadsong";
    private static final String storageLocStr = "/sdcard/";

    private String songName;
    private int songId;
    private String songArtist;
    private String songAlbum;

    private boolean cancelled = false;
    private ProgressBar dlProgress;

    public DownloadClientRequest(Context context) {
        super(context);
    }

    public boolean downloadSong(String token, int id, String songname, String artist, String album, ProgressBar progress) {
        String[] paramKeys = {"usr", "id"};
        String[] paramValues = {token, id + ""};
        this.dlProgress = progress;
        this.songId = id;
        this.songName = songname;
        this.songArtist = artist;
        this.songAlbum = album;
        String stringUrl = ClientRequestInfo.generateRequest(downloadStr, paramKeys, paramValues);
        boolean value = sendRequest(stringUrl);
        return value;
    }

    public void cancel() {
        if (this.cancelled)
            this.cancelled = false;
    }

    @Override
    protected boolean sendRequest(String stringUrl) {
        ConnectivityManager connMgr = (ConnectivityManager)
                getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageTask().execute(stringUrl);
        } else {
            return false;
        }
        return true;
    }

    @Override
    protected void getRequest(String result) {
        if (StringUtil.hasValue(result)) {
            if (result.equals("Success")) {
                SongFileLocator sfl = new SongFileLocator(getContext());
                sfl.storeSongData(songId, songName, songArtist, songAlbum, storageLocStr + songName + ".mp3");
            }
        }
    }

    public void publishProgress(int perc) {
        dlProgress.setProgress(perc);
    }

    private String downloadUrl(String myurl) throws IOException {
        InputStream input = null;
        OutputStream output = null;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();
            int fileLength = conn.getContentLength();
            input = conn.getInputStream();
            output = new FileOutputStream(storageLocStr + songName + ".mp3");

            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                // allow canceling with back button
                if (cancelled) {
                    input.close();
                    cancelled = false;
                    return "Cancelled";
                }
                total += count;
                // publishing the progress....
                if (fileLength > 0) // only if total length is known
                    publishProgress((int) (total * 100 / fileLength));
                output.write(data, 0, count);
            }
        } finally {
            if (input != null)
                input.close();
            if (output != null)
                output.close();
        }
        return "Success";
    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            getRequest(result);
        }
    }
}
