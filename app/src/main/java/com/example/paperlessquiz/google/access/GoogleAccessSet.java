package com.example.paperlessquiz.google.access;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class GoogleAccessSet {
    private Context context;
    private String parameters;
    private String result;
    int debugLevel;

    public GoogleAccessSet(Context context, String parameters,int debugLevel) {
        this.context = context;
        this.parameters = parameters;
        this.debugLevel=debugLevel;
    }

    //This method will add a line at the bottom of the sheet that was passed via parameters, using the other fields that were given
    public void setData(LoadingListener loadingListener) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GoogleAccess.SCRIPT_URL + parameters
                + GoogleAccess.PARAM_CONCATENATOR + GoogleAccess.PARAMNAME_DEBUGLEVEL + debugLevel,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                        JSONObject jo = new JSONObject(response);
                        result = jo.getString("result");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                        //TODO: add response listener for this + logging/messages
                        loadingListener.loadingEnded();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadingListener.loadingError("Error posting data: " + parameters);
                    }
                }
        );

        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
        loadingListener.loadingStarted();
    }

    public String getResult() {
        return result;
    }
}
