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

import java.util.List;

public class GoogleAccessSet {
    private Context context;
    private String parameters;

    public GoogleAccessSet(Context context, String parameters) {
        this.context = context;
        this.parameters = parameters;
    }

    //This method will add a line at the bottom of the sheet that was passed via parameters, using the other fields that were given
    public void setData(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GoogleAccess.SCRIPT_URL+parameters,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //TODO: add response listener for this + logging/messages

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                    }
                }
        );

        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }


}
