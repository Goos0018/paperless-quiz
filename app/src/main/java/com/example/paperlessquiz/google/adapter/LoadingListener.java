package com.example.paperlessquiz.google.adapter;

public interface LoadingListener {
    void loadingStarted();
    void loadingEnded();
    void loadingError(String error);
}
