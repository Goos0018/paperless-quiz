package com.paperlessquiz.loadinglisteners;

import android.content.Context;
import android.widget.Toast;

import com.paperlessquiz.MyApplication;
import com.paperlessquiz.loadinglisteners.LoadingListener;
import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.webrequest.EventLogger;

/**
 * This LL will only show a short message indicating that loading was started.
 * It will also log the same message to the eventLogger
 */
public class LLNotifyStartOnly implements LoadingListener {
    private Context context;
    private String userName;
    private String startMessage;

    public LLNotifyStartOnly(Context context, String userName, String startMessage) {
        this.context = context;
        this.userName=userName;
        this.startMessage = startMessage;
    }

    @Override
    public void loadingStarted() {
        Toast.makeText(context, startMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadingEnded(int callerID) {

    }

    @Override
    public void loadingError(String error, int callerID) {
        MyApplication.logMessage(context, QuizDatabase.LOGLEVEL_ERROR, error);
        Toast.makeText(context, error, Toast.LENGTH_LONG).show();
    }
}
