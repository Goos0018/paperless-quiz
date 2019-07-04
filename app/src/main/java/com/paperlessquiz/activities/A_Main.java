package com.paperlessquiz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.paperlessquiz.A_SelectRole;
import com.paperlessquiz.MyApplication;
import com.example.paperlessquiz.R;
import com.paperlessquiz.adapters.QuizListAdapter;
import com.paperlessquiz.googleaccess.GoogleAccess;
import com.paperlessquiz.googleaccess.GoogleAccessGet;
import com.paperlessquiz.googleaccess.LoadingActivity;
import com.paperlessquiz.googleaccess.LoadingListenerShowProgress;
import com.paperlessquiz.quiz.Quiz;
import com.paperlessquiz.quiz.QuizGenerator;
import com.paperlessquiz.quizlistdata.GetQuizListDataLPL;
import com.paperlessquiz.quizlistdata.QuizListData;
import com.paperlessquiz.quizlistdata.QuizListDataParser;

/*
This class/screen is the first screen of the app. It allows users to select a quiz from a list of available quiz'es.
The list of the available quizzes comes from a central Google Sheet (docID is in the GoogleAccess class
Basic details of the selected quiz are stored and passed via a thisQuiz variable
TODO: extract string resources + constants
TODO: layout
 */

public class A_Main extends AppCompatActivity implements LoadingActivity {
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
        setContentView(R.layout.act_a_main);

        lv_QuizList = (ListView) findViewById(R.id.lvQuizList);
        adapter = new QuizListAdapter(this);
        //Load the list of quizzes into the adapter
        scriptParams = GoogleAccess.PARAMNAME_DOC_ID + QuizGenerator.QUIZLIST_DOC_ID + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_SHEET + QuizGenerator.SHEET_QUIZLIST + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_GETDATA;
        GoogleAccessGet<QuizListData> googleAccessGet = new GoogleAccessGet<QuizListData>(this, scriptParams);
        googleAccessGet.getItems(new QuizListDataParser(), new GetQuizListDataLPL(adapter),
                new LoadingListenerShowProgress(this, this.getString(R.string.loadingtitle), this.getString(R.string.loadingmsg_list),
                        this.getString(R.string.loadingerror),false));

        lv_QuizList.setAdapter(adapter);
        lv_QuizList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Load the data from the selected Quiz into the thisQuiz object
                thisQuiz.setListData(adapter.getItem(position));
                MyApplication.theQuiz.setListData(adapter.getItem(position));
                thisQuiz=MyApplication.theQuiz;
                Intent intent = new Intent(A_Main.this, A_SelectRole.class);
                //Pass the SheetId of the selected quiz so we can log further events there
                MyApplication.eventLogger.setDocID(adapter.getItem(position).getSheetDocID());
                startActivity(intent);
            }
        });

    }
}

