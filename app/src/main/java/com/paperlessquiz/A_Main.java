package com.paperlessquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
        adapter = new QuizListAdapter(this);

        /*scriptParams = GoogleAccess.PARAMNAME_DOC_ID + QuizGenerator.QUIZLIST_DOC_ID + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_SHEET + QuizGenerator.SHEET_QUIZLIST + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_GETDATA;
        GoogleAccessGet<QuizListData> googleAccessGet = new GoogleAccessGet<QuizListData>(this, scriptParams);
        googleAccessGet.getItems(new QuizListDataParser(), new GetQuizListDataLPL(adapter),
                new LLShowProgressActWhenComplete(this, this.getString(R.string.loadingtitle), this.getString(R.string.loadingmsg_list),
                        this.getString(R.string.loadingerror),false));
        */

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
                //Pass the SheetId of the selected quiz so we can log further events there
                MyApplication.eventLogger.setDocID(adapter.getItem(position).getSheetDocID());
                startActivity(intent);
            }
        });

    }
}

