package com.example.paperlessquiz;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/*
This class/screen is the first screen of the app. It allows users to select a quiz from a list of available quiz'es.
 */

public class MainActivity extends AppCompatActivity {
    ListView lv_QuizList;
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

    String parameters = "DocID=1A4CGyeZZk2LW-xvh_P1dyeufZhV0qpBgCIQdrNEIDgk&Sheet=QuizList&action=getdata";
    GoogleAccess<Quiz> googleAccess = new GoogleAccess<Quiz>(this, parameters);
    googleAccess.getItems(new QuizParser(), new AddQuizToAdapterListParsedListener(adapter), new LoadingListenerImpl(this, "Please wait", "Loading quizzes", "Something went wrong: "));
  }
}
