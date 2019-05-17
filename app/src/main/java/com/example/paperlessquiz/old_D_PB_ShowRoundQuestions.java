package com.example.paperlessquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.paperlessquiz.adapters.QuestionsAdapter;
import com.example.paperlessquiz.answer.Answer;
import com.example.paperlessquiz.google.access.GoogleAccess;
import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.question.QuestionsList;
import com.example.paperlessquiz.quiz.Quiz;
import com.example.paperlessquiz.round.Round;

import java.util.ArrayList;

public class old_D_PB_ShowRoundQuestions extends AppCompatActivity {

    Quiz thisQuiz;
    LoginEntity thisLoginEntity;
    ArrayList<Answer> answers;
    //String answer1;
    Round thisRound;
    ListView lv_ShowRoundQuestions;
    QuestionsAdapter adapter;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_pb_show_round_questions);

        thisQuiz = (Quiz)getIntent().getSerializableExtra(Quiz.INTENT_EXTRANAME_THIS_QUIZ);
        thisLoginEntity = (LoginEntity)getIntent().getSerializableExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_ENTITY);
        thisRound=(Round) getIntent().getSerializableExtra(Round.INTENT_EXTRA_NAME_THIS_ROUND);
        String scriptParams = GoogleAccess.PARAMNAME_DOC_ID + thisQuiz.getListData().getSheetDocID() + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_SHEET + "Questions" + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_GETDATA;
        btnSubmit=(Button) findViewById(R.id.btnSubmit);
        lv_ShowRoundQuestions = (ListView) findViewById(R.id.lv_show_round_questions);
        adapter = new QuestionsAdapter(this,thisRound);
        //final GetQuestionsLPL listParsedListener = new GetQuestionsLPL(adapter, allQuestions,answer1);
        //final GetQuestionsLPL listParsedListener = new GetQuestionsLPL(adapter);
        //final GetQuestionsLPL listParsedListener = new GetQuestionsLPL();
        lv_ShowRoundQuestions.setAdapter(adapter);
        //lv_ShowRoundQuestions.setOnFocusChangeListener();
        lv_ShowRoundQuestions.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked, go to the A_SelectRole screen to allow the user to select the role for this quiz.
                // Pass the QuizListData object so the receiving screen can get the rest of the details
/*
                Intent intent = new Intent(old_D_PB_ShowRoundQuestions.this, D_PC_ConfirmSubmitAnswers.class);
                intent.putExtra(Quiz.INTENT_EXTRANAME_THIS_QUIZ,thisQuiz);
                intent.putExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_ENTITY,thisLoginEntity);
                intent.putExtra("Question",adapter.getItem(position));
                startActivity(intent);

                //Toast.makeText(old_D_PB_ShowRoundQuestions.this, "Loading question", Toast.LENGTH_SHORT).show();
*/
            }
        });
        btnSubmit.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(old_D_PB_ShowRoundQuestions.this, D_PC_ConfirmSubmitAnswers.class);
                        intent.putExtra(Quiz.INTENT_EXTRANAME_THIS_QUIZ, thisQuiz);
                        intent.putExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_ENTITY, thisLoginEntity);
                        //intent.putExtra(QuestionsList.INTENT_PUT_EXTRA_NAME_THIS_ROUND_ANSWERS, listParsedListener.getQuestionsList());
                        intent.putExtra(QuestionsList.INTENT_PUT_EXTRA_NAME_THIS_ROUND_ANSWERS,adapter.getMyAnswers());
                        startActivity(intent);
                    }
                }
        );

        //GoogleAccessGet<Question> googleAccessGet = new GoogleAccessGet<Question>(this, scriptParams);
        //googleAccessGet.getItems(new QuestionParser(), listParsedListener,
         //       new LoadingListenerImpl(this, "Please wait", "Loading questions", "Something went wrong: "));

    }
}
