package com.paperlessquiz.googleaccess;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.paperlessquiz.MyApplication;

/**
 * Same as ShowProgressOnly, but also calls an action in the calling activity when complete using interface LoadingActivity
 */
public class LLShowProgressActWhenComplete implements LoadingListener {

    private Context context;
    private ProgressDialog loading;
    private String loadingTitle;
    private String loadingMessage;
    private String errorMessage;
    private boolean cancellable;

    LoadingActivity activity;

    public LLShowProgressActWhenComplete(Context context, String loadingTitle, String loadingMessage, String errorMessage, boolean cancellable) {
        this.context = context;
        this.loadingTitle = loadingTitle;
        this.loadingMessage = loadingMessage;
        this.errorMessage = errorMessage;
        this.cancellable = cancellable;
        activity=(LoadingActivity)context;
    }

    @Override
    public void loadingStarted() {
        loading = ProgressDialog.show(context, loadingTitle, loadingMessage, false, cancellable);
    }

    @Override
    public void loadingEnded(int callerID) {
        loading.dismiss();
        activity.loadingComplete(callerID);
    }

    @Override
    public void loadingError(String error, int callerID) {
        String team;
        if (MyApplication.theQuiz.getThisTeam() == null){team = "none";} else {team = MyApplication.theQuiz.getThisTeam().getName();}
        MyApplication.eventLogger.logEvent(team,EventLogger.LEVEL_ERROR,error);
        Toast.makeText(context, errorMessage + error, Toast.LENGTH_LONG).show();
        loading.dismiss();
    }

}
