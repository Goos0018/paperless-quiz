package com.paperlessquiz.loadinglisteners;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.paperlessquiz.MyApplication;
import com.paperlessquiz.loadinglisteners.LoadingListener;
import com.paperlessquiz.webrequest.EventLogger;

/**
 *     Display a message while loading is in progress.
 *     Cancellable indicates whether this message will block the interface until loading has finished or is cancellable
 */
public class LLShowProgressOnly implements LoadingListener {

    private Context context;
    private ProgressDialog loading;
    private String loadingTitle;
    private String loadingMessage;
    private String errorMessage;
    private boolean cancellable;

    public LLShowProgressOnly(Context context, String loadingTitle, String loadingMessage, String errorMessage, boolean cancellable) {
        this.context = context;
        this.loadingTitle = loadingTitle;
        this.loadingMessage = loadingMessage;
        this.errorMessage = errorMessage;
        this.cancellable = cancellable;
    }

    @Override
    public void loadingStarted() {
        loading = ProgressDialog.show(context, loadingTitle, loadingMessage, false, cancellable);
    }

    @Override
    public void loadingEnded(int callerID) {
        loading.dismiss();
    }

    @Override
    public void loadingError(String error, int callerID) {
        String team;
        if (MyApplication.theQuiz.getThisUser() == null){team = "none";} else {team = MyApplication.theQuiz.getThisUser().getName();}
        MyApplication.eventLogger.logEvent(team, EventLogger.LEVEL_ERROR,error);
        Toast.makeText(context, errorMessage + error, Toast.LENGTH_LONG).show();
        loading.dismiss();
    }

}
