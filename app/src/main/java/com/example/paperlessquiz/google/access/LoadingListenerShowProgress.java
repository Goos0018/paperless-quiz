package com.example.paperlessquiz.google.access;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

public class LoadingListenerShowProgress implements LoadingListener {

    //This loadinglistener will display a message while loading is in progress.
    //Cancellable indicates whether this message will block the interface until loading has finished or be cancellable
    private Context context;
    private ProgressDialog loading;
    private String loadingTitle;
    private String loadingMessage;
    private String errorMessage;
    private boolean cancellable;

    public LoadingListenerShowProgress(Context context, String loadingTitle, String loadingMessage, String errorMessage, boolean cancellable) {
        this.context = context;
        this.loadingTitle = loadingTitle;
        this.loadingMessage = loadingMessage;
        this.errorMessage = errorMessage;
        this.cancellable = cancellable;
    }

    @Override
    public void loadingStarted() {
        loading = ProgressDialog.show(context, loadingTitle, loadingMessage, false, cancellable);
    }

    @Override
    public void loadingEnded() {
        loading.dismiss();
    }

    @Override
    public void loadingError(String error) {
        Toast.makeText(context, errorMessage + error, Toast.LENGTH_LONG).show();
    }

}
