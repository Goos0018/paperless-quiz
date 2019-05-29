package com.example.paperlessquiz.google.access;

import android.content.Context;
import android.widget.Toast;

import com.example.paperlessquiz.MyApplication;

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
        Toast.makeText(context, "Sending data...", Toast.LENGTH_SHORT).show();
        MyApplication.eventLogger.logEvent(userName,startMessage);
    }

    @Override
    public void loadingEnded() {

    }

    @Override
    public void loadingError(String error) {

    }
}
