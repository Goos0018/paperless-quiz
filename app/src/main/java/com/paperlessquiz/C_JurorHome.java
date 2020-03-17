package com.paperlessquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.paperlessquiz.adapters.CorrectAnswersAdapter;
import com.paperlessquiz.loadinglisteners.LoadingActivity;
import com.paperlessquiz.quiz.Answer;
import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.quiz.QuizLoader;
import com.paperlessquiz.quiz.Round;

import java.util.ArrayList;

/**
 * This class allows the Juror to mark answers as correct or not correct. The screen always has a round spinner to spin through the rounds and a team spinner to spin through the teams.
 * Corrections are only possible if the status of a round is CLOSED (already corrected by the correctors.
 * Team, round and question numbers are stored in the thisTeamNr, thisRoundNr, thisQuestionNr
 */

public class C_JurorHome extends MyActivity implements LoadingActivity, FragSpinner.HasSpinner, FragRoundSpinner.HasRoundSpinner, FragExplainRoundStatus.HasExplainRoundStatus {

    int thisTeamNr = 1, thisRoundNr = 1, thisQuestionNr = 1;
    String roundStatusExplanation;
    TextView tvCorrectAnswer;
    LinearLayout llCorrectQuestions;
    ListView lvCorrectQuestions;
    CorrectAnswersAdapter myAdapter;
    FragRoundSpinner rndSpinner;
    FragSpinner spinner;
    FragExplainRoundStatus explainRoundStatus;
    ArrayList<Answer> allAnswers;
    QuizLoader quizLoader;
    boolean roundsLoaded, answersLoaded, fullQuestionsLoaded;

    @Override
    public String getRoundStatusExplanation() {
        return roundStatusExplanation;
    }

    @Override
    public void onRoundChanged(int oldRoundNr, int roundNr) {
        this.thisRoundNr = roundNr;
        refreshDisplayFragments();
        spinner.moveToFirstPos();
        if (myAdapter != null) {
            myAdapter.setThisRoundNr(roundNr);
        }
        showCorrectAnswerForQuestion(thisRoundNr, 1);
    }

    @Override
    public void onSpinnerChange(int oldPos, int newPos) {
        thisTeamNr = newPos;
        refreshAnswers();
    }


    @Override
    public int getSizeOfSpinnerArray() {
        return thisQuiz.getTeams().size();
    }


    @Override
    public String getValueToSetForPrimaryField(int spinnerPos) {
        return "Ploeg " + Integer.toString(thisQuiz.getTeam(spinnerPos).getUserNr());
    }

    @Override
    public String getValueToSetForSecondaryField(int spinnerPos) {
        return thisQuiz.getTeam(spinnerPos).getName();
    }


    @Override
    public void loadingComplete(int requestID) {

        switch (requestID) {
            case QuizDatabase.REQUEST_ID_GET_ROUNDS:
                roundsLoaded = true;
                break;
            case QuizDatabase.REQUEST_ID_GET_ALLANSWERS:
                answersLoaded = true;
                break;
            case QuizDatabase.REQUEST_ID_GET_FULLQUESTIONS:
                fullQuestionsLoaded = true;
                break;
        }
        //If everything is properly loaded, we can start populating the central Quiz object
        if (roundsLoaded && answersLoaded && fullQuestionsLoaded) {
            //reset the loading statuses
            roundsLoaded = false;
            answersLoaded = false;
            fullQuestionsLoaded = false;
            quizLoader.updateRoundsIntoQuiz();
            quizLoader.updateAnswersIntoQuiz();
            quizLoader.updateFullQuestionsIntoQuiz();
            refreshDisplayFragments();
            rndSpinner.refreshIcons();
        }
    }

