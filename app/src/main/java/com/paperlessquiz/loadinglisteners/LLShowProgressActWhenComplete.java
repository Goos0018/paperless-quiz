package com.paperlessquiz.loadinglisteners;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.paperlessquiz.MyApplication;
import com.paperlessquiz.loadinglisteners.LoadingActivity;
import com.paperlessquiz.loadinglisteners.LoadingListener;
import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.webrequest.EventLogger;

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
        if (MyApplication.theQuiz.getThisUser() == null){team = "none";} else {team = MyApplication.theQuiz.getThisUser().getName();}
        MyApplication.logMessage(context, QuizDatabase.LOGLEVEL_ERROR, "Loading error - request ID" + callerID + ": " + error);
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        loading.dismiss();
    }

}
