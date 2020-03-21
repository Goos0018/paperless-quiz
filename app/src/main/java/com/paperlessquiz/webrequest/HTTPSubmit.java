package com.paperlessquiz.webrequest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.paperlessquiz.MyApplication;
import com.paperlessquiz.loadinglisteners.LoadingListener;
import com.paperlessquiz.quiz.QuizDatabase;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * The class is used to send a request to the back-end. Expected result is indication of success or failure and some text explanation
 */

public class HTTPSubmit {
    private Context context;
    private String parameters;
    private int requestID;
    private String explanation;
    private boolean requestOK;

    //Fieldnames that are used by the PHP Script to return result of the action - These should match with what is defined in the back-end PHP script constants.php
    public static final String FIELDNAME_RESULT = "result";
    public static final String FIELDNAME_DATA = "explanation";

    public HTTPSubmit(Context context, String parameters, int requestID) {
        this.context = context;
        this.parameters = parameters;
        this.requestID = requestID;
    }

    public void sendRequest(LoadingListener loadingListener) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, QuizDatabase.PHP_URL + parameters,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //The response first indicates success or failure, the data itself is in the Explanation
                        try {
                            JSONObject jo = new JSONObject(response);
                            int resultOK = jo.getInt(FIELDNAME_RESULT);
                            explanation = jo.getString(FIELDNAME_DATA);
                            if (!(resultOK == 1)) {
                                requestOK = false;
                                MyApplication.showError((Activity)context,"Fout: " + explanation);
                                //21/3: do show a toast here, should be handled in the activity
                                //Toast toast = Toast.makeText(context, "Error: " + explanation, Toast.LENGTH_LONG);
                                //TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                                //v.setBackgroundColor(Color.RED);
                                //toast.show();
                            } else {
                                requestOK = true;
                            }
                        } catch (JSONException e) {
                            requestOK = false;
                            explanation = e.getMessage();
                            e.printStackTrace();
                        }
                        loadingListener.loadingEnded(requestID);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadingListener.loadingError(error.toString(), requestID);
                    }
                }
        );

        int socketTimeOut = 5000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
        loadingListener.loadingStarted();
    }

    public String getExplanation() {
        return explanation;
    }

    public boolean isRequestOK() {
        return requestOK;
    }
}
