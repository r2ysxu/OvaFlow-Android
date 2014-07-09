package com.ovaflow.app.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ovaflow.app.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity {

    public final static String EXTRA_ID = "com.ovaflow.app.ID";
    public final static String EXTRA_RMB = "com.ovaflow.app.RMB";
    private Button logi;
    private Button reg;
    private TextView nameV;
    private TextView passV;
    private TextView infoV;
    private EditText nameE;
    private EditText passE;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setTitle("voaFlow");

        setContentView(R.layout.activity_main);

        nameV = (TextView) findViewById(R.id.IdText);
        passV = (TextView) findViewById(R.id.passwordText);
        infoV = (TextView) findViewById(R.id.information);

        nameE = (EditText) findViewById(R.id.id);
        passE = (EditText) findViewById(R.id.password);

        logi = (Button) findViewById(R.id.Login);
        logi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkLogin(nameE.getText().toString(), passE.getText().toString());
            }
        });

        reg = (Button) findViewById(R.id.Reg);
        reg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //do register
                infoV.setText("registering");
            }
        });


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
    private  Boolean shit(String a)
    {
        if(a.equals("0")||a.equals("1")||a.equals("2")||a.equals("3")||a.equals("4")||a.equals("5")||a.equals("5")||a.equals("6")||a.equals("7")||a.equals("8")||a.equals("9"))
        {
            return true;
        }
        else return false;
    }
    private String fuckit(String str)
    {
        int i=0;
        for(;i<str.length();i++)
        {
            if(!shit(str.substring(i,i+1)))
            {
                break;
            }
        }
        String answer = str.substring(0,i);
        if(answer.equals(""))
        {
            return "-1";
        }
        return answer;
    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        private int stoi(String value){
            int sum = 0;
            for(int i = 0;i<value.length();i++){
               // if()
            }
            return sum;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            if(result.substring(0,5+(nameE.getText().toString()).length()).equals("User:"+nameE.getText().toString())){
                infoV.setText("log in succeeded");
                //int val = stoi(result.substring(10+(nameE.getText().toString()).length(),result.length()-1));
                //if()
                String n = result.substring(11+(nameE.getText().toString()).length(),result.length());
                sendMessage(nameE.getText().toString(),Integer.parseInt(fuckit(n).toString()));
                //sendMessage(nameE.getText().toString(),Integer.parseInt("700"));
                //String value= result.substring(10+(nameE.getText().toString()).length(),result.length()-1);
                //infoV.setText();
            }else{
                infoV.setText("incorrect id or password");
            }
        }
    }

    public void sendMessage(String id,int rmb) {
        Intent intent = new Intent(this, menuActivity.class);
        intent.putExtra(EXTRA_ID, id);
        intent.putExtra(EXTRA_RMB, rmb);
        startActivity(intent);
    }

    public void checkLogin(String id, String password) {


        String ipAddress = "192.168.0.15";

        String stringUrl = "http://"+ipAddress+":8080/OvaflowServer/rest/ovf/acinfo?usr="+id+"&pass="+password;
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageTask().execute(stringUrl);
        } else {
            infoV.setText("No network connection available.");
        }

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
