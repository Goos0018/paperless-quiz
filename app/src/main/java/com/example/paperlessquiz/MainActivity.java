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
lkjfhdkjlz
 */

public class MainActivity extends AppCompatActivity {
    ListView lv_QuizList;
    ArrayList<Quiz> list;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    lv_QuizList = (ListView) findViewById(R.id.lvQuizList);
    list = new ArrayList<Quiz>();

    Quiz quiz1 = new Quiz("Q001", "TTC meerdaal Quiz 7", "7de TTC Meerdaal Quiz in zaal de Kring, Kessel-Lo",
                "1LR6F-VJCNPFAhgibNspJJBRCDW_mlCz_PFkepSZYxx8",true);

    Quiz quiz2 = new Quiz("Q001", "Test quiz", "Gebruik deze quiz om te controleren dat de app behoorlijk werkt voor jou",
                "1LR6F-VJCNPFAhgibNspJJBRCDW_mlCz_PFkepSZYxx8",true);
    list.add(quiz1);
    list.add(quiz2);
    getItems(list);
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



    }

    public void getItems(ArrayList<Quiz> list) {

        loading =  ProgressDialog.show(this,"Loading","please wait",false,true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbxF4oneivWH9QUnOyEJRWWDNIfdaSft5idzfNWgz7USI0ZzKw_o/exec?DocID=1A4CGyeZZk2LW-xvh_P1dyeufZhV0qpBgCIQdrNEIDgk&Sheet=QuizList&action=getdata",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseItems(response,list);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Quiz quiz1 = new Quiz("Q001", "TTC meerdaal Quiz 7", "7de TTC Meerdaal Quis in zaal de Kring, Kessel-Lo",
                                "1LR6F-VJCNPFAhgibNspJJBRCDW_mlCz_PFkepSZYxx8",true);

                        Quiz quiz2 = new Quiz("Q001", "Test quiz", "Gebruik deze quis om te controleren dat de app behoorlijk werkt voor jou",
                                "1LR6F-VJCNPFAhgibNspJJBRCDW_mlCz_PFkepSZYxx8",true);
                        list.add(quiz1);

                    }
                }
        );

        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }


    private void parseItems(String jsonResponse, ArrayList<Quiz> list) {

        //ArrayList<HashMap<String, String>> list = new ArrayList<>();

        Quiz quiz1 = new Quiz("Q001", "TTC meerdaal Quiz 7", "7de TTC Meerdaal Quis in zaal de Kring, Kessel-Lo",
                "1LR6F-VJCNPFAhgibNspJJBRCDW_mlCz_PFkepSZYxx8",true);

        //Quiz quiz2 = new Quiz("Q001", "Test quiz", "Gebruik deze quis om te controleren dat de app behoorlijk werkt voor jou",
         //       "1LR6F-VJCNPFAhgibNspJJBRCDW_mlCz_PFkepSZYxx8",true);
        list.add(quiz1);


/*
        try {
            JSONObject jobj = new JSONObject(jsonResponse);
            JSONArray jarray = jobj.getJSONArray("items");


            for (int i = 0; i < jarray.length(); i++) {

                JSONObject jo = jarray.getJSONObject(i);


                Quiz quiz = new Quiz(jo.getString("QuizID"), jo.getString("QuizName"), jo.getString("QuizDescription"), jo.getString("QuizSheet"),true );

                list.add(quiz);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

/*
        adapter = new SimpleAdapter(this,list,R.layout.list_item_row,
                new String[]{"itemName","brand","price"},new int[]{R.id.tv_item_name,R.id.tv_brand,R.id.tv_price});


        listView.setAdapter(adapter);
        */
        loading.dismiss();
    }



}
