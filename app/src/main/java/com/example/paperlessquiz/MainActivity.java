package com.example.paperlessquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.paperlessquiz.adapters.QuizBasicsAdapter;
import com.example.paperlessquiz.google.access.GoogleAccess;
import com.example.paperlessquiz.google.access.GoogleAccessGet;
import com.example.paperlessquiz.google.access.LoadingListenerImpl;
import com.example.paperlessquiz.quizbasics.AddQuizBasicsToAdapterLPL;
import com.example.paperlessquiz.quizbasics.QuizBasics;
import com.example.paperlessquiz.quizbasics.QuizBasicsParser;

/*
This class/screen is the first screen of the app. It allows users to select a quiz from a list of available quiz'es.
The list of the available quizzes comes from a centra, hardcoded Google Sheet
Basic details of the selected quiz stored and passed around via a quizBasics variable
TODO: extract string resources + constants
TODO: layout
 */

public class MainActivity extends AppCompatActivity {
    ListView lv_QuizList;
    QuizBasicsAdapter adapter;
    String sheetName = QuizBasics.QUIZLIST_TABNAME;
    String scriptParams = GoogleAccess.PARAMNAME_DOC_ID + QuizBasics.QUIZLIST_DOC_ID + GoogleAccess.PARAM_CONCATENATOR +
            GoogleAccess.PARAMNAME_SHEET + QuizBasics.QUIZLIST_TABNAME + GoogleAccess.PARAM_CONCATENATOR +
            GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_GETDATA;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    lv_QuizList = (ListView) findViewById(R.id.lvQuizList);
    adapter = new QuizBasicsAdapter(this);

    lv_QuizList.setAdapter(adapter);
    lv_QuizList.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
            // When clicked, go to the A_SelectRole screen to allow the user to select the role for this quiz.
            // Pass the QuizBasics object so the receiving screen can get the rest of the details
            Intent intent = new Intent(MainActivity.this, A_SelectRole.class);
            intent.putExtra(QuizBasics.INTENT_EXTRA_NAME_THIS_QUIZ_BASICS,adapter.getItem(position));
            startActivity(intent);

        }
    });

    GoogleAccessGet<QuizBasics> googleAccessGet = new GoogleAccessGet<QuizBasics>(this, scriptParams);
    googleAccessGet.getItems(new QuizBasicsParser(), new AddQuizBasicsToAdapterLPL(adapter),
            new LoadingListenerImpl(this, "Please wait", "Loading quizzes", "Something went wrong: "));
  }
}
