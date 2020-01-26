package com.paperlessquiz;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.paperlessquiz.adapters.CorrectAnswersAdapter;
import com.paperlessquiz.quiz.Answer;
import com.paperlessquiz.loadinglisteners.LoadingActivity;
import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.quiz.QuizLoader;
import com.paperlessquiz.quiz.Round;

import java.util.ArrayList;
//TODO: change colour of qSpinner if not all answers are corrected - current way is not working as it should
//TODO: refresh answers when refresh is clicked
//TODO: pass ID of loader when running LoadCompleted

/**
 * This class allows a corrector to mark answers as correct or not correct. The screen always has a round spinner to spin through the rounds.
 * Corrections are only possible if the status of a round is to allow this (acceptCorrections = true).
 * Standard is to correct all answers for a question. In that case, a questionspinner allows to scroll through the questions of the round and correct them
 * There is also the option to correct answers per team. In that case, the questionspinner is replaced by a teamSpinner
 * Team, round and question numbers are stored in the thisTeamNr, thisRoundNr, thisQuestionNr
 */

public class C_CorrectorHome extends MyActivity implements LoadingActivity, FragSpinner.HasSpinner, FragRoundSpinner.HasRoundSpinner, FragExplainRoundStatus.HasExplainRoundStatus {

    int thisTeamNr = 1, thisRoundNr = 1, thisQuestionNr = 1;
    String roundStatusExplanation;
    TextView tvCorrectAnswer;
    //Button btnSubmitCorrections;
    LinearLayout llCorrectQuestions;
    ListView lvCorrectQuestions;
    CorrectAnswersAdapter myAdapter;
    FragRoundSpinner rndSpinner;
    FragSpinner spinner;
    FragExplainRoundStatus explainRoundStatus;
    ArrayList<Answer> allAnswers;
    boolean correctPerQuestion = true;
    QuizLoader quizLoader;
    boolean roundsLoaded, answersLoaded, fullQuestionsLoaded;

    @Override
    public String getRoundStatusExplanation() {
        return roundStatusExplanation;
    }

    @Override
    //TODO: update round status when spinning through rounds
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
        if (correctPerQuestion) {
            thisQuestionNr = newPos;
            showCorrectAnswerForQuestion(thisRoundNr, thisQuestionNr);
        } else {
            thisTeamNr = newPos;
        }
        refreshAnswers();
        /*
        if (myAdapter.allAnswersCorrected){
            spinner.changeSpinnerColor(R.color.correctGreen);
        } else {
            spinner.changeSpinnerColor(R.color.wrongRed);
        }
        */
    }


    @Override
    public int getSizeOfSpinnerArray() {
        if (correctPerQuestion) {
            return thisQuiz.getRound(thisRoundNr).getQuestions().size();
        } else {
            return thisQuiz.getTeams().size();
        }
    }


    @Override
    public String getValueToSetForPrimaryField(int spinnerPos) {
        if (correctPerQuestion) {
            return thisQuiz.getQuestion(thisRoundNr, spinnerPos).getQuestionNr() + ". " + thisQuiz.getQuestion(thisRoundNr, spinnerPos).getName();
        } else {
            return "Ploeg " + Integer.toString(thisQuiz.getTeam(spinnerPos).getUserNr());
        }
    }

    @Override
    public String getValueToSetForSecondaryField(int spinnerPos) {
        if (correctPerQuestion) {
            return "(" + thisQuiz.getQuestion(thisRoundNr, spinnerPos).getHint() + ")";
        } else {
            return thisQuiz.getTeam(spinnerPos).getName();
        }
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
            /*rndSpinner.positionChanged();
            if (spinner.callingActivity != null) {
                spinner.positionChanged();
            }
            */
            refreshDisplayFragments();
            rndSpinner.refreshIcons();
        }
    }

    private void loadQuiz() {
        quizLoader.loadRounds();
        quizLoader.loadAllAnswers();
        quizLoader.loadFullQuestions();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.corrector, menu);
        //Hide items for the correction per team/per question button as needed
        if (thisQuiz.getRound(thisRoundNr).getRoundStatus() == QuizDatabase.ROUNDSTATUS_OPENFORCORRECTIONS) {
            //Show corrector buttons
            if (correctPerQuestion) {
                menu.findItem(R.id.rounds).setVisible(false);
                menu.findItem(R.id.teams).setVisible(true);
            } else {
                menu.findItem(R.id.rounds).setVisible(true);
                menu.findItem(R.id.teams).setVisible(false);
            }
        } else {
            menu.findItem(R.id.rounds).setVisible(false);
            menu.findItem(R.id.teams).setVisible(false);
        }
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
            case R.id.teams:
                correctPerQuestion = !correctPerQuestion;
                spinner.moveToFirstPos();
                refreshAnswers();
                showCorrectAnswerForQuestion(thisRoundNr, 1);
                //spinner.positionChanged();
                break;
            case R.id.rounds:
                correctPerQuestion = !correctPerQuestion;
                spinner.moveToFirstPos();
                refreshAnswers();
                showCorrectAnswerForQuestion(thisRoundNr, 1);
                //spinner.positionChanged();
                break;
