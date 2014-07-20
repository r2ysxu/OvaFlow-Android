package com.ovaflow.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ovaflow.app.R;
import com.ovaflow.app.requests.LoginClientRequest;

public class RegistrationActivity extends Activity {

    private EditText nameText, passwordText, passwordReText;
    private Button okButton, backButton;
    private TextView infoView;

    private LoginClientRequest loginRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        nameText = (EditText) findViewById(R.id.reg_user_text);
        passwordText = (EditText) findViewById(R.id.reg_password_text);
        passwordReText = (EditText) findViewById(R.id.reg_passrep_text);

        okButton = (Button) findViewById(R.id.reg_ok_button);
        backButton = (Button) findViewById(R.id.reg_back_button);

        infoView = (TextView) findViewById(R.id.reg_info_text);

        loginRequest = new LoginClientRequest(this);

        addListeners();
    }

    private void addListeners() {
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userid = nameText.getText().toString();
                String password = passwordText.getText().toString();
                String passwordRe = passwordReText.getText().toString();
                if(loginRequest.registerAccount(userid, password, passwordRe, infoView))
                    infoView.setText("No network connection available.");
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavUtils.navigateUpFromSameTask(RegistrationActivity.this);
            }
        });
    }
}
