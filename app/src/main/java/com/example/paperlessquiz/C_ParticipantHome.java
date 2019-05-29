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
import android.widget.ListView;
import android.widget.TextView;

import com.example.paperlessquiz.adapters.ShowAllAnswersAdapter;
import com.example.paperlessquiz.answer.Answer;
import com.example.paperlessquiz.google.access.GoogleAccess;
import com.example.paperlessquiz.google.access.GoogleAccessSet;
import com.example.paperlessquiz.google.access.LoadingListenerNotify;
import com.example.paperlessquiz.google.access.LoadingListenerShowProgress;
import com.example.paperlessquiz.loginentity.LoginEntity;
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
    TextView tvRoundName, tvRoundDescription, tvQuestionName, tvQuestionDescription, tvDisplayRoundResults, tvCorrectAnswer;
    EditText etAnswer;
    Button btnRndUp, btnRndDown, btnQuestionUp, btnQuestionDown, btnSubmit, btnSubmitCorrections;
    LinearLayout displayLayout, answerLayout, correctorLayout;
    ListView lvCorrectQuestions;
    ShowAllAnswersAdapter myAdapter;


    private void refresh() {
        //this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //If this is a participant
        if (thisQuiz.getMyLoginentity().getType().equals(LoginEntity.SELECTION_PARTICIPANT)) {
            if (thisQuiz.getRound(roundSpinner.getPosition()).AcceptsAnswers()) {
                answerLayout.setVisibility(View.VISIBLE);
                //etAnswer is by default invisible to avoid seeing the keyboard when you shouldn't
                etAnswer.setVisibility(View.VISIBLE);
            } else {
                answerLayout.setVisibility((View.GONE));
            }
            correctorLayout.setVisibility((View.GONE));
        }
        //If this is a questionscorrector
        if (!(thisQuiz.getMyLoginentity().getType().equals(LoginEntity.SELECTION_PARTICIPANT))) {
            answerLayout.setVisibility((View.GONE));
            displayLayout.setVisibility(View.GONE);
            //correctorLayout.findFocus();
            ArrayList<Answer> allAnswers;
            allAnswers = thisQuiz.getAllAnswers().get(roundSpinner.getPosition()).get(questionSpinner.getPosition()).getAllAnswers();
            myAdapter = new ShowAllAnswersAdapter(this, allAnswers);
            lvCorrectQuestions.setAdapter(myAdapter);
            //myAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.c_participant_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                QuizLoader quizLoader = new QuizLoader(C_ParticipantHome.this, thisQuiz.getListData().getSheetDocID(), thisQuiz);
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
        //Log that the user logged in
        MyApplication.eventLogger.logEvent(thisQuiz.getMyLoginentity().getName(), "Logged in");
        displayLayout = findViewById(R.id.llDisplay);
        answerLayout = findViewById(R.id.llAnswers);
        correctorLayout = findViewById(R.id.llCorrectQuestions);
        tvQuestionName = findViewById(R.id.tvQuestionName);
        tvQuestionDescription = findViewById(R.id.tvQuestionDescription);
        tvRoundName = findViewById(R.id.tvRoundName);
        tvRoundDescription = findViewById(R.id.tvRoundDescription);
        tvDisplayRoundResults = findViewById(R.id.tvDisplayRound);
        tvCorrectAnswer = findViewById(R.id.tvCorrectAnswer);
        etAnswer = findViewById(R.id.etAnswer);
        btnQuestionDown = findViewById(R.id.btnQuestionDown);
        btnQuestionUp = findViewById(R.id.btnQuestionUp);
        btnRndDown = findViewById(R.id.btnRndDown);
        btnRndUp = findViewById(R.id.btnRndUp);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmitCorrections = findViewById(R.id.btnSubmitCorrections);
        lvCorrectQuestions = findViewById(R.id.lvCorrectQuestions);
        actionBar.setTitle(thisQuiz.getMyLoginentity().getName());

        etAnswer.setText(thisQuiz.getMyAnswers().get(0).get(0).getThisAnswer());
        questionSpinner = new QuestionSpinner(thisQuiz.getAllQuestionsPerRound(), tvQuestionName, tvQuestionDescription, tvDisplayRoundResults,
                thisQuiz.getMyAnswers(), etAnswer, 0);
        roundSpinner = new RoundSpinner(thisQuiz.getRounds(), tvRoundName, tvRoundDescription, questionSpinner);
        //Refresh does all actions that are dependent on the position of the question spinner and the roundspinner
        refresh();


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
                refresh();
            }
        });

        btnQuestionUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionSpinner.moveUp();
                refresh();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionSpinner.moveDown();
                questionSpinner.moveUp();
                ArrayList<Answer> answerList = thisQuiz.getMyAnswers().get(roundSpinner.getPosition());
                String tmp = "[";
                for (int i = 0; i < answerList.size(); i++) {
                    tmp = tmp + "[\"" + answerList.get(i).getThisAnswer() + "\"]";
                    if (i < answerList.size() - 1) {
                        tmp = tmp + ",";
                    }
                }
                tmp = tmp + "]";
                JSONArray answerArray = new JSONArray(answerList);
                String answers = answerArray.toString();
                String scriptParams = GoogleAccess.PARAMNAME_DOC_ID + thisQuiz.getListData().getSheetDocID() + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_USERID + "Rupert" + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_ROUNDID + (roundSpinner.getPosition() + 1) + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_FIRSTQUESTION + (roundSpinner.getPosition() + 1) + ".1" + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_TEAMID + thisQuiz.getMyLoginentity().getId() + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_ANSWERS + tmp + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_SUBMITANSWERS;
                GoogleAccessSet submitAnswers = new GoogleAccessSet(C_ParticipantHome.this, scriptParams);
                submitAnswers.setData(new LoadingListenerNotify(C_ParticipantHome.this, thisQuiz.getMyLoginentity().getName(),
                        "Submit answers for round " + (roundSpinner.getPosition() + 1)));
            }
        });

        btnSubmitCorrections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //questionSpinner.moveDown();
                //questionSpinner.moveUp();
                ArrayList<Answer> answerList = thisQuiz.getAllAnswers().get(roundSpinner.getPosition()).get(questionSpinner.getPosition()).getAllAnswers();
                String tmp = "[[";
                for (int i = 0; i < answerList.size(); i++) {
                    tmp = tmp + "\"" + answerList.get(i).isCorrect() + "\"";
                    if (i < answerList.size() - 1) {
                        tmp = tmp + ",";
                    }
                }
                tmp = tmp + "]]";
                //JSONArray answerArray = new JSONArray(answerList);
                //String answers = answerArray.toString();
                String scriptParams = GoogleAccess.PARAMNAME_DOC_ID + thisQuiz.getListData().getSheetDocID() + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_USERID + "Rupert" + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_SHEET + GoogleAccess.SHEET_SCORES + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_RECORDID + (roundSpinner.getPosition() + 1) + "." + (questionSpinner.getPosition() + 1) + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_FIELDNAME + "T1" + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_NEWVALUES + tmp + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_SETDATA;
                GoogleAccessSet submitScores = new GoogleAccessSet(C_ParticipantHome.this, scriptParams);
                submitScores.setData(new LoadingListenerNotify(C_ParticipantHome.this, thisQuiz.getMyLoginentity().getName(),
                        "Submit scores for question " + (questionSpinner.getPosition() + 1)));
            }
        });
    }

    @Override
    protected void onPause() {
        MyApplication.eventLogger.logEvent(thisQuiz.getMyLoginentity().getName(), "WARNING: Paused the app");
        super.onPause();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        MyApplication.eventLogger.logEvent(thisQuiz.getMyLoginentity().getName(), "WARNING: Resumed the app");
    }
}
