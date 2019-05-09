package com.example.paperlessquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.question.QuestionsList;
import com.example.paperlessquiz.quizbasics.QuizBasics;
import com.example.paperlessquiz.quizextras.QuizExtras;

public class D_PC_ConfirmSubmitAnswers extends AppCompatActivity {

    QuizExtras thisQuizExtras;
    QuizBasics thisQuizBasics;
    LoginEntity thisLoginEntity;
    QuestionsList allQuestions;
    TextView tvDisplayAnswers;
    String displayAnswers = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_pc_confirm_submit_answers);
        thisQuizBasics = (QuizBasics)getIntent().getSerializableExtra(QuizBasics.INTENT_EXTRA_NAME_THIS_QUIZ_BASICS);
        thisQuizExtras = (QuizExtras)getIntent().getSerializableExtra(QuizExtras.INTENT_EXTRA_NAME_THIS_QUIZ_EXTRAS);
        thisLoginEntity = (LoginEntity)getIntent().getSerializableExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_ENTITY);
        //allQuestions = (QuestionsList) getIntent().getSerializableExtra(QuestionsList.INTENT_PUT_EXTRA_NAME_THIS_ROUND_ANSWERS);
        String answer1 = (String) getIntent().getStringExtra(QuestionsList.INTENT_PUT_EXTRA_NAME_THIS_ROUND_ANSWERS);
        tvDisplayAnswers = (TextView)findViewById(R.id.tvDisplayAnswers);
 /*       for (int i=0;i<10;i++){
            displayAnswers = displayAnswers + " Question" + i + allQuestions.getQuestionsList().get(0).getThisAnswer();
        }
*/
        tvDisplayAnswers.setText(answer1);
    }
}
