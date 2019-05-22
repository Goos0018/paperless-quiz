package com.example.paperlessquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.paperlessquiz.answer.Answer;
import com.example.paperlessquiz.google.access.GoogleAccess;
import com.example.paperlessquiz.google.access.GoogleAccessAddLine;
import com.example.paperlessquiz.question.Question;
import com.example.paperlessquiz.quiz.Quiz;
import com.example.paperlessquiz.quiz.QuizLoader;
import com.example.paperlessquiz.spinners.QuestionSpinner;
import com.example.paperlessquiz.spinners.RoundSpinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class C_ParticipantHome extends AppCompatActivity {
    Quiz thisQuiz;
    RoundSpinner roundSpinner;
    QuestionSpinner questionSpinner;
    TextView tvRoundName, tvRoundDescription, tvQuestionName, tvQuestionDescription, tvDisplayAnswers;
    EditText etAnswer;
    Button btnRndUp, btnRndDown, btnQuestionUp, btnQuestionDown, btnSubmit;
    LinearLayout displayLayout, answerLayout;

    private void refresh() {
        if (thisQuiz.getRound(roundSpinner.getPosition()).AcceptsAnswers()) {
            answerLayout.setVisibility(View.VISIBLE);
        } else {
            answerLayout.setVisibility((View.INVISIBLE));
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_act_participant_home);

        thisQuiz = (Quiz) getIntent().getSerializableExtra(Quiz.INTENT_EXTRANAME_THIS_QUIZ);
        tvQuestionName = findViewById(R.id.tvQuestionName);
        tvQuestionDescription = findViewById(R.id.tvQuestionDescription);
        tvRoundName = findViewById(R.id.tvRoundName);
        tvRoundDescription = findViewById(R.id.tvRoundDescription);
        tvDisplayAnswers = findViewById(R.id.tvDisplayAnswers);
        etAnswer = findViewById(R.id.etAnswer);
        btnQuestionDown = findViewById(R.id.btnQuestionDown);
        btnQuestionUp = findViewById(R.id.btnQuestionUp);
        btnRndDown = findViewById(R.id.btnRndDown);
        btnRndUp = findViewById(R.id.btnRndUp);
        btnSubmit = findViewById(R.id.btnSubmit);
        displayLayout = findViewById(R.id.llDisplay);
        answerLayout = findViewById(R.id.llAnswers);

        questionSpinner = new QuestionSpinner(thisQuiz.getAllQuestionsPerRound(), tvQuestionName, tvQuestionDescription, tvDisplayAnswers,
                thisQuiz.getMyAnswers(), etAnswer, 0);
        roundSpinner = new RoundSpinner(thisQuiz.getRounds(), tvRoundName, tvRoundDescription, questionSpinner);

        //displayLayout.setVisibility(LinearLayout.INVISIBLE);
        //questionSpinner.positionChanged();

        btnRndDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roundSpinner.moveDown();
                refresh();
            }
        });

        btnRndUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roundSpinner.moveUp();
                refresh();

            }
        });

        btnQuestionDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionSpinner.moveDown();

            }
        });

        btnQuestionUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionSpinner.moveUp();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //QuizLoader quizLoader =new QuizLoader(C_ParticipantHome.this,thisQuiz.getListData().getSheetDocID(),thisQuiz);
                //quizLoader.loadRounds();
                ArrayList<Answer> answerList = thisQuiz.getMyAnswers().get(roundSpinner.getPosition());
                String tmp = "[";
                for (int i = 0; i < answerList.size(); i++) {
                    tmp = tmp + "[\"" + answerList.get(i).getAnswer() + "\"]";
                    if (i < answerList.size() - 1) {
                        tmp = tmp + ",";
                    }
                }
                tmp = tmp + "]";
                JSONArray answerArray = new JSONArray(answerList);
                String answers = answerArray.toString();
                String scriptParams = GoogleAccess.PARAMNAME_DOC_ID + thisQuiz.getListData().getSheetDocID() + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_USERID + "Rupert" + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_ROUNDID + (roundSpinner.getPosition()+1) + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_FIRSTQUESTION + (roundSpinner.getPosition()+1) + ".1" + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_TEAMID + thisQuiz.getMyLoginentity().getId() + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_ANSWERS + tmp + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_SUBMITANSWERS;
                GoogleAccessAddLine submitAnswers = new GoogleAccessAddLine(C_ParticipantHome.this, scriptParams);
                submitAnswers.addLine();
            }
        });
    }
}
