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
 * Created by ArthurXu on 09/07/2014.
 */
public class LoginClientRequest extends ClientRequest {

    private static final String serviceStr = "/OvaflowServer/rest/ovf/acinfo";

    private TextView infoView;

    public LoginClientRequest(Context context) {
        super(context);
    }

    public boolean checkLogin(String userid, String password, TextView infoView) {
        this.infoView = infoView;

        String[] paramKeys = {"usr", "pass"};
        String[] paramValues = {userid, password};

        if (StringUtil.hasValue(userid)){
            return false;
        }

        String stringUrl = ClientRequestInfo.generateRequest(serviceStr, paramKeys, paramValues);
        super.sendRequest(stringUrl);
        return true;
    }

    public boolean registerAccount(String userId, String password, TextView infoView) {
        return true;
    }

    public void sendMessage(String id, int rmb, int avatarId) {
        Intent intent = new Intent(getContext(), MenuActivity.class);
        intent.putExtra(ExtraConstants.EXTRA_ID, id);
        intent.putExtra(ExtraConstants.EXTRA_RMB, rmb);
        intent.putExtra(ExtraConstants.EXTRA_AVATARID, avatarId);
        getContext().startActivity(intent);
    }

    @Override
    protected void getRequest(String result) {
        try {
            JSONObject jsonResult = new JSONObject(result);

            String token = jsonResult.getString("Token");
            String username = jsonResult.getString("User");
            int rmb = jsonResult.getInt("RMB");
            int avatarID = jsonResult.getInt("Current Avatar");

            if (StringUtil.hasValue(token)) {
                infoView.setText("Log in succeeded");
                sendMessage(username, rmb, avatarID);
            } else {
                infoView.setText("Incorrect id or password");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}