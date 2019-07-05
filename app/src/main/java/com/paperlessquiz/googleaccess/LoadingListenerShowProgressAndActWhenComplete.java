package com.paperlessquiz.googleaccess;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.paperlessquiz.MyApplication;

public class LoadingListenerShowProgressAndActWhenComplete implements LoadingListener {

    //This loadinglistener will display a message while loading is in progress.
    //When loading is complete, we will call the onLoaded interface in the calling activity
    //Cancellable indicates whether this message will block the interface until loading has finished or be cancellable
    private Context context;
    private ProgressDialog loading;
    private String loadingTitle;
    private String loadingMessage;
    private String errorMessage;
    private boolean cancellable;

    LoadingActivity activity;


    public LoadingListenerShowProgressAndActWhenComplete(Context context, String loadingTitle, String loadingMessage, String errorMessage, boolean cancellable) {
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
    public void loadingEnded() {
        loading.dismiss();
        activity.loadingComplete();
    }

    @Override
    public void loadingError(String error) {
        String team;
        if (MyApplication.theQuiz.getMyLoginentity() == null){team = "none";} else {team = MyApplication.theQuiz.getMyLoginentity().getName();}
        MyApplication.eventLogger.logEvent(team,EventLogger.LEVEL_ERROR,error);
        Toast.makeText(context, errorMessage + error, Toast.LENGTH_LONG).show();
        loading.dismiss();
    }

}
