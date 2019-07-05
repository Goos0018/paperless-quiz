package com.paperlessquiz.googleaccess;

import android.content.Context;
import android.widget.Toast;

import com.paperlessquiz.MyApplication;

//This LL will showa short message indicating that loading was started.
// It will also log the same message to the eventLogger
public class LoadingListenerNotify implements LoadingListener {
    private Context context;
    private String userName;
    private String startMessage;


    public LoadingListenerNotify(Context context,String userName,String startMessage) {
        this.context = context;
        this.userName=userName;
        this.startMessage = startMessage;
    }

    @Override
    public void loadingStarted() {
        Toast.makeText(context, startMessage, Toast.LENGTH_SHORT).show();
        MyApplication.eventLogger.logEvent(userName,EventLogger.LEVEL_INFO,startMessage);
    }

    @Override
    public void loadingEnded() {

    }

    @Override
    public void loadingError(String error) {
        String team;
        if (MyApplication.theQuiz.getMyLoginentity() == null){team = "none";} else {team = MyApplication.theQuiz.getMyLoginentity().getName();}
        MyApplication.eventLogger.logEvent(team,EventLogger.LEVEL_ERROR,error);
        Toast.makeText(context, error, Toast.LENGTH_LONG).show();
    }
}
