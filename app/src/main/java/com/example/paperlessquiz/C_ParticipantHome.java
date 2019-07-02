package com.example.paperlessquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paperlessquiz.adapters.DisplayAnswersAdapter;
import com.example.paperlessquiz.google.access.EventLogger;
import com.example.paperlessquiz.google.access.LoadingActivity;
import com.example.paperlessquiz.quiz.QuizLoader;
import com.example.paperlessquiz.round.Round;

/**
 * Home screen for teams. Contains a round spinner and a question spinner + fields that allow to answer the question that is currently selected + button to submit answers.
 * Parts of the screen are hidden based on round settings.
 */
public class C_ParticipantHome extends MyActivity implements LoadingActivity, FragSpinner.HasSpinner,
        FragRoundSpinner.HasRoundSpinner, FragShowRoundScore.HasShowRoundScore, FragExplainRoundStatus.HasExplainRoundStatus {

    int thisTeamNr;
    FragRoundSpinner roundSpinner;
    FragSpinner questionSpinner;
    FragShowRoundScore roundResultFrag;
    FragExplainRoundStatus explainRoundStatus;
    TextView tvDisplayRoundResults;
    EditText etAnswer;
    Button btnSubmit;
    LinearLayout displayAnswersLayout, editAnswerLayout;
    RecyclerView rvDisplayAnswers;
    DisplayAnswersAdapter displayAnswersAdapter;
    RecyclerView.LayoutManager layoutManager;
    String roundStatusExplanation;

    @Override
    public String getRoundStatusExplanation() {
        return roundStatusExplanation;
    }

    @Override
    public int getRound() {
        return roundSpinner.getPosition();
    }

    @Override
    public int getTeam() {
        return thisTeamNr;
    }

    @Override
    //This is for the round spinner
    public void onRoundChanged(int oldRoundNr, int roundNr) {
        //Similar as with a questionSpinner change, we save the answer that we have and load the new answer - only do this if the field is visible
        if (etAnswer.getVisibility() == View.VISIBLE) {
            thisQuiz.setAnswerForTeam(oldRoundNr, questionSpinner.getPosition(), thisTeamNr, etAnswer.getText().toString().trim());
        }
        //Set the value of the answer for the new question to what we already have in the central Quiz object
        //Move the QuestionSpinner to position 1 to make sure we have something at that position
        questionSpinner.moveToFirstPos();
        etAnswer.setText(thisQuiz.getAnswerForTeam(roundNr, 1, thisTeamNr).getTheAnswer());
        //Dismiss the keyboard if its there
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etAnswer.getWindowToken(), 0);
        refreshDisplayFragments();
        roundResultFrag.refresh();
    }

    @Override
    //This is for the Question spinner
    public void onSpinnerChange(int oldPos, int newPos) {
        //Save the answer that was given
        thisQuiz.setAnswerForTeam(roundSpinner.getPosition(), oldPos, thisTeamNr, etAnswer.getText().toString().trim());
        //Set the value of the answer for the new question to what we already have
        etAnswer.setText(thisQuiz.getAnswerForTeam(roundSpinner.getPosition(), newPos, thisTeamNr).getTheAnswer());
        refreshAnswers(); //refresh the field that shows all answers already given for this round
    }

    @Override
    public int getSizeOfSpinnerArray() {
        return thisQuiz.getRound(roundSpinner.getPosition()).getQuestions().size();
    }

    @Override
    public String getValueToSetForPrimaryField(int newPos) {
        return thisQuiz.getQuestion(roundSpinner.getPosition(), newPos).getName();
    }

    @Override
    public String getValueToSetForSecondaryField(int newPos) {
        return "(" + thisQuiz.getQuestion(roundSpinner.getPosition(), newPos).getHint() + ")";
    }

    @Override
    public void loadingComplete() {
        //Actions do do when we are completed (re)loading data from the Google sheet
        //roundSpinner.moveUp();roundSpinner.moveDown(); // Replaced with refreshIcons since moveUp and MoveDown crashed the app sometimes? Issue with Fragment mgr?
        roundSpinner.refreshIcons();
        roundResultFrag.refresh();  //This will recalculate scores based on the re-loaded corrections
        refreshDisplayFragments();  //Display the correct fragments based on new round status etc.
    }

    private void refreshAnswers() {
        //Update the displayed answers for this round as needed
        displayAnswersAdapter.setAnswers(thisQuiz.getRound(roundSpinner.getPosition()).getQuestions());
        rvDisplayAnswers.setAdapter(displayAnswersAdapter);
    }

    private void refreshDisplayFragments() {
        //Refresh what is in the display based on the current values of roundSpinner position
        Round thisRound = thisQuiz.getRound(roundSpinner.getPosition());
        if (!(thisRound.getAcceptsAnswers() || thisRound.getAcceptsCorrections() || thisRound.isCorrected())) {
            //Round is not yet open
            //Just display the fragment that tells you this
            roundStatusExplanation = C_ParticipantHome.this.getString(R.string.participant_waitforroundopen);
            explainRoundStatus.setStatus(roundStatusExplanation);
            toggleFragments(R.id.frPlaceHolder, explainRoundStatus, roundResultFrag, questionSpinner);
            editAnswerLayout.setVisibility((View.GONE));
            displayAnswersLayout.setVisibility(View.GONE);
        }
        if (thisRound.getAcceptsAnswers()) {
            //Round is open to enter answers
            //Show the questionSpinner
            toggleFragments(R.id.frPlaceHolder, questionSpinner, roundResultFrag, explainRoundStatus);
            //Show the layouts to edit and display answers
            editAnswerLayout.setVisibility(View.VISIBLE);
            displayAnswersLayout.setVisibility(View.VISIBLE);
            //etAnswer is by default invisible to avoid seeing the keyboard when you shouldn't
            etAnswer.setVisibility(View.VISIBLE);
            //Initialize etAnswer with the correct answer
            etAnswer.setText(thisQuiz.getAnswerForTeam(roundSpinner.getPosition(), questionSpinner.getPosition(), thisTeamNr).getTheAnswer());
        }
        if (thisRound.getAcceptsCorrections()) {
            //Round is closed for answering, but not yet corrected
            //Just display the fragment that tells you this
            roundStatusExplanation = C_ParticipantHome.this.getString(R.string.participant_waitforroundcorrection);
            explainRoundStatus.setStatus(roundStatusExplanation);
            toggleFragments(R.id.frPlaceHolder, explainRoundStatus, roundResultFrag, questionSpinner);
            editAnswerLayout.setVisibility((View.GONE));
            displayAnswersLayout.setVisibility(View.GONE);
        }
        if (thisRound.isCorrected()) {
            //Show the RoundResults Fragment
            toggleFragments(R.id.frPlaceHolder, roundResultFrag, questionSpinner, explainRoundStatus);
            //Hide the layout to edit answers
            editAnswerLayout.setVisibility((View.GONE));
            displayAnswersLayout.setVisibility(View.VISIBLE);
        }
        refreshAnswers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.participant, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                //Reload the data that is needed for participants
                QuizLoader quizLoader = new QuizLoader(C_ParticipantHome.this, thisQuiz.getListData().getSheetDocID());
                quizLoader.loadRounds();            //To get the latest rounds status
                quizLoader.loadAllCorrections();    //To know if the previously submitted answers were correct and be able to calculate scores and standings
                //quizLoader.loadAllAnswers();        //Not needed, teams only need to know their own answers, and those are anyhow loaded on restart
                //The LoadingCompleted triggers refresh of the interface
                break;

            case R.id.help:
                //Intent intentDisplayPDF = new Intent(C_ParticipantHome.this, DisplayPDF.class);
                //startActivity(intentDisplayPDF);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_c_participant_home);
        //Get the round spinner fragment
        roundSpinner = (FragRoundSpinner) getSupportFragmentManager().findFragmentById(R.id.frRoundSpinner);
        //Create the other fragments that are needed here and set the action bar icon and title
        questionSpinner = new FragSpinner();
        roundResultFrag = new FragShowRoundScore();
        explainRoundStatus = new FragExplainRoundStatus();
        setActionBarIcon();
        setActionBarTitle();
        //Log that the user logged in and set LoggedIn to TRUE
        thisTeamNr = thisQuiz.getMyLoginentity().getId();
        MyApplication.eventLogger.logEvent(thisQuiz.getMyLoginentity().getName(), EventLogger.LEVEL_INFO, "Logged in");
        thisQuiz.setLoggedIn(C_ParticipantHome.this, thisTeamNr, true);
        MyApplication.setLoggedIn(true);
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
        //Initialize the adapter, we will change the questionsList later on
        displayAnswersAdapter = new DisplayAnswersAdapter(this, thisQuiz.getRound(1).getQuestions(), thisTeamNr);
        //Initially, we start with question 1 of round 1, so set the text of the editText to this answer
        //etAnswer.setText(thisQuiz.getQuestion(1, 1).getAnswerForTeam(thisTeamNr).getTheAnswer());
        //RefreshDisplay will make sure we are seeing the correct fragments
        //refreshDisplayFragments();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //First store the answer to the last question
                thisQuiz.setAnswerForTeam(roundSpinner.getPosition(), questionSpinner.getPosition(), thisTeamNr, etAnswer.getText().toString().trim());
                refreshDisplayFragments();
                thisQuiz.submitAnswers(C_ParticipantHome.this, roundSpinner.getPosition());
            }
        });
    }

    @Override
    protected void onPause() {
        MyApplication.eventLogger.logEvent(thisQuiz.getMyLoginentity().getName(), EventLogger.LEVEL_WARNING, "WARNING: Paused the app");
        thisQuiz.setLoggedIn(C_ParticipantHome.this, thisTeamNr, false);
        MyApplication.setAppPaused(true);
        super.onPause();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (MyApplication.isAppPaused()) {
            MyApplication.eventLogger.logEvent(thisQuiz.getMyLoginentity().getName(), EventLogger.LEVEL_WARNING, "WARNING: Resumed the app");
            thisQuiz.setLoggedIn(C_ParticipantHome.this, thisTeamNr, true);
            MyApplication.setAppPaused(false);
        }
    }

    @Override
    public void onBackPressed() {
        if (true) {
            Toast.makeText(this, "Back button to be disabled still", Toast.LENGTH_SHORT).show();
            super.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }
}
