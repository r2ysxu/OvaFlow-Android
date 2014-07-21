package com.ovaflow.app.requests;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ArthurXu on 20/07/2014.
 */
public abstract class ClientRequest {

    private Context context;
    private boolean cancelled = false;
    protected static final String storageLocStr = "/OvaflowMusic/";

    public ClientRequest(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void cancel() {
        if (this.cancelled)
            this.cancelled = false;
    }

    private String readIt(InputStream stream, int len) throws IOException {
        Reader reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

    private String downloadUrl(String myurl) throws IOException {
        InputStream input = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            //Log.d(DEBUG_TAG, "The response is: " + response);
            input = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(input, len);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (input != null) {
                input.close();
            }
        }
    }


    protected boolean sendFileRequest(String stringUrl, String fileName) {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadFileTask(fileName).execute(stringUrl);
        } else {
            return false;
        }
        return true;
    }

    protected boolean sendRequest(String stringUrl) {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageTask().execute(stringUrl);
        } else {
            return false;
        }
        return true;
    }

    protected abstract void getRequest(String result);

    protected abstract void publishProgress(int perc);

    private String downloadFileUrl(String myurl, String filePath) throws IOException {
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


            File loc = new File(getContext().getExternalFilesDir(null).getPath() + storageLocStr);
            if (!loc.exists())
                loc.mkdir();
            File f = new File(filePath);
            loc.createNewFile();

            output = new FileOutputStream(getContext().getExternalFilesDir(null).getPath() + filePath);

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
        return "Success:" + ClientRequestInfo.parseBeatmap(myurl, "id");
    }

    private class DownloadFileTask extends AsyncTask<String, Void, String> {
        String filePath;

        public DownloadFileTask(String filePath) {
            this.filePath = filePath;
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                return downloadFileUrl(urls[0], filePath);
            } catch (IOException e) {
                e.printStackTrace();
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            getRequest(result);
        }
    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            getRequest(result);
        }
    }
}
