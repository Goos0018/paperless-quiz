package com.paperlessquiz.googleaccess;

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
import com.paperlessquiz.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

public class GoogleAccessSet {
    /*
    private Context context;
    private String parameters;
    private String resultExplanation; //explanation of what was the result
    private boolean resultOK;             //indicates whether the results was OK or not
    int debugLevel;

    public GoogleAccessSet(Context context, String parameters) {
        this.context = context;
        this.parameters = parameters;
        //this.debugLevel = debugLevel;
    }

    //This method will add a line at the bottom of the sheet that was passed via parameters, using the other fields that were given
    public void setData(LoadingListener loadingListener) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GoogleAccess.SCRIPT_URL + parameters
                + GoogleAccess.PARAM_CONCATENATOR + GoogleAccess.PARAMNAME_DEBUGLEVEL + MyApplication.getDebugLevel()
                + GoogleAccess.PARAM_CONCATENATOR + GoogleAccess.PARAMNAME_KEEPLOGS + MyApplication.isKeepLogs(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jo = new JSONObject(response);
                            resultOK = jo.getBoolean(GoogleAccess.GASET_FIELDNAME_RESULT);
                            resultExplanation = jo.getString(GoogleAccess.GASET_FIELDNAME_RESULT_EXPLANATION);
                            MyApplication.googleLog.add(Boolean.toString(resultOK) + ": " + resultExplanation);
                            if (!resultOK){
                                Toast toast = Toast.makeText(context, "Error: " + resultExplanation, Toast.LENGTH_LONG);
                                TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                                v.setBackgroundColor(Color.RED);
                                toast.show();
                                //toast.getView().setBackgroundColor(Color.RED)
                                //Toast.makeText(context, "Error: " + resultExplanation, Toast.LENGTH_LONG).getView().setBackgroundColor(Color.RED).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        loadingListener.loadingEnded(0);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadingListener.loadingError("Error posting data: " + parameters,0);
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

    public String getResultExplanation() {
        return resultExplanation;
    }

    public boolean isResultOK() {
        return resultOK;
    }
    */
}
