package com.example.paperlessquiz;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.paperlessquiz.adapters.DisplayAnswersAdapter;
import com.example.paperlessquiz.google.access.LoadingActivity;
import com.example.paperlessquiz.quiz.Quiz;
import com.example.paperlessquiz.quiz.QuizLoader;
import com.example.paperlessquiz.round.Round;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class C_ParticipantHome extends AppCompatActivity implements LoadingActivity, FragSpinner.HasSpinner, FragRoundSpinner.HasRoundSpinner {

    Quiz thisQuiz = MyApplication.theQuiz;
    int thisTeamNr;
    FragRoundSpinner roundSpinner;
    FragSpinner questionSpinner;
    TextView tvDisplayRoundResults;
    EditText etAnswer;
    Button btnSubmit;
    LinearLayout displayAnswersLayout, editAnswerLayout;
    RecyclerView rvDisplayAnswers;
    DisplayAnswersAdapter displayAnswersAdapter;
    RecyclerView.LayoutManager layoutManager;
    String qSpinnerTag = "qSpinner";

    @Override
    public void onSpinnerChange(int id, int oldPos, int newPos) {
        //Save the answer that was given
        thisQuiz.setAnswerForTeam(roundSpinner.getPosition(), oldPos, thisTeamNr, etAnswer.getText().toString().trim());
        //Set the value of the answer for the new question to what we already have
        etAnswer.setText(thisQuiz.getAnswerForTeam(roundSpinner.getPosition(), newPos, thisTeamNr).getTheAnswer());
        refresh();
    }

    @Override
    public int getSizeOfSpinnerArray() {
        return thisQuiz.getRound(roundSpinner.getPosition()).getQuestions().size();
    }

    @Override
    public String getValueToSetForPrimaryField(int id, int newPos) {
        return thisQuiz.getQuestion(roundSpinner.getPosition(), newPos).getName();
    }

    @Override
    public String getValueToSetForSecondaryField(int id, int newPos) {
        return thisQuiz.getQuestion(roundSpinner.getPosition(), newPos).getDescription();
    }

    @Override
    public void onRoundChanged(int oldRoundNr, int roundNr) {
        //Similar as with a questionSpinner change, we save the answer that we have and load the new answer
        thisQuiz.setAnswerForTeam(oldRoundNr, questionSpinner.getPosition(), thisTeamNr, etAnswer.getText().toString().trim());
        //Set the value of the answer for the new question to what we already have
        etAnswer.setText(thisQuiz.getAnswerForTeam(oldRoundNr, questionSpinner.getPosition(), thisTeamNr).getTheAnswer());
        refresh();
    }

    @Override
    public void loadingComplete() {
        //Actions do do when we are completed (re)loading data from the Google sheet
        refresh();
    }

    private void refresh() {
        //Refresh what is in the display based on the current values of roundSpinner and QuestionSpinner positions
        Round thisRound = thisQuiz.getRound(roundSpinner.getPosition());
        if (thisRound.getAcceptsAnswers()) {
            //TODO: unhide the questionspinner
            editAnswerLayout.setVisibility(View.VISIBLE);
            displayAnswersLayout.setVisibility(View.VISIBLE);
            //etAnswer is by default invisible to avoid seeing the keyboard when you shouldn't
            etAnswer.setVisibility(View.VISIBLE);
            etAnswer.setText(thisQuiz.getAnswerForTeam(roundSpinner.getPosition(), questionSpinner.getPosition(), thisTeamNr).getTheAnswer());
        } else {
            //TODO: hide the questionspinner
            editAnswerLayout.setVisibility((View.GONE));
            displayAnswersLayout.setVisibility(View.VISIBLE);
        }
        //Update the displayed answser as needed
        displayAnswersAdapter.setAnswers(thisQuiz.getRound(roundSpinner.getPosition()).getQuestions());
        rvDisplayAnswers.setAdapter(displayAnswersAdapter);

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
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_act_participant_home);
        //Get the round spinner fragment
        roundSpinner = (FragRoundSpinner) getSupportFragmentManager().findFragmentById(R.id.frRoundSpinner);
        //Create the QuestionSpinner fragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        questionSpinner = FragSpinner.newInstance(1);
        ft.replace(R.id.frQuestionSpinner, questionSpinner,qSpinnerTag).commit();
        //Get the question spinner fragment
        //questionSpinner = (FragSpinner) getSupportFragmentManager().findFragmentByTag(qSpinnerTag);

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
            public void onBitmapFailed(Drawable drawable) {
            }

            @Override
            public void onPrepareLoad(Drawable drawable) {
            }
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
        tvDisplayRoundResults = findViewById(R.id.tvDisplayRound);
        etAnswer = findViewById(R.id.etAnswer);
        btnSubmit = findViewById(R.id.btnSubmit);
        rvDisplayAnswers = findViewById(R.id.rvDisplayAnswers);
        rvDisplayAnswers.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvDisplayAnswers.setLayoutManager(layoutManager);
        displayAnswersAdapter = new DisplayAnswersAdapter(this, thisQuiz.getRound(1).getQuestions(), thisTeamNr);
        //Initially, we start with question 1 of round 1, so set the text of the editText to this answer
        etAnswer.setText(thisQuiz.getQuestion(1, 1).getAnswerForTeam(thisTeamNr).getTheAnswer());
        //Refresh does all actions that are dependent on the position of the question spinner and the roundspinner
        refresh();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //questionSpinner.moveDown();
                //questionSpinner.moveUp();
                thisQuiz.setAnswerForTeam(roundSpinner.getPosition(), questionSpinner.getPosition(), thisTeamNr, etAnswer.getText().toString().trim());
                refresh();
                thisQuiz.submitAnswers(C_ParticipantHome.this, roundSpinner.getPosition());
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
