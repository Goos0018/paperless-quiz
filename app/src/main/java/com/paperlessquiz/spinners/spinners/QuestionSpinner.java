package com.paperlessquiz.spinners.spinners;

import android.widget.EditText;
import android.widget.TextView;

import com.paperlessquiz.quiz.Quiz;

public class QuestionSpinner {
    private Quiz quiz;
    private int questionNr;
    private int oldQuestionNr;
    private int roundNr;
    private int teamNr;
    private TextView tvMain, tvSub;
    private EditText answerField;


    public QuestionSpinner(Quiz quiz, TextView tvMain, TextView tvSub, EditText editAnswerField, int roundNr, int teamNr) {
        this.quiz = quiz;
        this.tvMain = tvMain;
        this.tvSub = tvSub;
        this.questionNr = 1;
        this.roundNr = roundNr;
        this.teamNr=teamNr;
        this.oldQuestionNr = quiz.getRound(roundNr).getQuestions().size();
        this.answerField = editAnswerField;
    }

    public void refreshDisplay(){
        tvMain.setText(quiz.getQuestion(roundNr,questionNr).getName());
        tvSub.setText(quiz.getQuestion(roundNr,questionNr).getHint());
    }

    public void positionChanged() {
        //Save the answer that was given into Quiz
        quiz.setAnswerForTeam(roundNr, oldQuestionNr, teamNr,answerField.getText().toString().trim());
        //Set the value of the answer for the new question to what we have in the array
        answerField.setText(quiz.getAnswerForTeam(roundNr, questionNr,teamNr).getTheAnswer());
        //Show that the question has changed
        refreshDisplay();
    }

    public void initialize(int pos) {
        //This is called when a round changes
        //We don't want to save the answer that is currently in answerField in that case, because it belongs to a previous round
        //So just set the value of the answer for the new question to what we have in the array and refresh
        answerField.setText(quiz.getAnswerForTeam(roundNr, questionNr,teamNr).getTheAnswer());
        questionNr = pos;
        refreshDisplay();
    }

    public void moveTo(int newQuestionNr) {
        oldQuestionNr = questionNr;
        if (newQuestionNr <= quiz.getRound(roundNr).getQuestions().size()) {
            questionNr = newQuestionNr;
        } else {
            questionNr = 1;
        }
        positionChanged();
    }

    public void moveUp() {
        oldQuestionNr = questionNr;
        if (questionNr == quiz.getRound(roundNr).getQuestions().size()) {
            questionNr = 1;
        } else {
            questionNr++;
        }
        positionChanged();
    }

    public void moveDown() {
        oldQuestionNr = questionNr;
        if (questionNr == 1) {
            questionNr = quiz.getRound(roundNr).getQuestions().size();
        } else {
            questionNr--;
        }
        positionChanged();
    }

    public void setRoundNr(int roundNr) {
        this.roundNr = roundNr;
    }

    public int getQuestionNr() {
        return questionNr;
    }
}