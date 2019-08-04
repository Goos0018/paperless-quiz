package com.paperlessquiz.loadinglisteners;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.paperlessquiz.MyApplication;
import com.paperlessquiz.webrequest.EventLogger;

public class LLSilentActWhenComplete implements LoadingListener {

    private Context context;
    private String errorMessage;

    LoadingActivity activity;

    public LLSilentActWhenComplete(Context context, String errorMessage) {
        this.context = context;
        this.errorMessage = errorMessage;
        activity = (LoadingActivity) context;
    }

    @Override
    public void loadingStarted() {
    }

    @Override
    public void loadingEnded(int callerID) {
        activity.loadingComplete(callerID);
    }

    @Override
    public void loadingError(String error, int callerID) {
        String team;
        if (MyApplication.theQuiz.getThisUser() == null) {
            team = "none";
        } else {
            team = MyApplication.theQuiz.getThisUser().getName();
        }
        MyApplication.eventLogger.logEvent(team, EventLogger.LEVEL_ERROR, error);
        Toast.makeText(context, errorMessage + error, Toast.LENGTH_LONG).show();
    }
}