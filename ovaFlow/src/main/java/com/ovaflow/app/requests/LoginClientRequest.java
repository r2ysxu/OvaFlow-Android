package com.ovaflow.app.requests;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.ovaflow.app.activity.MenuActivity;
import com.ovaflow.app.util.ExtraConstants;
import com.ovaflow.app.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Handles account Login validation and account registration with server
 * <b>WARNING:</b> This class is not thread safe, sharing this class in different threads might
 * yield unpredictable results
 */
public class LoginClientRequest extends ClientRequest {

    private static final String loginStr = "/OvaflowServer/rest/ovf/acinfo";
    private static final String registerStr = "/OvaflowServer/rest/ovf/newuser";

    private TextView infoView;

    private int requestType = -1;

    public LoginClientRequest(Context context) {
        super(context);
    }

    public boolean checkLogin(String userid, String password, TextView infoView) {
        this.infoView = infoView;
        requestType = 0;
        String[] paramKeys = {"usr", "pass"};
        String[] paramValues = {userid, password};

        if (!StringUtil.hasValue(userid)){
            infoView.setText("Please enter userid");
            return true;
        }

        String stringUrl = ClientRequestInfo.generateRequest(loginStr, paramKeys, paramValues);
        return super.sendRequest(stringUrl);
    }

    public boolean registerAccount(String userid, String password, String passwordre, TextView infoView) {
        requestType = 1;
        if (!StringUtil.hasValue(userid, password, passwordre)) {
            if (password.equals(passwordre)) {
                String[] paramKeys = {"usr", "pass"};
                String[] paramValues = {userid, password};
                String stringUrl = ClientRequestInfo.generateRequest(registerStr, paramKeys, paramValues);
                super.sendRequest(stringUrl);
            } else {
                infoView.setText("Passwords don't match");
                return false;
            }
        } else {
            infoView.setText("Please fill in all information");
            return false;
        }
        return true;
    }

    public void sendMessage(String token, String id, int rmb, int avatarId) {
        Intent intent = new Intent(getContext(), MenuActivity.class);
        intent.putExtra(ExtraConstants.EXTRA_TOKEN, token);
        intent.putExtra(ExtraConstants.EXTRA_ID, id);
        intent.putExtra(ExtraConstants.EXTRA_RMB, rmb);
        intent.putExtra(ExtraConstants.EXTRA_AVATARID, avatarId);
        getContext().startActivity(intent);
    }

    private void getLoginRequest(String result) {
        try {
            JSONObject jsonResult = new JSONObject(result);

            String token = jsonResult.getString("Token");
            String username = jsonResult.getString("User");
            int rmb = jsonResult.getInt("RMB");
            int avatarID = jsonResult.getInt("Current Avatar");

            if (StringUtil.hasValue(token)) {
                infoView.setText("Log in succeeded");
                sendMessage(token, username, rmb, avatarID);
            } else {
                infoView.setText("Incorrect id or password");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getRegisterRequest(String result) {
        try {
            JSONObject jsonResult = new JSONObject(result);

            String token = jsonResult.getString("Token");
            String username = jsonResult.getString("User");
            int rmb = jsonResult.getInt("RMB");
            int avatarID = jsonResult.getInt("Current Avatar");

            if (StringUtil.hasValue(token)) {
                infoView.setText("Registration successful");
                sendMessage(token, username, rmb, avatarID);
            } else {
                infoView.setText("Registration failed");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void getRequest(String result) {
        switch (this.requestType) {
            case 0: getLoginRequest(result);break;
            case 1: getRegisterRequest(result);break;
        }
        requestType = -1;
    }

    @Override
    protected void publishProgress(int perc) {

    }
}