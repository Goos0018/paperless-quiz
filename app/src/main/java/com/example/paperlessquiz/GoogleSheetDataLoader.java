package com.example.paperlessquiz;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class GoogleSheetDataLoader
{
    // This contains the URL of the Google script we use to access the Google sheets that are passed
    final String ScriptURL = "https://script.google.com/macros/s/AKfycbxF4oneivWH9QUnOyEJRWWDNIfdaSft5idzfNWgz7USI0ZzKw_o/exec?";
    private String result;
    private Context context;

    public GoogleSheetDataLoader(Context context)
    {
        this.context=context;
        this.result="Initialized";
    }
    //TO DO: define methods Public String GetData(String arguments) and Public String SetData(arguments)

    public void GetData(String argumentsToPass)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ScriptURL + argumentsToPass,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        result = response;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        result = error.toString();
                    }
                }
        );

       /* int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);*/

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }

}
