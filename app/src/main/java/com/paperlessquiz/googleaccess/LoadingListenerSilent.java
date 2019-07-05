package com.paperlessquiz.googleaccess;

import com.paperlessquiz.MyApplication;

public class LoadingListenerSilent implements LoadingListener {
    //This loadinglistener simply does nothing - allows for silent "fire and forget" logging

    public LoadingListenerSilent() {

    }

    @Override
    public void loadingStarted() {

    }

    @Override
    public void loadingEnded() {

    }

    @Override
    public void loadingError(String error) {

    }
}
