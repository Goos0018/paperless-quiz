package com.example.paperlessquiz.google.access;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

public class LoadingListenerImpl implements LoadingListener {

    private Context context;
    private ProgressDialog loading;
    private String loadingTitle;
    private String loadingMessage;
    private String errorMessage;

    public LoadingListenerImpl(Context context, String loadingTitle, String loadingMessage, String errorMessage) {
        this.context = context;
        this.loadingTitle = loadingTitle;
        this.loadingMessage = loadingMessage;
        this.errorMessage = errorMessage;
    }

    @Override
    public void loadingStarted() {
        loading = ProgressDialog.show(context, loadingTitle, loadingMessage, false, false);
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
