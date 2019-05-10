package com.example.paperlessquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.paperlessquiz.adapters.ReviewAnswersAdapter;
import com.example.paperlessquiz.answer.Answer;
import com.example.paperlessquiz.google.access.GoogleAccess;
import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.question.QuestionsList;
import com.example.paperlessquiz.quizbasics.QuizBasics;
import com.example.paperlessquiz.quizextras.QuizExtras;

import java.util.ArrayList;
import java.util.List;

public class D_PC_ConfirmSubmitAnswers extends AppCompatActivity {

    QuizExtras thisQuizExtras;
    QuizBasics thisQuizBasics;
    LoginEntity thisLoginEntity;
    ArrayList<Answer> answers;
    ListView lvAnswers;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_pc_confirm_submit_answers);
        thisQuizBasics = (QuizBasics)getIntent().getSerializableExtra(QuizBasics.INTENT_EXTRA_NAME_THIS_QUIZ_BASICS);
        thisQuizExtras = (QuizExtras)getIntent().getSerializableExtra(QuizExtras.INTENT_EXTRA_NAME_THIS_QUIZ_EXTRAS);
        thisLoginEntity = (LoginEntity)getIntent().getSerializableExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_ENTITY);
        answers = (ArrayList<Answer>) getIntent().getSerializableExtra(QuestionsList.INTENT_PUT_EXTRA_NAME_THIS_ROUND_ANSWERS);
        lvAnswers = (ListView) findViewById(R.id.lv_DisplayAnswers);
        btnSubmit = (Button) findViewById(R.id.btnSubmitAnswers);
        ReviewAnswersAdapter adapter = new ReviewAnswersAdapter(this,answers);
        lvAnswers.setAdapter(adapter);

        btnSubmit.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        //Intent intent = new Intent(D_PC_ConfirmSubmitAnswers.this, D_PA_ShowRounds.class);
                        //intent.putExtra(QuizBasics.INTENT_EXTRA_NAME_THIS_QUIZ_BASICS, thisQuizBasics);
                        //intent.putExtra(QuizExtras.INTENT_EXTRA_NAME_THIS_QUIZ_EXTRAS, thisQuizExtras);
                        //intent.putExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_ENTITY, thisLoginEntity);
                        //intent.putExtra(QuestionsList.INTENT_PUT_EXTRA_NAME_THIS_ROUND_ANSWERS,adapter.getMyAnswers());
                        //GoogleAccessSet submitAnswers = new GoogleAccess();
                        //startActivity(intent);
                        finish();
                    }
                }
        );

    }
}
