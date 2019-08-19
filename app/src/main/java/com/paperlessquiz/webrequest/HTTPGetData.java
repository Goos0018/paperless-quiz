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
import com.paperlessquiz.loadinglisteners.LoadingListener;
import com.paperlessquiz.quiz.QuizDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * This class executes a HTTP GET request (in practice a PHP script)
 * The result must be an array of JSON objects that are parsed
 * to a list of objects T via Parser class that is passed as argument to the getItems method.
 * No error handling is done, if this doesn't work, the app cannot work properly.
 *
 * @param <T>
 */
public class HTTPGetData<T> {
    private Context context;
    private String parameters;
    private int requestID;
    private ArrayList<T> resultsList;

    public HTTPGetData(Context context, String parameters, int requestID) {
        this.context = context;
        this.parameters = parameters;
        this.requestID = requestID;
    }

    //For each type of object/specific use, you need to create an implementation of JsonParser and ListParsedListener
    //For simple submit requetss where the answer is just confirmation, we use a String parser
    //public void getItems(JsonParser<T> parser, final ListParsedListener<T> listParsedListener, LoadingListener loadingListener) {
    public void getItems(JsonParser<T> parser, LoadingListener loadingListener) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, QuizDatabase.PHP_URL + parameters,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        resultsList = parseItems(response, parser);
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

    public ArrayList<T> getResultsList() {
        return resultsList;
    }
}
