package com.example.paperlessquiz.google.adapter;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GoogleAccess<T> {

    private static final String SCRIPT_URL = "https://script.google.com/macros/s/AKfycbxF4oneivWH9QUnOyEJRWWDNIfdaSft5idzfNWgz7USI0ZzKw_o/exec?";
    private Context context;
    private String parameters;

    public GoogleAccess(Context context, String parameters) {
        this.context = context;
        this.parameters = parameters;
    }

    public void getItems(JsonParser<T> parser, final ListParsedListener<T> parserListener, LoadingListener loadingListener) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, SCRIPT_URL+parameters,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<T> items =  parseItems(response, parser, parserListener);
                        loadingListener.loadingEnded();
                        parserListener.listParsed(items);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        loadingListener.loadingError(error.toString());
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


    private ArrayList<T> parseItems(String jsonResponse, JsonParser<T> parser, ListParsedListener<T> parserEventListener) {

        ArrayList<T> list = new ArrayList<T>();
        try {
            JSONObject jobj = new JSONObject(jsonResponse);
            JSONArray jarray = jobj.getJSONArray("items");


            for (int i = 0; i < jarray.length(); i++) {

                JSONObject jo = jarray.getJSONObject(i);

                T quiz = parser.parse(jo);
                list.add(quiz);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

}
