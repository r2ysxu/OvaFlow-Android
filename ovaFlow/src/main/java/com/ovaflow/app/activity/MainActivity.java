package com.ovaflow.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ovaflow.app.R;
import com.ovaflow.app.requests.LoginClientRequest;

public class MainActivity extends Activity {

    private Button loginButton;
    private Button registerButton;
    private Button guestButton;
    private TextView infoView;
    private EditText nameE;
    private EditText passE;

    private LoginClientRequest loginRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setTitle("OvaFlow Login");

        setContentView(R.layout.activity_main);

        infoView = (TextView) findViewById(R.id.information);

        nameE = (EditText) findViewById(R.id.id);
        passE = (EditText) findViewById(R.id.main_password_text);

        loginButton = (Button) findViewById(R.id.main_login_button);
        registerButton = (Button) findViewById(R.id.main_reg_button);
        guestButton = (Button) findViewById(R.id.main_guest_button);

        loginRequest = new LoginClientRequest(this);
        addActionListeners();
    }

    private void addActionListeners() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String username = nameE.getText().toString();
                String password = passE.getText().toString();
                if (!loginRequest.checkLogin(username, password, infoView))
                    infoView.setText("No network connection available.");
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                infoView.setText("Registering");
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }
        });
        guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginRequest.sendMessage("guest", 0, 2);
            }
        });
    }

}
