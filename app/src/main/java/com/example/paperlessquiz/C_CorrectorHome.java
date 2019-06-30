package com.example.paperlessquiz;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.paperlessquiz.adapters.CorrectAnswersAdapter;
import com.example.paperlessquiz.answer.Answer;
import com.example.paperlessquiz.google.access.LoadingActivity;
import com.example.paperlessquiz.quiz.QuizLoader;
import com.example.paperlessquiz.round.Round;

import java.util.ArrayList;
//TODO: change colour of qSpinner if not all answers are corrected - current way is not working as it should
//TODO: refresh answers when refresh is clicked
//TODO: pass ID of loader when running LoadCompleted

/**
 * This class is allows a corrector to mark answers as correct or not correct. The screen always has a round spinner to spin through the rounds.
 * Corrections are only possible if the status of a round is to allow this (acceptCorrections = true).
 * Standard is to correct all answers for a question. In that case, a questionspinner allows to scroll through the questions of the round and correct them
 * There is also the option to correct answers per team. In that case, the questionspinner is replaced by a teamSpinner
 * Team, round and question numbers are stored in the thisTeamNr, thisRoundNr, thisQuestionNr
 */

public class C_CorrectorHome extends MyActivity implements LoadingActivity, FragSpinner.HasSpinner, FragRoundSpinner.HasRoundSpinner, FragExplainRoundStatus.HasExplainRoundStatus {

    int thisTeamNr = 1, thisRoundNr = 1, thisQuestionNr = 1;
    String roundStatusExplanation;
    TextView tvCorrectAnswer;
    Button btnSubmitCorrections;
    LinearLayout llCorrectQuestions;
    ListView lvCorrectQuestions;
    CorrectAnswersAdapter myAdapter;
    FragRoundSpinner rndSpinner;
    FragSpinner spinner;
    FragExplainRoundStatus explainRoundStatus;
    ArrayList<Answer> allAnswers;
    boolean correctPerQuestion = true;
    QuizLoader quizLoader;

    @Override
    public String getRoundStatusExplanation() {
        return roundStatusExplanation;
    }

