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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//This class is used to get data from a Google sheet.
public class GoogleAccessGet<T> {

    private static final String SCRIPT_URL = "https://script.google.com/macros/s/AKfycbxF4oneivWH9QUnOyEJRWWDNIfdaSft5idzfNWgz7USI0ZzKw_o/exec?";
    private Context context;
    private String parameters;

    public GoogleAccessGet(Context context, String parameters) {
        this.context = context;
        this.parameters = parameters;
    }

    //This method will return
    //  - either a list of objects T (in that case objectToUpdate is Null
    //  - or a list containing one single object T that is based on objectToUpdate
    //For each type of object/specific use, you need to create an implementation of JsonParser and ListParsedListener
    public void getItems(JsonParser<T> parser, final ListParsedListener<T> listParsedListener, LoadingListener loadingListener) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, SCRIPT_URL+parameters,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<T> items =  parseItems(response, parser);
                        loadingListener.loadingEnded();
                        listParsedListener.listParsed(items);
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


    private ArrayList<T> parseItems(String jsonResponse, JsonParser<T> parser) {

        ArrayList<T> list = new ArrayList<T>();
        try {
            JSONObject jobj = new JSONObject(jsonResponse);
            JSONArray jarray = jobj.getJSONArray("items");


            for (int i = 0; i < jarray.length(); i++) {

                JSONObject jo = jarray.getJSONObject(i);

                T object = parser.parse(jo);
                list.add(object);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

}
