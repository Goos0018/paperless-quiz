package com.example.paperlessquiz;

public interface LoadingListener {
    void loadingStarted();
    void loadingEnded();
    void loadingError(String error);
}
