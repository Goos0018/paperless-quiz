package com.paperlessquiz.loadinglisteners;

public interface LoadingActivity {

    /**
     * Stuff to do when (re)loading data from theGoogle sheet is completed
     */
    public void loadingComplete(int requestId);

}

