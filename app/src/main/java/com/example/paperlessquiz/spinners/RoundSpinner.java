package com.example.paperlessquiz.spinners;

import android.widget.TextView;

import com.example.paperlessquiz.quiz.Quiz;

public class RoundSpinner {

    private Quiz quiz;
    private int roundNr;
    private int oldRoundNr;
    private TextView tvMain;
    private TextView tvSub;
    private QuestionSpinner questionSpinner;

    public RoundSpinner(Quiz quiz, TextView tvMain, TextView tvSub, QuestionSpinner questionSpinner) {
        this.quiz = quiz;
        this.tvMain = tvMain;
        this.tvSub = tvSub;
        this.roundNr = 1; //New round spinnner always starts with round 1
        this.oldRoundNr = quiz.getRounds().size();
        this.questionSpinner = questionSpinner;
        //When creating a roundspinner, always start with Round 1
        moveTo(1);
    }

    public void positionChanged() {
        //Move up the questionspinner to the first question
        questionSpinner.moveTo(1);
        tvMain.setText(quiz.getRound(roundNr).getName());
        tvSub.setText(quiz.getRound(roundNr).getDescription());
        //Tell the questionspinner the round has changed and go the the first question of this new round
        questionSpinner.setRoundNr(roundNr);
        questionSpinner.initialize(1);
        //Set the questions and answers of the questionspinner to those of the next round
    }

    public void moveTo(int newRoundNr){
        oldRoundNr = roundNr;
        if(newRoundNr <= quiz.getRounds().size()){
            roundNr = newRoundNr;
        }
        else {
            roundNr = 1;
        }
        positionChanged();
    }

    public void moveUp() {
        oldRoundNr = roundNr;
        if (roundNr == quiz.getRounds().size()) {
            roundNr = 1;
        } else {
            roundNr++;
        }
        positionChanged();
    }

    public void moveDown() {
        oldRoundNr = roundNr;
        if (roundNr == 1) {
            roundNr = quiz.getRounds().size();
        } else {
            roundNr--;
        }
        positionChanged();
    }

    public int getRoundNr() {
        return roundNr;
    }
}