package com.example.paperlessquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.paperlessquiz.adapters.QuizRoundsAdapter;
import com.example.paperlessquiz.google.access.GoogleAccess;
import com.example.paperlessquiz.google.access.GoogleAccessGet;
import com.example.paperlessquiz.google.access.LoadingListenerImpl;
import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.quizlistdata.QuizListData;
import com.example.paperlessquiz.quizextradata.QuizExtraData;
import com.example.paperlessquiz.round.GetRoundsLPL;
import com.example.paperlessquiz.round.Round;
import com.example.paperlessquiz.round.RoundParser;

public class D_PA_ShowRounds extends AppCompatActivity {

    QuizExtraData thisQuizExtraData;
    QuizListData thisQuizListData;
    LoginEntity thisLoginEntity;
    ListView lv_DisplayRoundsList;
    QuizRoundsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_pa_show_quiz_overview);

        thisQuizListData = (QuizListData)getIntent().getSerializableExtra(QuizListData.INTENT_EXTRA_NAME_THIS_QUIZ_BASICS);
        thisQuizExtraData = (QuizExtraData)getIntent().getSerializableExtra(QuizExtraData.INTENT_EXTRA_NAME_THIS_QUIZ_EXTRAS);
        thisLoginEntity = (LoginEntity)getIntent().getSerializableExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_ENTITY);
        String scriptParams = GoogleAccess.PARAMNAME_DOC_ID + thisQuizListData.getSheetDocID() + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_SHEET + RoundParser.ROUNDSLIST_TABNAME + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_GETDATA;
        lv_DisplayRoundsList = (ListView) findViewById(R.id.lv_display_rounds);
        adapter = new QuizRoundsAdapter(this);

        lv_DisplayRoundsList.setAdapter(adapter);
        lv_DisplayRoundsList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked, go to the A_SelectRole screen to allow the user to select the role for this quiz.
                // Pass the QuizListData object so the receiving screen can get the rest of the details

                Intent intent = new Intent(D_PA_ShowRounds.this, D_PB_ShowRoundQuestions.class);
                intent.putExtra(QuizListData.INTENT_EXTRA_NAME_THIS_QUIZ_BASICS, thisQuizListData);
                intent.putExtra(QuizExtraData.INTENT_EXTRA_NAME_THIS_QUIZ_EXTRAS, thisQuizExtraData);
                intent.putExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_ENTITY,thisLoginEntity);
                intent.putExtra(Round.INTENT_EXTRA_NAME_THIS_ROUND,adapter.getItem(position));
                startActivity(intent);

                //Toast.makeText(D_PA_ShowRounds.this, "Loading round", Toast.LENGTH_SHORT).show();

            }
        });

        GoogleAccessGet<Round> googleAccessGet = new GoogleAccessGet<Round>(this, scriptParams);
        googleAccessGet.getItems(new RoundParser(), new GetRoundsLPL(),
                new LoadingListenerImpl(this, "Please wait", "Loading rounds", "Something went wrong: "));
    }


}
