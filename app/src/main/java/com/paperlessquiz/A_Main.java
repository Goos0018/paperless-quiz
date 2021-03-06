package com.paperlessquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.paperlessquiz.adapters.QuizListAdapter;
import com.paperlessquiz.loadinglisteners.LLShowProgressActWhenComplete;
import com.paperlessquiz.loadinglisteners.LoadingActivity;
import com.paperlessquiz.quiz.Quiz;
import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.quiz.QuizListData;
import com.paperlessquiz.parsers.QuizListDataParser;
import com.paperlessquiz.webrequest.HTTPGetData;

/**
 * This class/screen is the first screen of the app. It allows users to select a quiz from a list of available quiz'es.
 * The list of the available quizzes is loaded from the central SQL db, no authentication is needed to retrieve it
 * Basic details of the selected quiz are stored in the central Quiz object
 */

public class A_Main extends AppCompatActivity implements LoadingActivity {
    Quiz thisQuiz = MyApplication.theQuiz;
    ListView lv_QuizList;
    TextView tvVersion;
    QuizListAdapter adapter;
    //String scriptParams;
    HTTPGetData<QuizListData> request;

    @Override
    public void loadingComplete(int requestID) {
        //Load the list of quizzes into the adapter
        adapter.addAll(request.getResultsList());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_a_main);

        lv_QuizList = (ListView) findViewById(R.id.lvQuizList);
        tvVersion = findViewById(R.id.tvVersion);
        tvVersion.setText("Versie "+ BuildConfig.VERSION_NAME);
        adapter = new QuizListAdapter(this);

        request = new HTTPGetData<QuizListData>(this, QuizDatabase.SCRIPT_GET_QUIZLIST, QuizDatabase.REQUEST_ID_GET_QUIZLIST);
        request.getItems(new QuizListDataParser(),
                new LLShowProgressActWhenComplete(this, this.getString(R.string.loadingtitle), this.getString(R.string.loadingmsg_list),
                        this.getString(R.string.loadingerror),false));

        lv_QuizList.setAdapter(adapter);
        lv_QuizList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Load the data from the selected Quiz into the thisQuiz object
                thisQuiz.setListData(adapter.getItem(position));
                Intent intent = new Intent(A_Main.this, A_SelectRole.class);
                startActivity(intent);
            }
        });

    }
}

