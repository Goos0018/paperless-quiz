package com.paperlessquiz.googleaccess;

public interface LoadingListener {
    void loadingStarted();
    void loadingEnded(int callerID);
    void loadingError(String error,int callerID);
}
