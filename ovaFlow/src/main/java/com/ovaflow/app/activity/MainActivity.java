package com.ovaflow.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ovaflow.app.R;
import com.ovaflow.app.requests.LoginClientRequest;

public class MainActivity extends Activity {

    private Button logi;
    private Button reg;
    private TextView nameV;
    private TextView passV;
    private TextView infoV;
    private EditText nameE;
    private EditText passE;

    private LoginClientRequest loginRequest;


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

        loginRequest = new LoginClientRequest(this);

        logi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String username = nameE.getText().toString();
                String password = passE.getText().toString();
                if (username != null && username.equals("guest")) { //backDoor
                    loginRequest.sendMessage(username, 0);
                } else if (!loginRequest.checkLogin(username, password, infoV))
                    infoV.setText("No network connection available.");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
