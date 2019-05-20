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

//This class gets the data in Sheet and return it as an array of objects T
public class GoogleAccessGetSheetData<T> {
    private Context context;
    private String parameters;
    private boolean loadingStarted;
    private boolean loadingFinished;
    private boolean loadingFailed;
    private ArrayList<T> result;
    private String docID;
    private String sheet;

    public GoogleAccessGetSheetData(Context context, String docID, String sheet) {
        this.context = context;
        this.parameters = GoogleAccess.PARAMNAME_DOC_ID + docID + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_SHEET + sheet + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_GETDATA;
        this.loadingStarted = false;
        this.loadingFinished = false;
        this.loadingFailed = false;
        this.docID = docID;
        this.sheet = sheet;

    }

    //This method will return
    //  - either a list of objects T (in that case objectToUpdate is Null
    //  - or a list containing one single object T that is based on objectToUpdate
    //For each type of object/specific use, you need to create an implementation of JsonParser and ListParsedListener
    public void getItems(JsonParser<T> parser) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, GoogleAccess.SCRIPT_URL+parameters,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<T> items =  parseItems(response, parser);
                        result = (ArrayList)items;
                        loadingFinished = true;
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        loadingFailed = true;
                    }
                }
        );

        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
        loadingStarted = true;
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

    public boolean isLoadingStarted() {
        return loadingStarted;
    }

    public boolean isLoadingFinished() {
        return loadingFinished;
    }

    public boolean isLoadingFailed() {
        return loadingFailed;
    }
}