    @Override
    public void onRoundChanged(int oldRoundNr, int roundNr) {
        this.thisRoundNr = roundNr;
        refreshDisplayFragments();
        spinner.moveToFirstPos();
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
        if (correctPerQuestion) {
        } else {

        }
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
            return thisQuiz.getQuestion(thisRoundNr, spinnerPos).getName();
        } else {
            return Integer.toString(thisQuiz.getTeam(spinnerPos).getId());
        }
    }

    @Override
    public String getValueToSetForSecondaryField(int spinnerPos) {
        if (correctPerQuestion) {
            return thisQuiz.getQuestion(thisRoundNr, spinnerPos).getHint();
        } else {
            return thisQuiz.getTeam(spinnerPos).getName();
        }
    }


    @Override
    public void loadingComplete() {
        //If this was the quizloader for the rounds, we don't want to set the corrections
        if (quizLoader.quizCorrectionsLPL.getAllCorrectionsPerRound().size() == 0) {
        } else {
            thisQuiz.setAllCorrectionsPerQuestion(quizLoader.quizCorrectionsLPL.getAllCorrectionsPerRound());
        }
        rndSpinner.positionChanged();
        if (spinner.callingActivity != null) {
            spinner.positionChanged();
        }
        refreshDisplayFragments();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.corrector, menu);
        //Hide items for the correction per team/per question button as needed
        if (thisQuiz.getRound(thisRoundNr).getAcceptsCorrections()) {
            //Show corrector buttons
            if (correctPerQuestion) {
                menu.findItem(R.id.rounds).setVisible(false);
            } else {
                menu.findItem(R.id.teams).setVisible(false);
            }
            menu.findItem(R.id.allcorrect).setVisible(true);
            menu.findItem(R.id.allwrong).setVisible(true);
        } else {
            menu.findItem(R.id.rounds).setVisible(false);
            menu.findItem(R.id.teams).setVisible(false);
            menu.findItem(R.id.allcorrect).setVisible(false);
            menu.findItem(R.id.allwrong).setVisible(false);
        }
        //MenuItem correctPerRndItem = menu.findItem(R.id.rounds);
        //MenuItem correctPerTeamItem = menu.findItem(R.id.teams);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                quizLoader = new QuizLoader(C_CorrectorHome.this, thisQuiz.getListData().getSheetDocID());
                quizLoader.loadRounds();
                quizLoader.loadAllCorrections();
                break;
            case R.id.teams:
                correctPerQuestion = !correctPerQuestion;
                spinner.moveToFirstPos();
                refreshAnswers();
                showCorrectAnswerForQuestion(thisRoundNr, 1);
                //spinner.positionChanged();
                break;
            case R.id.allcorrect:
                for (int i = 0; i < allAnswers.size(); i++) {
                    allAnswers.get(i).setCorrect(true);
                    myAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.allwrong:
                for (int i = 0; i < allAnswers.size(); i++) {
                    allAnswers.get(i).setCorrect(false);
                    myAdapter.notifyDataSetChanged();
                }
                break;
        }
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
    }

    private void showCorrectAnswerForQuestion(int roundNr, int questionNr) {
        tvCorrectAnswer.setText(thisQuiz.getQuestion(thisRoundNr, thisQuestionNr).getQuestionID() + ": " + thisQuiz.getQuestion(thisRoundNr, thisQuestionNr).getCorrectAnswer());
    }

    private void refreshDisplayFragments() {
        //Refresh what is in the display based on the current value of roundSpinner position
        Round thisRound = thisQuiz.getRound(thisRoundNr);
        if (!(thisRound.getAcceptsAnswers() || thisRound.getAcceptsCorrections() || thisRound.isCorrected())) {
            //Round is not yet open
            //Just display the fragment that tells you this
            roundStatusExplanation = C_CorrectorHome.this.getString(R.string.corrector_waitforroundopen);
            explainRoundStatus.setStatus(roundStatusExplanation);
            toggleFragments(R.id.frPlaceHolder, explainRoundStatus, spinner, spinner);
            llCorrectQuestions.setVisibility((View.GONE));
        }
        if (thisRound.getAcceptsAnswers()) {
            //Round is open for the teams
            //Just display the fragment that tells you this
            roundStatusExplanation = C_CorrectorHome.this.getString(R.string.corrector_waitforallanswers);
            explainRoundStatus.setStatus(roundStatusExplanation);
            toggleFragments(R.id.frPlaceHolder, explainRoundStatus, spinner, spinner);
            llCorrectQuestions.setVisibility((View.GONE));
        }
        if (thisRound.getAcceptsCorrections()) {
            //Round is open for the corrector
            toggleFragments(R.id.frPlaceHolder, spinner, explainRoundStatus, explainRoundStatus);
            llCorrectQuestions.setVisibility((View.VISIBLE));
            showCorrectAnswerForQuestion(thisRoundNr, thisQuestionNr);
            refreshAnswers();
        }
        if (thisRound.isCorrected()) {
            //Round is open for the teams
            //Just display the fragment that tells you this
            roundStatusExplanation = C_CorrectorHome.this.getString(R.string.corrector_roundalreadycorrected);
            explainRoundStatus.setStatus(roundStatusExplanation);
            toggleFragments(R.id.frPlaceHolder, explainRoundStatus, spinner, spinner);
            llCorrectQuestions.setVisibility((View.GONE));
        }
        //Force onCreateoptionsMenu to run again
        supportInvalidateOptionsMenu();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        btnSubmitCorrections = findViewById(R.id.btnSubmitCorrections);
        lvCorrectQuestions = findViewById(R.id.lvCorrectQuestions);
        btnSubmitCorrections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (correctPerQuestion) {
                    thisQuiz.submitCorrectionsForQuestion(C_CorrectorHome.this, thisRoundNr, thisQuestionNr);
                } else {
                    thisQuiz.submitCorrectionsForTeam(C_CorrectorHome.this, thisRoundNr, thisTeamNr);
                }
            }
        });
        //OnRoundChange and onSpinnerChanged are called by the respective fragments
    }

    /*
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
    */

}
