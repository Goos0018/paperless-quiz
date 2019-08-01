package com.paperlessquiz;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.paperlessquiz.adapters.DisplayAnswersAdapter;
import com.paperlessquiz.googleaccess.EventLogger;
import com.paperlessquiz.googleaccess.LoadingActivity;
import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.quiz.QuizLoader;
import com.paperlessquiz.round.Round;
import com.paperlessquiz.users.User;

import java.util.Date;

/**
 * Home screen for teams. Contains a round spinner and a question spinner + fields that allow to answer the question that is currently selected
 * + button to indicate you are done answering the questions for this round.
 * Parts of the screen are hidden based on round status.
 * Answers are sent to the central db when spinning through the questions
 * Info is refreshed from the central db when spinning through the rounds
 */
public class C_ParticipantHome extends MyActivity implements LoadingActivity, FragSpinner.HasSpinner,
        FragRoundSpinner.HasRoundSpinner, FragShowRoundScore.HasShowRoundScore, FragExplainRoundStatus.HasExplainRoundStatus {

    int thisTeamNr;
    User thisTeam;
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
    long totalTimePaused;
    Date lastPausedDate;
    QuizLoader quizLoader;
    boolean roundsLoaded, answersLoaded, scoresLoaded;

    @Override
    public void loadingComplete(int requestID) {
        switch (requestID) {
            case QuizDatabase.REQUEST_ID_GET_ROUNDS:
                roundsLoaded = true;
                break;
            case QuizDatabase.REQUEST_ID_GET_ANSWERS:
                answersLoaded = true;
                break;
            case QuizDatabase.REQUEST_ID_GET_RESULTS:
                scoresLoaded = true;
                break;
        }
        //If everything is properly loaded, we can start populating the central Quiz object
        if (roundsLoaded && answersLoaded && scoresLoaded) {
            //reset the loading statuses
            roundsLoaded = false;
            answersLoaded = false;
            scoresLoaded = false;
            quizLoader.updateRounds();
            quizLoader.updateAnswersIntoQuiz();
            quizLoader.loadResultsIntoQuiz();
            roundSpinner.refreshIcons();
            roundResultFrag.refresh();  //This will recalculate scores based on the re-loaded corrections
            refreshDisplayFragments();  //Display the correct fragments based on new round status etc.
        }
    }

    private void updateQuiz() {
        //TODO: show a toast when starting this and dismiss it when loading is completely done
        quizLoader.updateQuizForUser();
        //The rest is done when loading is complete
    }

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
    //This is called from the round spinner
    public void onRoundChanged(int oldRoundNr, int roundNr) {
        //Similar as with a questionSpinner change, we save the answer that we have and load the new answer - only do this if the field was visible
        if (etAnswer.getVisibility() == View.VISIBLE) {
            //Update the answer if it was changed
            String oldAnswer = thisQuiz.getAnswerForTeam(oldRoundNr, questionSpinner.getPosition(), thisTeamNr).getTheAnswer();
            String newAnswer = etAnswer.getText().toString().trim();
            if (!(oldAnswer.equals(newAnswer))) {
                int questionID = thisQuiz.getQuestionID(oldRoundNr, questionSpinner.getPosition());
                thisQuiz.setAnswerForTeam(oldRoundNr, questionSpinner.getPosition(), thisTeamNr, newAnswer);
                //Store the answer in the central quiz db
                quizLoader.submitAnswer(questionID, newAnswer);
            }
        }
        //Set the value of the answer for the new question to what we already have in the central Quiz object
        //Move the QuestionSpinner to position 1 to make sure we have something at that position
        questionSpinner.moveToFirstPos();
        etAnswer.setText(thisQuiz.getAnswerForTeam(roundNr, 1, thisTeamNr).getTheAnswer());
        //Dismiss the keyboard if its there
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etAnswer.getWindowToken(), 0);
        //Update info from the central database
        updateQuiz();
        //Rest is done when loading is complete
        //refreshDisplayFragments();
        //roundResultFrag.refresh();
    }

    @Override
    //This is called from the Question spinner
    public void onSpinnerChange(int oldPos, int newPos) {
        //Update the answer if it was changed
        String oldAnswer = thisQuiz.getAnswerForTeam(roundSpinner.getPosition(), oldPos, thisTeamNr).getTheAnswer();
        String newAnswer = etAnswer.getText().toString().trim();
        if (!(oldAnswer.equals(newAnswer))) {
            int questionID = thisQuiz.getQuestionID(roundSpinner.getPosition(), oldPos);
            thisQuiz.setAnswerForTeam(roundSpinner.getPosition(), oldPos, thisTeamNr, newAnswer);
            //Store the answer in the central quiz db
            quizLoader.submitAnswer(questionID, newAnswer);
        }
        //Set the value of the answer for the new question to what we already have in the Quiz object
        etAnswer.setText(thisQuiz.getAnswerForTeam(roundSpinner.getPosition(), newPos, thisTeamNr).getTheAnswer());
        //Dismiss the keyboard if its there
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etAnswer.getWindowToken(), 0);
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
    //End stuff for the QuestionSpinner

    private void refreshAnswers() {
        //Update the displayed answers for this round as needed
        displayAnswersAdapter.setAnswers(thisQuiz.getRound(roundSpinner.getPosition()).getQuestions());
        rvDisplayAnswers.setAdapter(displayAnswersAdapter);
    }

    private void refreshDisplayFragments() {
        //Refresh what is in the display based on the current values of roundSpinner position
        Round thisRound = thisQuiz.getRound(roundSpinner.getPosition());
        switch (thisRound.getRoundStatus()) {
            case QuizDatabase.ROUNDSTATUS_CLOSED:
                roundStatusExplanation = C_ParticipantHome.this.getString(R.string.participant_waitforroundopen);
                explainRoundStatus.setStatus(roundStatusExplanation);
                toggleFragments(R.id.frPlaceHolder, explainRoundStatus, roundResultFrag, questionSpinner);
                editAnswerLayout.setVisibility((View.GONE));
                displayAnswersLayout.setVisibility(View.GONE);
                break;
            case QuizDatabase.ROUNDSTATUS_OPENFORANSWERS:
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
                break;
            case QuizDatabase.ROUNDSTATUS_OPENFORCORRECTIONS:
                //Round is closed for answering, but not yet corrected
                //Just display the fragment that tells you this
                roundStatusExplanation = C_ParticipantHome.this.getString(R.string.participant_waitforroundcorrection);
                explainRoundStatus.setStatus(roundStatusExplanation);
                toggleFragments(R.id.frPlaceHolder, explainRoundStatus, roundResultFrag, questionSpinner);
                editAnswerLayout.setVisibility((View.GONE));
                displayAnswersLayout.setVisibility(View.GONE);
                break;
            case QuizDatabase.ROUNDSTATUS_CORRECTED:
                //Show the RoundResults Fragment
                toggleFragments(R.id.frPlaceHolder, roundResultFrag, questionSpinner, explainRoundStatus);
                //Hide the layout to edit answers
                editAnswerLayout.setVisibility((View.GONE));
                displayAnswersLayout.setVisibility(View.VISIBLE);
                break;
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
                updateQuiz();
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
        //Create the other fragments that are needed here and set the action bar icon and title
        questionSpinner = new FragSpinner();
        roundResultFrag = new FragShowRoundScore();
        explainRoundStatus = new FragExplainRoundStatus();
        setActionBarIcon();
        setActionBarTitle();
        //Log that the user logged in and set LoggedIn to TRUE so we know this to track if the user pauses the app
        thisTeamNr = thisQuiz.getThisUser().getUserNr();
        MyApplication.eventLogger.logEvent(thisQuiz.getThisUser().getName(), EventLogger.LEVEL_INFO, "Logged in");
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
        //Initialize the adapter
        displayAnswersAdapter = new DisplayAnswersAdapter(this, thisQuiz.getRound(1).getQuestions(), thisTeamNr);
        quizLoader = new QuizLoader(C_ParticipantHome.this);
        thisTeam = thisQuiz.getThisUser();
        quizLoader.updateMyStatus(QuizDatabase.USERSTATUS_PRESENTLOGGEDIN);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Dismiss the keyboard if its there
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etAnswer.getWindowToken(), 0);
                //Update the answer if it was changed
                String oldAnswer = thisQuiz.getAnswerForTeam(roundSpinner.getPosition(), questionSpinner.getPosition(), thisTeamNr).getTheAnswer();
                String newAnswer = etAnswer.getText().toString().trim();
                if (!(oldAnswer.equals(newAnswer))) {
                    int questionID = thisQuiz.getQuestionID(roundSpinner.getPosition(), questionSpinner.getPosition());
                    thisQuiz.setAnswerForTeam(roundSpinner.getPosition(), questionSpinner.getPosition(), thisTeamNr, newAnswer);
                    //Store the answer in the central quiz db
                    quizLoader.submitAnswer(questionID, newAnswer);
                    refreshDisplayFragments();
                    //thisQuiz.submitAnswers(C_ParticipantHome.this, roundSpinner.getPosition());
                    quizLoader.setAnswersSubmitted(roundSpinner.getPosition());
                }
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        displayPDF(thisQuiz.getListData().getQuizPdfURL(), C_ParticipantHome.this.getString(R.string.participant_aboutquiztitle));
    }

    @Override
    protected void onPause() {
        if (thisQuiz.isAnyRoundOpen()) {
            lastPausedDate = new Date();
            MyApplication.eventLogger.logEvent(thisQuiz.getThisUser().getName(), EventLogger.LEVEL_INFO, "INFO: Paused the app");
            thisQuiz.setLoggedIn(C_ParticipantHome.this, thisTeamNr, false);
            MyApplication.setAppPaused(true);
        }
        quizLoader.updateMyStatus(QuizDatabase.USERSTATUS_PRESENTNOTLOGGEDIN);
        super.onPause();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (MyApplication.isAppPaused()) {
            Date dateResumed = new Date();
            long timePaused = (dateResumed.getTime() - lastPausedDate.getTime()) / 1000;
            totalTimePaused = totalTimePaused + timePaused;
            MyApplication.eventLogger.logEvent(thisQuiz.getThisUser().getName(), EventLogger.LEVEL_WARNING, "WARNING: Resumed the app after "
                    + timePaused + " seconds - total time paused is now " + totalTimePaused + " seconds");
            thisQuiz.setLoggedIn(C_ParticipantHome.this, thisTeamNr, true);
            MyApplication.setAppPaused(false);
        }
        quizLoader.updateMyStatus(QuizDatabase.USERSTATUS_PRESENTLOGGEDIN);
    }

    @Override
    public void onBackPressed() {
        if (true) {
            Toast.makeText(this, C_ParticipantHome.this.getString(R.string.participant_nobackallowed), Toast.LENGTH_SHORT).show();
            //super.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }
}