    private void loadQuiz() {
        roundsLoaded = false;
        answersLoaded = false;
        fullQuestionsLoaded = false;
        quizLoader.loadRounds();
        quizLoader.loadAllAnswers();
        quizLoader.loadFullQuestions();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.juror, menu);
        //MenuItem correctPerRndItem = menu.findItem(R.id.rounds);
        //MenuItem correctPerTeamItem = menu.findItem(R.id.teams);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                loadQuiz();
                break;
            case R.id.messages:
                //Reload orders, using the given filters
                Intent intent = new Intent(C_JurorHome.this, C_MessagesHome.class);
                startActivity(intent);
                break;
        }
        //Force onCreateoptionsMenu to run again to make sure buttons are hidden and shown as needed
        //supportInvalidateOptionsMenu();
        return super.onOptionsItemSelected(item);
    }

    private void refreshAnswers() {
        allAnswers = thisQuiz.getAnswersForRound(thisRoundNr, thisTeamNr);
        myAdapter = new CorrectAnswersAdapter(this, allAnswers);
        lvCorrectQuestions.setAdapter(myAdapter);
        myAdapter.setThisRoundNr(thisRoundNr);
        myAdapter.setThisTeamNr(thisTeamNr);
        myAdapter.setCorrectPerQuestion(false);
    }

    private void showCorrectAnswerForQuestion(int roundNr, int questionNr) {
        tvCorrectAnswer.setVisibility(View.GONE);
    }

    private void refreshDisplayFragments() {
        //Refresh what is in the display based on the current value of roundSpinner position
        Round thisRound = thisQuiz.getRound(thisRoundNr);
        switch (thisRound.getRoundStatus()) {
            case QuizDatabase.ROUNDSTATUS_CLOSED:
                //Round is not yet open
                //Just display the fragment that tells you this
                roundStatusExplanation = C_JurorHome.this.getString(R.string.corrector_waitforroundopen);
                explainRoundStatus.setStatus(roundStatusExplanation);
                toggleFragments(R.id.frPlaceHolder, explainRoundStatus, spinner, spinner);
                llCorrectQuestions.setVisibility((View.GONE));
                break;
            case QuizDatabase.ROUNDSTATUS_OPENFORANSWERS:
                //Round is open for the teams
                //Just display the fragment that tells you this
                roundStatusExplanation = C_JurorHome.this.getString(R.string.corrector_waitforallanswers);
                explainRoundStatus.setStatus(roundStatusExplanation);
                toggleFragments(R.id.frPlaceHolder, explainRoundStatus, spinner, spinner);
                llCorrectQuestions.setVisibility((View.GONE));
                break;
            case QuizDatabase.ROUNDSTATUS_OPENFORCORRECTIONS:
                //Round is open for the corrector - the juror shouldn't do anything yet
                //Just display the fragment that tells you this
                roundStatusExplanation = C_JurorHome.this.getString(R.string.juror_roundbeingcorrected);
                explainRoundStatus.setStatus(roundStatusExplanation);
                toggleFragments(R.id.frPlaceHolder, explainRoundStatus, spinner, spinner);
                llCorrectQuestions.setVisibility((View.GONE));
                break;
            case QuizDatabase.ROUNDSTATUS_CORRECTED:
                //Round is corrected and closed => now the juror can do his job
                toggleFragments(R.id.frPlaceHolder, spinner, explainRoundStatus, explainRoundStatus);
                llCorrectQuestions.setVisibility((View.VISIBLE));
                showCorrectAnswerForQuestion(thisRoundNr, thisQuestionNr);
                refreshAnswers();
                break;
        }
        //Force onCreateoptionsMenu to run again
        supportInvalidateOptionsMenu();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //allAnswers = new ArrayList<>();

        setContentView(R.layout.act_c_corrector_home);
        //RoundSpinner is in the layout, create the dynamic fragments that will be used
        rndSpinner = (FragRoundSpinner) getSupportFragmentManager().findFragmentById(R.id.frRoundSpinner);
        spinner = new FragSpinner();
        explainRoundStatus = new FragExplainRoundStatus();
        //Set the action bar
        setActionBarIcon();
        setActionBarTitle();
        //Get handles to everything in the layout
        llCorrectQuestions = findViewById(R.id.llCorrectQuestions);
        tvCorrectAnswer = findViewById(R.id.tvCorrectAnswer);
        //btnSubmitCorrections = findViewById(R.id.btnSubmitCorrections);
        lvCorrectQuestions = findViewById(R.id.lvCorrectQuestions);
        //OnRoundChange and onSpinnerChanged are called by the respective fragments
        quizLoader = new QuizLoader(this);
        loadQuiz();
    }

}
