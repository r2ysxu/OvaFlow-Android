package com.ovaflow.app.requests;

import android.content.Context;

/**
 * Created by ArthurXu on 20/07/2014.
 */
public class ScoreClientRequest extends ClientRequest {


    public ScoreClientRequest(Context context) {
        super(context);
    }

    public boolean sendScore() {
        return true;
    }

    public boolean getPersonalScore() {
        return true;
    }

    public boolean getLeaderboardScore() {
        return true;
    }

    @Override
    protected void getRequest(String result) {
    }

    @Override
    protected void publishProgress(int perc) {

    }
}
