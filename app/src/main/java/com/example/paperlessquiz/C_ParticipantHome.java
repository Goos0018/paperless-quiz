package com.example.paperlessquiz;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.paperlessquiz.adapters.CorrectAnswersAdapter;
import com.example.paperlessquiz.adapters.DisplayAnswersAdapter;
import com.example.paperlessquiz.answer.Answer;
import com.example.paperlessquiz.google.access.GoogleAccess;
import com.example.paperlessquiz.google.access.GoogleAccessSet;
import com.example.paperlessquiz.google.access.LoadingActivity;
import com.example.paperlessquiz.google.access.LoadingListenerNotify;
import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.question.Question;
import com.example.paperlessquiz.quiz.Quiz;
import com.example.paperlessquiz.quiz.QuizLoader;
import com.example.paperlessquiz.round.Round;
import com.example.paperlessquiz.spinners.QuestionSpinner;
import com.example.paperlessquiz.spinners.RoundSpinner;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

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


public class C_ParticipantHome extends AppCompatActivity implements LoadingActivity {
    Quiz thisQuiz;
    int thisTeamNr;
    RoundSpinner roundSpinner;
    QuestionSpinner questionSpinner;
    TextView tvRoundName, tvRoundDescription, tvQuestionName, tvQuestionDescription, tvDisplayRoundResults, tvCorrectAnswer;
    ImageView ivRoundStatus;
    EditText etAnswer;
    Button btnRndUp, btnRndDown, btnQuestionUp, btnQuestionDown, btnSubmit, btnSubmitCorrections;
    LinearLayout displayAnswersLayout, editAnswerLayout, correctorLayout, questionSpinnerLayout;
    ListView lvCorrectQuestions;
    RecyclerView rvDisplayAnswers;
    DisplayAnswersAdapter displayAnswersAdapter;
    RecyclerView.LayoutManager layoutManager;
    CorrectAnswersAdapter myAdapter;

    @Override
    public void loadingComplete() {
        //Actions do do when we are completed (re)loading data from the Google sheet
        refresh();
    }

    private void refresh() {
        Round thisRound = thisQuiz.getRound(roundSpinner.getRoundNr());
        //Question thisQuestion = thisQuiz.getQuestion(roundSpinner.getRoundNr(),questionSpinner.getQuestionNr());
        String thisLoginEntityType = thisQuiz.getMyLoginentity().getType();
        //Set the icon that shows the round status
        if (!thisRound.getAcceptsAnswers() && !thisRound.getAcceptsCorrections() && !thisRound.isCorrected()) {
            ivRoundStatus.setImageResource(R.drawable.rnd_not_yet_open);
        }
        if (thisRound.getAcceptsAnswers()) {
            ivRoundStatus.setImageResource(R.drawable.rnd_open);
        }
        if (thisRound.getAcceptsCorrections()) {
            ivRoundStatus.setImageResource(R.drawable.rnd_closed);
        }
        if (thisRound.isCorrected()) {
            ivRoundStatus.setImageResource(R.drawable.rnd_corrected);
        }
        //If this is a participant
        if (thisLoginEntityType.equals(LoginEntity.SELECTION_PARTICIPANT)) {
            if (thisRound.getAcceptsAnswers()) {
                questionSpinnerLayout.setVisibility(View.VISIBLE);
                editAnswerLayout.setVisibility(View.VISIBLE);
                displayAnswersLayout.setVisibility(View.VISIBLE);
                //etAnswer is by default invisible to avoid seeing the keyboard when you shouldn't
                etAnswer.setVisibility(View.VISIBLE);
            } else {
                questionSpinnerLayout.setVisibility(View.GONE);
                editAnswerLayout.setVisibility((View.GONE));
                displayAnswersLayout.setVisibility(View.VISIBLE);
            }
            correctorLayout.setVisibility((View.GONE));
            //correctorLayout.findFocus();
            ArrayList<Question> questions;
            displayAnswersAdapter.setAnswers(thisQuiz.getRound(roundSpinner.getRoundNr()).getQuestions());
            rvDisplayAnswers.setAdapter(displayAnswersAdapter);

        }
        //If this is a questionscorrector
        if (!(thisLoginEntityType.equals(LoginEntity.SELECTION_PARTICIPANT))) {
            //No need for the editAnswers and displayAnswers parts
            editAnswerLayout.setVisibility((View.GONE));
            displayAnswersLayout.setVisibility(View.GONE);
            if (thisRound.getAcceptsCorrections()) {
                questionSpinnerLayout.setVisibility(View.VISIBLE);
                correctorLayout.setVisibility((View.VISIBLE));
            } else {
                questionSpinnerLayout.setVisibility(View.GONE);
                correctorLayout.setVisibility((View.GONE));
            }
            ArrayList<Answer> allAnswers;
            allAnswers = thisQuiz.getQuestion(roundSpinner.getRoundNr(), questionSpinner.getQuestionNr()).getAllAnswers();
            myAdapter = new CorrectAnswersAdapter(this, allAnswers);
            lvCorrectQuestions.setAdapter(myAdapter);
            tvCorrectAnswer.setText(thisQuiz.getQuestion(roundSpinner.getRoundNr(), questionSpinner.getQuestionNr()).getCorrectAnswer());
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
                QuizLoader quizLoader = new QuizLoader(C_ParticipantHome.this, thisQuiz.getListData().getSheetDocID());
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
        thisQuiz = MyApplication.theQuiz;
        //Set the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); //Display the "back" icon, we will replace this with the icon of this Quiz
        final Target mTarget = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                BitmapDrawable mBitmapDrawable = new BitmapDrawable(getResources(), bitmap);
                actionBar.setHomeAsUpIndicator(mBitmapDrawable);
            }
            @Override
            public void onBitmapFailed(Drawable drawable) { }
            @Override
            public void onPrepareLoad(Drawable drawable) { }
        };
        String URL = thisQuiz.getListData().getLogoURL();
        if (URL.equals("")) {
            actionBar.setDisplayHomeAsUpEnabled(false);//If the Quiz has no logo, then don't display anything
        } else {
            //Picasso.with(this).load("http://www.meerdaal.be//assets/logo-05c267018885eb67356ce0b49bf72129.png").into(mTarget);
            Picasso.with(this).load(thisQuiz.getListData().getLogoURL()).resize(Quiz.ACTIONBAR_ICON_WIDTH, Quiz.ACTIONBAR_ICON_HEIGHT).into(mTarget);
        }
        actionBar.setTitle(thisQuiz.getMyLoginentity().getName());

