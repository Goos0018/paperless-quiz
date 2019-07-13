package com.paperlessquiz.googleaccess;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.paperlessquiz.MyApplication;
import com.paperlessquiz.parsers.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//This class is used to get data from a Google sheet.
//TODO: foresee proper tracking and error handling
public class GoogleAccessGet<T> {

    private Context context;
    private String parameters;
    int debugLevel;

    public GoogleAccessGet(Context context, String parameters) {
        this.context = context;
        this.parameters = parameters;
        //this.debugLevel=debugLevel;
    }

    //For each type of object/specific use, you need to create an implementation of JsonParser and ListParsedListener
    public void getItems(JsonParser<T> parser, final ListParsedListener<T> listParsedListener, LoadingListener loadingListener) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, GoogleAccess.SCRIPT_URL+parameters +
                GoogleAccess.PARAM_CONCATENATOR + GoogleAccess.PARAMNAME_DEBUGLEVEL + MyApplication.getDebugLevel()
                + GoogleAccess.PARAM_CONCATENATOR + GoogleAccess.PARAMNAME_KEEPLOGS + MyApplication.isKeepLogs(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<T> items =  parseItems(response, parser);
                        listParsedListener.listParsed(items);
                        loadingListener.loadingEnded(0);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        loadingListener.loadingError(error.toString(),0);
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
