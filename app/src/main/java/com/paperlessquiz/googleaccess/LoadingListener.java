package com.paperlessquiz.googleaccess;

public interface LoadingListener {
    void loadingStarted();
    void loadingEnded();
    void loadingError(String error);
}
