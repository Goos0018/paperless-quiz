package com.example.paperlessquiz;

import android.content.Context;
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

//TODO: find method to hide the keyboard when spinner changes
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
    //String qSpinnerTag = "qSpinner";
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
    public void onSpinnerChange(int oldPos, int newPos) {
        //Save the answer that was given
        thisQuiz.setAnswerForTeam(roundSpinner.getPosition(), oldPos, thisTeamNr, etAnswer.getText().toString().trim());
        //Set the value of the answer for the new question to what we already have
        etAnswer.setText(thisQuiz.getAnswerForTeam(roundSpinner.getPosition(), newPos, thisTeamNr).getTheAnswer());
        //etAnswer.clearFocus();
        //etAnswer.setImeOptions(EditorInfo.IME_ACTION_DONE);
        refreshAnswers();
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
    public void onRoundChanged(int oldRoundNr, int roundNr) {
        //Similar as with a questionSpinner change, we save the answer that we have and load the new answer - only do this if the field is visible
        if (etAnswer.getVisibility() == View.VISIBLE) {
            thisQuiz.setAnswerForTeam(oldRoundNr, questionSpinner.getPosition(), thisTeamNr, etAnswer.getText().toString().trim());
        }

        //Set the value of the answer for the new question to what we already have
        //Move the QuestionSpinner to position 1 to make sure we have something at that position
        questionSpinner.moveToFirstPos();
        etAnswer.setText(thisQuiz.getAnswerForTeam(roundNr, 1, thisTeamNr).getTheAnswer());
        //Dismiss the keyboard if its there
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etAnswer.getWindowToken(), 0);
        //etAnswer.onEditorAction(EditorInfo.IME_ACTION_DONE);
        refreshDisplayFragments();
        roundResultFrag.refresh();
    }

    @Override
    public void loadingComplete() {
        //Actions do do when we are completed (re)loading data from the Google sheet
        refreshDisplayFragments();
    }

    private void refreshAnswers() {
        //Update the displayed answer as needed
        displayAnswersAdapter.setAnswers(thisQuiz.getRound(roundSpinner.getPosition()).getQuestions());
        rvDisplayAnswers.setAdapter(displayAnswersAdapter);
    }

    //TODO: Move to MyActivity?
    protected void toggleFragments(int placeHolderID, Fragment fragToShow, Fragment fragToHide1, Fragment fragToHide2) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (fragToShow.isAdded()) { // if the fragment is already in container
            ft.show(fragToShow);
        } else { // fragment needs to be added to frame container
            ft.add(placeHolderID, fragToShow);
            ft.show(fragToShow);
        }
        // Hide other fragments
        if (fragToHide1.isAdded()) {
            ft.hide(fragToHide1);
        }
        if (fragToHide2.isAdded()) {
            ft.hide(fragToHide2);
        }
        // Commit changes
        ft.commit();
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
            //explainRoundStatus.setStatus("Please wait for this round to be corrected");
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
                //thisQuiz.calculateScores();
                QuizLoader quizLoader = new QuizLoader(C_ParticipantHome.this, thisQuiz.getListData().getSheetDocID());
                quizLoader.loadRounds();
                quizLoader.loadAllCorrections();
                roundResultFrag.refresh();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_c_participant_home);
        //Get the round spinner fragment
        roundSpinner = (FragRoundSpinner) getSupportFragmentManager().findFragmentById(R.id.frRoundSpinner);
        //Create the other fragments that are needed here
        //FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        questionSpinner = new FragSpinner();
        //roundResultFrag = new FragShowRoundScore();
        //ft.replace(R.id.frQuestionSpinner, questionSpinner, qSpinnerTag).commit();
        //Create the roundResults fragment
        //FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        roundResultFrag = new FragShowRoundScore();
        explainRoundStatus = new FragExplainRoundStatus();

        //roundResultFrag = new FragShowRoundScore();
        //getSupportFragmentManager().beginTransaction().replace(R.id.frQuestionSpinner, questionSpinner, qSpinnerTag).commit();
        setActionBarIcon();
        setActionBarTitle();
        //Log that the user logged in and set LoggedIn to TRUE
        thisTeamNr = thisQuiz.getMyLoginentity().getId();
        MyApplication.eventLogger.logEvent(thisQuiz.getMyLoginentity().getName(), EventLogger.LEVEL_INFO, "Logged in");
        thisQuiz.setLoggedIn(C_ParticipantHome.this,thisTeamNr,true);
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
                //questionSpinner.moveDown();
                //questionSpinner.moveUp();
                //TODO: report failure if round already closed!
                thisQuiz.setAnswerForTeam(roundSpinner.getPosition(), questionSpinner.getPosition(), thisTeamNr, etAnswer.getText().toString().trim());
                refreshDisplayFragments();
                thisQuiz.submitAnswers(C_ParticipantHome.this, roundSpinner.getPosition());
            }
        });
    }

    @Override
    protected void onPause() {
        MyApplication.eventLogger.logEvent(thisQuiz.getMyLoginentity().getName(), EventLogger.LEVEL_WARNING,"WARNING: Paused the app");
        thisQuiz.setLoggedIn(C_ParticipantHome.this,thisTeamNr,false);
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
            Toast.makeText(this, "Back button is disabled", Toast.LENGTH_SHORT).show();
            super.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }
}
