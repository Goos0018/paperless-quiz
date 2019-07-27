package com.paperlessquiz.webrequest;

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
import com.paperlessquiz.parsers.JsonParser;
import com.paperlessquiz.googleaccess.LoadingListener;
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

    int debugLevel;

    /*
    //Fieldnames that are used by the SetData GScript to return result of the action
    public static final String FIELDNAME_RESULT = "result";
    public static final String FIELDNAME_DATA = "explanation";
    public static final int TYPE_GET = 0;
    public static final int TYPE_SUBMIT = 1;
*/

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
                        //The response first indicates success or failure, the data itself is in the Explanation
                        //try {
                            /*
                            JSONObject jo = new JSONObject(response);
                            int resultOK = jo.getInt(FIELDNAME_RESULT);
                            String data = jo.getString(FIELDNAME_DATA);
                            //MyApplication.googleLog.add(Boolean.toString(resultOK) + ": " + resultExplanation);
                            if (!(resultOK == 1)) {
                                Toast toast = Toast.makeText(context, "Error: " + data, Toast.LENGTH_LONG);
                                TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                                v.setBackgroundColor(Color.RED);
                                toast.show();
                                //toast.getView().setBackgroundColor(Color.RED)
                                //Toast.makeText(context, "Error: " + resultExplanation, Toast.LENGTH_LONG).getView().setBackgroundColor(Color.RED).show();
                            } else {
                            */
                                resultsList = parseItems(response, parser);
                                loadingListener.loadingEnded(requestID);
                            //}
                        //} catch (JSONException e) {
                        //    e.printStackTrace();
                        //}
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
