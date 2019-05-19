package com.example.paperlessquiz.spinners;

import android.widget.TextView;

import com.example.paperlessquiz.quiz.Quiz;
import com.example.paperlessquiz.round.Round;

import java.util.ArrayList;

public class RoundSpinner {

    private ArrayList<Round> roundsList;
    private int position;
    private int oldPosition;
    private int curPosition;
    private TextView tvMain;
    private TextView tvSub;
    private QuestionSpinner questionSpinner;
    private Quiz thisQuiz;

    /*Question q;
    SpinnerData s;
    ArrayList<Question> qList;
    ArrayList<SpinnerData> sList;
    */

    public RoundSpinner(ArrayList<Round> roundsList, TextView tvMain, TextView tvSub, QuestionSpinner questionSpinner, Quiz thisQuiz) {
        this.roundsList = roundsList;
        this.tvMain = tvMain;
        this.tvSub = tvSub;
        this.position = 0;
        this.oldPosition = roundsList.size()-1;
        //this.curPosition = 0;
        this.questionSpinner = questionSpinner;
        this.thisQuiz = thisQuiz;


        //s = q; // Okay because q implements interface s
        //sList=(ArrayList<SpinnerData>)qList; // Why is this not Okay?
    }

    public void positionChanged() {
        //Move up the questionspinner in case it contains an answer that was not saved yet
        questionSpinner.moveUp();
        questionSpinner.moveDown();
        //thisQuiz.setAnswersForRound(oldPosition,questionSpinner.getRoundAnswers());
        tvMain.setText(roundsList.get(position).getName());
        tvSub.setText(roundsList.get(position).getDescription());
        //Set the questons and answers of the questionspinner to those of the next round
        questionSpinner.setArrayList(thisQuiz.getRound(position).getQuestions());
        questionSpinner.setRoundAnswers(thisQuiz.getMyAnswers().get(position));

    }

    public void moveUp() {
        oldPosition=position;
        if (position == roundsList.size()-1) {
            position = 0;
        } else {
            position++;
        }
        positionChanged();
    }

    public void moveDown() {
        oldPosition=position;
        if (position == 0) {
            position = roundsList.size()-1;
        } else {
            position--;
        }
        positionChanged();
    }

    public void setArrayList(ArrayList<Round> roundsList) {
        this.roundsList = roundsList;
    }
}