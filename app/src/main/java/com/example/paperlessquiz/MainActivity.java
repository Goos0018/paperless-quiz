package com.example.paperlessquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.paperlessquiz.adapters.QuizListAdapter;
import com.example.paperlessquiz.google.access.GoogleAccess;
import com.example.paperlessquiz.google.access.GoogleAccessGet;
import com.example.paperlessquiz.google.access.LoadingActivity;
import com.example.paperlessquiz.google.access.LoadingListenerShowProgress;
import com.example.paperlessquiz.quiz.Quiz;
import com.example.paperlessquiz.quizlistdata.GetQuizListDataLPL;
import com.example.paperlessquiz.quizlistdata.QuizListData;
import com.example.paperlessquiz.quizlistdata.QuizListDataParser;

/*
This class/screen is the first screen of the app. It allows users to select a quiz from a list of available quiz'es.
The list of the available quizzes comes from a central, hardcoded Google Sheet (docID is in the GoogleAccess class
Basic details of the selected quiz are stored and passed via a thisQuiz variable
TODO: extract string resources + constants
TODO: layout
 */

public class MainActivity extends AppCompatActivity implements LoadingActivity {
    Quiz thisQuiz = MyApplication.theQuiz;
    ListView lv_QuizList;
    QuizListAdapter adapter;
    String scriptParams;

    @Override
    public void loadingComplete() {
        //nothing to do here
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv_QuizList = (ListView) findViewById(R.id.lvQuizList);
        adapter = new QuizListAdapter(this);
        //Load the list of quizzes into the adapter
        scriptParams = GoogleAccess.PARAMNAME_DOC_ID + GoogleAccess.QUIZLIST_DOC_ID + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_SHEET + GoogleAccess.QUIZLIST_TABNAME + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_GETDATA;
        GoogleAccessGet<QuizListData> googleAccessGet = new GoogleAccessGet<QuizListData>(this, scriptParams, thisQuiz.getAdditionalData().getDebugLevel());
        googleAccessGet.getItems(new QuizListDataParser(), new GetQuizListDataLPL(adapter),
                new LoadingListenerShowProgress(this, "Please wait", "Loading list of available quizzes",
                        "Something went wrong: ",false));

        lv_QuizList.setAdapter(adapter);
        lv_QuizList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Load the data from the selected Quiz into the thisQuiz object
                //thisQuiz.setListData(adapter.getItem(position));
                MyApplication.theQuiz.setListData(adapter.getItem(position));
                thisQuiz=MyApplication.theQuiz;
                Intent intent = new Intent(MainActivity.this, A_SelectRole.class);
                //intent.putExtra(Quiz.INTENT_EXTRANAME_THIS_QUIZ, thisQuiz);
                MyApplication.eventLogger.setDocID(adapter.getItem(position).getSheetDocID());
                startActivity(intent);
            }
        });

    }
}