/*
            case R.id.allcorrect:
                for (int i = 0; i < allAnswers.size(); i++) {
                    allAnswers.get(i).setCorrect(true);
                    myAdapter.notifyDataSetChanged();
                }
                break;
*/
            case R.id.allwrong:
                for (int i = 0; i < allAnswers.size(); i++) {
                    if (!(allAnswers.get(i).isCorrected())) {
                        allAnswers.get(i).setCorrect(false);
                        allAnswers.get(i).setCorrected(true);
                    }
                }
                quizLoader.setAllAnswersToFalse(thisQuiz.getQuestionID(thisRoundNr, thisQuestionNr), 0);
                myAdapter.notifyDataSetChanged();
                break;

        }
        //Force onCreateoptionsMenu to run again to make sure buttons are hidden and shown as needed
        supportInvalidateOptionsMenu();
        return super.onOptionsItemSelected(item);
    }

    private void refreshAnswers() {
        if (correctPerQuestion) {
            allAnswers = thisQuiz.getQuestion(thisRoundNr, thisQuestionNr).getAllAnswers();
        } else { //Correct per team
            allAnswers = thisQuiz.getAnswersForRound(thisRoundNr, thisTeamNr);
        }
        myAdapter = new CorrectAnswersAdapter(this, allAnswers);
        lvCorrectQuestions.setAdapter(myAdapter);
        myAdapter.setThisRoundNr(thisRoundNr);
        myAdapter.setThisTeamNr(thisTeamNr);
        myAdapter.setCorrectPerQuestion(correctPerQuestion);

    }

    private void showCorrectAnswerForQuestion(int roundNr, int questionNr) {
        //tvCorrectAnswer.setText(thisQuiz.getQuestion(roundNr, questionNr).getQuestionNr() + ": " + thisQuiz.getQuestion(roundNr, questionNr).getCorrectAnswer());
        tvCorrectAnswer.setText(thisQuiz.getQuestion(roundNr, questionNr).getCorrectAnswer());
        if (correctPerQuestion) {
            tvCorrectAnswer.setVisibility(View.VISIBLE);
        } else {
            tvCorrectAnswer.setVisibility(View.GONE);
        }
    }

    private void refreshDisplayFragments() {
        //Refresh what is in the display based on the current value of roundSpinner position
        Round thisRound = thisQuiz.getRound(thisRoundNr);
        switch (thisRound.getRoundStatus()) {
            case QuizDatabase.ROUNDSTATUS_CLOSED:
                //Round is not yet open
                //Just display the fragment that tells you this
                roundStatusExplanation = C_CorrectorHome.this.getString(R.string.corrector_waitforroundopen);
                explainRoundStatus.setStatus(roundStatusExplanation);
                toggleFragments(R.id.frPlaceHolder, explainRoundStatus, spinner, spinner);
                llCorrectQuestions.setVisibility((View.GONE));
                break;
            case QuizDatabase.ROUNDSTATUS_OPENFORANSWERS:
                //Round is open for the teams
                //Just display the fragment that tells you this
                roundStatusExplanation = C_CorrectorHome.this.getString(R.string.corrector_waitforallanswers);
                explainRoundStatus.setStatus(roundStatusExplanation);
                toggleFragments(R.id.frPlaceHolder, explainRoundStatus, spinner, spinner);
                llCorrectQuestions.setVisibility((View.GONE));
                break;
            case QuizDatabase.ROUNDSTATUS_OPENFORCORRECTIONS:
                //Round is open for the corrector
                toggleFragments(R.id.frPlaceHolder, spinner, explainRoundStatus, explainRoundStatus);
                llCorrectQuestions.setVisibility((View.VISIBLE));
                showCorrectAnswerForQuestion(thisRoundNr, thisQuestionNr);
                refreshAnswers();
                break;
            case QuizDatabase.ROUNDSTATUS_CORRECTED:
                //Round is open for the teams
                //Just display the fragment that tells you this
                roundStatusExplanation = C_CorrectorHome.this.getString(R.string.corrector_roundalreadycorrected);
                explainRoundStatus.setStatus(roundStatusExplanation);
                toggleFragments(R.id.frPlaceHolder, explainRoundStatus, spinner, spinner);
                llCorrectQuestions.setVisibility((View.GONE));
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
