package com.paperlessquiz.webrequest;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.paperlessquiz.parsers.JsonParser;
import com.paperlessquiz.googleaccess.ListParsedListener;
import com.paperlessquiz.googleaccess.LoadingListener;
import com.paperlessquiz.quiz.Quiz;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This class retrieves data via a HTTP GET request. The expected result is an array of JSON objects that are parsed
 * to a list of objects T via Parser class that is passed as argument to the getItems method.
 * Via the LoadingListener, actions can be done in the calling activity when loading is sompleted.
 * @param <T>
 */
public class HTTPGet<T> {
    private Context context;
    private String parameters;
    int debugLevel;

    public HTTPGet(Context context, String parameters) {
        this.context = context;
        this.parameters = parameters;
    }

    //For each type of object/specific use, you need to create an implementation of JsonParser and ListParsedListener
    public void getItems(JsonParser<T> parser, final ListParsedListener<T> listParsedListener, LoadingListener loadingListener) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Quiz.PHP_URL + parameters,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<T> items =  parseItems(response, parser);
                        listParsedListener.listParsed(items);
                        loadingListener.loadingEnded();
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

        int socketTimeOut = 5000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
        loadingListener.loadingStarted();
    }

    //Parse the resulting string in a list of objects T
    private ArrayList<T> parseItems(String jsonResponse, JsonParser<T> parser) {

        ArrayList<T> list = new ArrayList<T>();
        try {
            JSONArray jarray = new JSONArray(jsonResponse); // Format of the response should be [{property:"value", property:"value", ...}, ... ,{property:"value", property:"value", ...}]
            //Loop over the array and parse the object with the specific parser for this object
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
