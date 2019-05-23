package com.example.paperlessquiz.xxxObsolete;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.paperlessquiz.R;
import com.example.paperlessquiz.adapters.ReviewAnswersAdapter;
import com.example.paperlessquiz.answer.Answer;
import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.question.QuestionsList;
import com.example.paperlessquiz.quizextradata.QuizExtraData;
import com.example.paperlessquiz.quizlistdata.QuizListData;

import java.util.ArrayList;

public class D_PC_ConfirmSubmitAnswers extends AppCompatActivity {

    /*


    QuizExtraData thisQuizExtraData;
    QuizListData thisQuizListData;
    LoginEntity thisLoginEntity;
    ArrayList<Answer> answers;
    ListView lvAnswers;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_pc_confirm_submit_answers);
        thisQuizListData = (QuizListData)getIntent().getSerializableExtra(QuizListData.INTENT_EXTRA_NAME_THIS_QUIZ_BASICS);
        thisQuizExtraData = (QuizExtraData)getIntent().getSerializableExtra(QuizExtraData.INTENT_EXTRA_NAME_THIS_QUIZ_EXTRAS);
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
                        //Intent intent = new Intent(D_PC_ConfirmSubmitAnswers.this, old_D_PA_ShowRounds.class);
                        //intent.putExtra(QuizListData.INTENT_EXTRA_NAME_THIS_QUIZ_BASICS, thisQuizListData);
                        //intent.putExtra(QuizExtraData.INTENT_EXTRA_NAME_THIS_QUIZ_EXTRAS, thisQuizExtraData);
                        //intent.putExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_ENTITY, thisLoginEntity);
                        //intent.putExtra(QuestionsList.INTENT_PUT_EXTRA_NAME_THIS_ROUND_ANSWERS,adapter.getMyAnswers());
                        //GoogleAccessSet submitAnswers = new GoogleAccess();
                        //startActivity(intent);
                        finish();
                    }
                }
        );

    }
    */
}
