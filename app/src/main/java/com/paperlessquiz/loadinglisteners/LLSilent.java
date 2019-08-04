package com.paperlessquiz.loadinglisteners;

import com.paperlessquiz.loadinglisteners.LoadingListener;

/**
 * This loadinglistener simply does nothing - allows for silent "fire and forget" logging
 */
public class LLSilent implements LoadingListener {

    public LLSilent() {
    }

    @Override
    public void loadingStarted() {
    }

    @Override
    public void loadingEnded(int callerID) {
    }

    @Override
    public void loadingError(String error, int callerID) {
    }
}