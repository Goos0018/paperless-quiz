package com.example.paperlessquiz;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.paperlessquiz.answer.Answer;
import com.example.paperlessquiz.google.access.GoogleAccess;
import com.example.paperlessquiz.google.access.GoogleAccessSet;
import com.example.paperlessquiz.quiz.Quiz;
import com.example.paperlessquiz.quiz.QuizLoader;
import com.example.paperlessquiz.spinners.QuestionSpinner;
import com.example.paperlessquiz.spinners.RoundSpinner;

import org.json.JSONArray;

import java.util.ArrayList;
/*
This activity is the home screen for participants. It will display a round spinner and a question spinner,
allowing the user to enter answer per question, and submit them per round.
Display is as follows:
- Round is pending start: display text
- Round is accepts answers: display spinners and answer field + submit button
- Round is closed for answers: display answers + pending correction text
- Round is corrected: display answers + scores + correct answers if available
 */


public class C_ParticipantHome extends AppCompatActivity {
    Quiz thisQuiz;
    RoundSpinner roundSpinner;
    QuestionSpinner questionSpinner;
    TextView tvRoundName, tvRoundDescription, tvQuestionName, tvQuestionDescription, tvDisplayRoundResults;
    EditText etAnswer;
    Button btnRndUp, btnRndDown, btnQuestionUp, btnQuestionDown, btnSubmit;
    LinearLayout displayLayout, answerLayout;

    private void refresh() {
        if (thisQuiz.getRound(roundSpinner.getPosition()).AcceptsAnswers()) {
            answerLayout.setVisibility(View.VISIBLE);
        } else {
            answerLayout.setVisibility((View.GONE));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.c_participant_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.refresh:
                QuizLoader quizLoader =new QuizLoader(C_ParticipantHome.this,thisQuiz.getListData().getSheetDocID(),thisQuiz);
                quizLoader.loadRounds();
                refresh();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_act_participant_home);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setIcon();
        //actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setDisplayUseLogoEnabled(true);

        thisQuiz = (Quiz) getIntent().getSerializableExtra(Quiz.INTENT_EXTRANAME_THIS_QUIZ);
        tvQuestionName = findViewById(R.id.tvQuestionName);
        tvQuestionDescription = findViewById(R.id.tvQuestionDescription);
        tvRoundName = findViewById(R.id.tvRoundName);
        tvRoundDescription = findViewById(R.id.tvRoundDescription);
        tvDisplayRoundResults = findViewById(R.id.tvDisplayRound);
        etAnswer = findViewById(R.id.etAnswer);
        btnQuestionDown = findViewById(R.id.btnQuestionDown);
        btnQuestionUp = findViewById(R.id.btnQuestionUp);
        btnRndDown = findViewById(R.id.btnRndDown);
        btnRndUp = findViewById(R.id.btnRndUp);
        btnSubmit = findViewById(R.id.btnSubmit);
        displayLayout = findViewById(R.id.llDisplay);
        answerLayout = findViewById(R.id.llAnswers);

        actionBar.setTitle(thisQuiz.getMyLoginentity().getName());
        questionSpinner = new QuestionSpinner(thisQuiz.getAllQuestionsPerRound(), tvQuestionName, tvQuestionDescription, tvDisplayRoundResults,
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
                questionSpinner.moveDown();
                ArrayList<Answer> answerList = thisQuiz.getMyAnswers().get(roundSpinner.getPosition());
                String tmp = "[";
                for (int i = 0; i < answerList.size(); i++) {
                    tmp = tmp + "[\"" + answerList.get(i).getMyAnswer() + "\"]";
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
                GoogleAccessSet submitAnswers = new GoogleAccessSet(C_ParticipantHome.this, scriptParams);
                submitAnswers.setData();
            }
        });
    }
}
