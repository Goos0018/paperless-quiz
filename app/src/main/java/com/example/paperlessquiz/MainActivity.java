package com.example.paperlessquiz;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;

/*
This class/screen is the first screen of the app. It allows users to select a quiz from a list of available quiz'es.
 */

public class MainActivity extends AppCompatActivity {
    ListView lv_QuizList;
    ProgressDialog loading;
    QuizAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    lv_QuizList = (ListView) findViewById(R.id.lvQuizList);
    adapter = new QuizAdapter(this);

    lv_QuizList.setAdapter(adapter);
    lv_QuizList.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
            // When clicked, show a toast with the TextView text
            Toast.makeText(MainActivity.this,adapter.getItem(position).getTitle(),
                    Toast.LENGTH_SHORT).show();
            //Load Quiz from adapter.getItem(position).getSpreadsheetDocId()
        }
    });

    getItems(new QuizParser(), adapter);
    /*
    QuizAdapter adapter = new QuizAdapter(this, list);
    //ArrayAdapter<Quiz> adapter = new ArrayAdapter<Quiz>(this, android.R.layout.simple_list_item_1,list);


    lv_QuizList.setAdapter(adapter);
    lv_QuizList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                // When clicked, show a toast with the TextView text
                Toast.makeText(MainActivity.this,list.get(position).getTitle(),
                        Toast.LENGTH_SHORT).show();
            }
        });


*/
    }

    public void getItems(JsonParser<Quiz> parser, ArrayAdapter<Quiz> arrayAdapter) {

        loading =  ProgressDialog.show(this,"Loading","please wait",false,true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbxF4oneivWH9QUnOyEJRWWDNIfdaSft5idzfNWgz7USI0ZzKw_o/exec?DocID=1A4CGyeZZk2LW-xvh_P1dyeufZhV0qpBgCIQdrNEIDgk&Sheet=QuizList&action=getdata",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseItems(response, parser, arrayAdapter);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        // TOOD: log error
                    }
                }
        );

        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }


    private void parseItems(String jsonResponse, JsonParser<Quiz> parser, ArrayAdapter<Quiz> arrayAdapter) {

        ArrayList<Quiz> list = new ArrayList<Quiz>();
        try {
            JSONObject jobj = new JSONObject(jsonResponse);
            JSONArray jarray = jobj.getJSONArray("items");


            for (int i = 0; i < jarray.length(); i++) {

                JSONObject jo = jarray.getJSONObject(i);

                Quiz quiz = parser.parse(jo);
                list.add(quiz);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        arrayAdapter.addAll(list);
        //ArrayAdapter<Quiz> adapter = new ArrayAdapter<Quiz>(this, android.R.layout.simple_list_item_1,list);

        loading.dismiss();
    }
}