        //Log that the user logged in
        thisTeamNr = thisQuiz.getMyLoginentity().getId();
        MyApplication.eventLogger.logEvent(thisQuiz.getMyLoginentity().getName(), "Logged in");
        //Get all the stuff from the layout
        displayAnswersLayout = findViewById(R.id.llDisplay);
        editAnswerLayout = findViewById(R.id.llAnswers);
        correctorLayout = findViewById(R.id.llCorrectQuestions);
        questionSpinnerLayout = findViewById(R.id.llQuestionSpinner);
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
        ivRoundStatus = findViewById(R.id.ivRndStatusL);
        lvCorrectQuestions = findViewById(R.id.lvCorrectQuestions);
        rvDisplayAnswers = findViewById(R.id.rvDisplayAnswers);
        rvDisplayAnswers.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvDisplayAnswers.setLayoutManager(layoutManager);
        displayAnswersAdapter = new DisplayAnswersAdapter(this, thisQuiz.getRound(1).getQuestions(), thisTeamNr);
        //Initially, we start with question 1 of round 1, so set the text of the editText to this answer
        etAnswer.setText(thisQuiz.getQuestion(1, 1).getAnswerForTeam(thisTeamNr).getTheAnswer());
        questionSpinner = new QuestionSpinner(thisQuiz, tvQuestionName, tvQuestionDescription, etAnswer, 1, thisTeamNr);
        roundSpinner = new RoundSpinner(thisQuiz, tvRoundName, tvRoundDescription, questionSpinner);
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
                refresh();
                ArrayList<Answer> answerList = thisQuiz.getAnswersForRound(roundSpinner.getRoundNr(), thisTeamNr);
                String tmp = "[";
                for (int i = 0; i < answerList.size(); i++) {
                    tmp = tmp + "[\"" + answerList.get(i).getTheAnswer() + "\"]";
                    if (i < answerList.size() - 1) {
                        tmp = tmp + ",";
                    }
                }
                tmp = tmp + "]";
                JSONArray answerArray = new JSONArray(answerList);
                String answers = answerArray.toString();
                String scriptParams = GoogleAccess.PARAMNAME_DOC_ID + thisQuiz.getListData().getSheetDocID() + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_USERID + "Rupert" + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_ROUNDID + roundSpinner.getRoundNr() + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_FIRSTQUESTION + thisQuiz.getRound(roundSpinner.getRoundNr()).getQuestion(1).getQuestionID() + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_TEAMID + thisQuiz.getMyLoginentity().getId() + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_ANSWERS + tmp + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_SUBMITANSWERS;
                GoogleAccessSet submitAnswers = new GoogleAccessSet(C_ParticipantHome.this, scriptParams, thisQuiz.getAdditionalData().getDebugLevel());
                submitAnswers.setData(new LoadingListenerNotify(C_ParticipantHome.this, thisQuiz.getMyLoginentity().getName(),
                        "Submitting answers for round " + (roundSpinner.getRoundNr())));
            }
        });

        btnSubmitCorrections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Answer> answerList = thisQuiz.getAllAnswersForQuestion(roundSpinner.getRoundNr(), questionSpinner.getQuestionNr());
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
                        GoogleAccess.PARAMNAME_RECORDID + thisQuiz.getQuestion(roundSpinner.getRoundNr(), questionSpinner.getQuestionNr()).getQuestionID() + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_FIELDNAME + GoogleAccess.PARAMVALUE_FIRST_TEAM_NR + GoogleAccess.PARAM_CONCATENATOR + //We write score starting from the first team which should have id 1
                        GoogleAccess.PARAMNAME_NEWVALUES + tmp + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_SETDATA;
                GoogleAccessSet submitScores = new GoogleAccessSet(C_ParticipantHome.this, scriptParams,thisQuiz.getAdditionalData().getDebugLevel());
                submitScores.setData(new LoadingListenerNotify(C_ParticipantHome.this, thisQuiz.getMyLoginentity().getName(),
                        "Submitting scores for question " + thisQuiz.getQuestion(roundSpinner.getRoundNr(), questionSpinner.getQuestionNr()).getQuestionID()));
            }
        });

        ivRoundStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
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
