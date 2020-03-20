package com.paperlessquiz.loadinglisteners;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.paperlessquiz.MyApplication;
import com.paperlessquiz.R;
import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.webrequest.EventLogger;

/**
 * Loading listener that is silent unless something goes wrong
 */
public class LLSilentActWhenComplete implements LoadingListener {

    private Context context;
    private String errorMessage;

    LoadingActivity activity;

    public LLSilentActWhenComplete(Context context, String errorMessage) {
        this.context = context;
        this.errorMessage = errorMessage;
        activity = (LoadingActivity) context;
    }

    @Override
    public void loadingStarted() {
    }

    @Override
    public void loadingEnded(int callerID) {
        activity.loadingComplete(callerID);
    }

    @Override
    public void loadingError(String error, int callerID) {
        MyApplication.logMessage(context,QuizDatabase.LOGLEVEL_ERROR, "Loading error - request ID" + callerID + ": " + errorMessage + " (" + error + ")");
        String displayError;
        if (errorMessage.equals("")){
            displayError = error;
        }
        else
        {
            displayError=errorMessage;
        }
        //Toast.makeText(context, displayError, Toast.LENGTH_LONG).show();
        MyApplication.showError((Activity) context, displayError);
        /*
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_container));

        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText("This is a custom toast");

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    */





    }
}