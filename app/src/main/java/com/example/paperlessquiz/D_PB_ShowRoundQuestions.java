package com.example.paperlessquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.paperlessquiz.adapters.QuizRoundsAdapter;
import com.example.paperlessquiz.adapters.RoundQuestionsAdapter;
import com.example.paperlessquiz.google.access.GoogleAccess;
import com.example.paperlessquiz.google.access.GoogleAccessGet;
import com.example.paperlessquiz.google.access.LoadingListenerImpl;
import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.question.AddQuestionsToRoundQuestionsAdapterLPL;
import com.example.paperlessquiz.question.Question;
import com.example.paperlessquiz.question.QuestionParser;
import com.example.paperlessquiz.quizbasics.QuizBasics;
import com.example.paperlessquiz.quizextras.QuizExtras;
import com.example.paperlessquiz.round.Round;

public class D_PB_ShowRoundQuestions extends AppCompatActivity {

    QuizExtras thisQuizExtras;
    QuizBasics thisQuizBasics;
    LoginEntity thisLoginEntity;
    Round thisRound;
    ListView lv_ShowRoundQuestions;
    RoundQuestionsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_pb_show_round_questions);
        thisQuizBasics = (QuizBasics)getIntent().getSerializableExtra(QuizBasics.INTENT_EXTRA_NAME_THIS_QUIZ_BASICS);
        thisQuizExtras = (QuizExtras)getIntent().getSerializableExtra(QuizExtras.INTENT_EXTRA_NAME_THIS_QUIZ_EXTRAS);
        thisLoginEntity = (LoginEntity)getIntent().getSerializableExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_ENTITY);
        thisRound=(Round) getIntent().getSerializableExtra(Round.INTENT_EXTRA_NAME_THIS_ROUND);
        String scriptParams = GoogleAccess.PARAMNAME_DOC_ID + thisQuizBasics.getSheetDocID() + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_SHEET + "Questions" + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_GETDATA;
        lv_ShowRoundQuestions = (ListView) findViewById(R.id.lv_show_round_questions);
        adapter = new RoundQuestionsAdapter(this);

        lv_ShowRoundQuestions.setAdapter(adapter);
        lv_ShowRoundQuestions.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked, go to the A_SelectRole screen to allow the user to select the role for this quiz.
                // Pass the QuizBasics object so the receiving screen can get the rest of the details

                /*Intent intent = new Intent(D_PA_ShowRounds.this, D_PB_ShowRoundQuestions.class);
                intent.putExtra(QuizBasics.INTENT_EXTRA_NAME_THIS_QUIZ_BASICS,thisQuizBasics);
                intent.putExtra(QuizExtras.INTENT_EXTRA_NAME_THIS_QUIZ_EXTRAS,thisQuizExtras);
                intent.putExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_ENTITY,thisLoginEntity);
                intent.putExtra(Round.INTENT_EXTRA_NAME_THIS_ROUND,adapter.getItem(position));
                startActivity(intent);
*/
                Toast.makeText(D_PB_ShowRoundQuestions.this, "Loading question", Toast.LENGTH_SHORT).show();

            }
        });


    GoogleAccessGet<Question> googleAccessGet = new GoogleAccessGet<Question>(this, scriptParams);
        googleAccessGet.getItems(new QuestionParser(), new AddQuestionsToRoundQuestionsAdapterLPL(adapter),
                new LoadingListenerImpl(this, "Please wait", "Loading questions", "Something went wrong: "));
    }
}
