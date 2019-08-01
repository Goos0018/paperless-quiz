package com.paperlessquiz.loadinglisteners;

public interface LoadingListener {
    void loadingStarted();
    void loadingEnded(int callerID);
    void loadingError(String error,int callerID);
}
