package com.ovaflow.app.requests;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.ovaflow.app.activity.MenuActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ArthurXu on 09/07/2014.
 */
public class LoginClientRequest {

    Context context;
    public final static String EXTRA_ID = "com.ovaflow.app.ID";
    public final static String EXTRA_RMB = "com.ovaflow.app.RMB";

    private static final String serviceStr = "/OvaflowServer/rest/ovf/acinfo";

    public LoginClientRequest(Context context) {
        this.context = context;
    }

    public void sendMessage(String id, int rmb) {
        Intent intent = new Intent(context, MenuActivity.class);
        intent.putExtra(EXTRA_ID, id);
        intent.putExtra(EXTRA_RMB, rmb);
        context.startActivity(intent);
    }

    public boolean checkLogin(String id, String password, TextView infoView) {
        String ipAddress = "192.168.0.15";
        String[] paramKeys = {"usr", "pass"};
        String[] paramValues = {id, password};

        String stringUrl = ClientRequestInfo.generateRequest(serviceStr, paramKeys, paramValues);
        Log.i("URL", stringUrl);
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageTask(id, password, infoView).execute(stringUrl);
        } else {
            return false;
        }
        return true;
    }

    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
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
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {

        private String userId, password;
        private TextView infoView;

        public DownloadWebpageTask(String userId, String password, TextView view) {
            this.userId = userId;
            this.password = password;
            infoView = view;
        }


        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Log.i("Result", result);
            if (userId == null) {
                infoView.setText("Please enter a username");
            } else if (userId.equals("guest")) {
                sendMessage(userId, 0);
            } else if (!result.contains("Token:null")) {
                infoView.setText("Log in succeeded");
                String name = result.substring(result.indexOf("User:") + 5, result.indexOf("~RMB:"));
                String n = result.substring(result.indexOf("RMB:") + 4, result.indexOf("~Current Avatar:")).trim();
                sendMessage(name, Integer.parseInt(n));
            } else {
                infoView.setText("Incorrect id or password");
            }
        }
    }
}
