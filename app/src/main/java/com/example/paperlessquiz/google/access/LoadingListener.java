package com.example.paperlessquiz.google.access;

public interface LoadingListener {
    void loadingStarted();
    void loadingEnded();
    void loadingError(String error);
}
