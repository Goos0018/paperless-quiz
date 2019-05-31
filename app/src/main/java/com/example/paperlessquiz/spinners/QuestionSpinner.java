package com.example.paperlessquiz.spinners;

import android.widget.EditText;
import android.widget.TextView;

import com.example.paperlessquiz.answer.Answer;
import com.example.paperlessquiz.question.Question;
import com.example.paperlessquiz.quiz.Quiz;

import java.util.ArrayList;

public class QuestionSpinner {
    private Quiz quiz;
    //private ArrayList<ArrayList<Question>> questionsList;
    private int position;
    private int oldPosition;
    private int roundNr;
    private int teamNr;
    private TextView tvMain, tvSub, tvDisplayAnswers;
    //private ArrayList<ArrayList<Answer>> myAnswers;
    private EditText answerField;
    //private DisplayAnswersAdapter answersAdapter;

    public QuestionSpinner(Quiz quiz, TextView tvMain, TextView tvSub,TextView tvDisplayAnswers, EditText answerField, int roundNr,int teamNr) {
        //ArrayList<ArrayList<Answer>> myAnswers, DisplayAnswersAdapter answersAdapter,EditText answerField, int roundNr) {
        this.quiz = quiz;
        this.tvMain = tvMain;
        this.tvSub = tvSub;
        this.tvDisplayAnswers = tvDisplayAnswers;
        this.position = 0;
        this.roundNr = roundNr;
        this.teamNr=teamNr;
        this.oldPosition = quiz.getRound(roundNr).getQuestions().size() - 1;
        this.answerField = answerField;
    }
/*
    public String displayAnswers() {
        String tmp = "";
        for (int i = 0; i < questionsList.get(roundNr).size(); i++) {
            tmp = tmp + (i+1) + ". " + myAnswers.get(roundNr).get(i).getTheAnswer() + "\n";
        }
        return tmp;

    }
    */
    public void refreshDisplay(){
        tvMain.setText(quiz.getRound(roundNr).getQuestions().get(position).getName());
        tvSub.setText(quiz.getRound(roundNr).getQuestions().get(position).getDescription());
    }

    public void positionChanged() {
        //Save the answer that was given into Quiz
        setAnswer(roundNr, oldPosition, teamNr,answerField.getText().toString().trim());
        //Set the value of the answer for the new question to what we have in the array
        answerField.setText(getAnswer(roundNr, position,teamNr));
        //Show that the question has changed
        refreshDisplay();
    }

    public void initialize(int pos) {
        //This is called when a round changes
        //We don't want to save the answer that is currently in answerField in that case, because it belongs to a previous round
        //So just set the value of the answer for the new question to what we have in the array and refresh
        answerField.setText(getAnswer(roundNr, pos,teamNr));
        position = pos;
        refreshDisplay();
    }

    public void moveTo(int newPosition) {
        oldPosition = position;
        if (newPosition < quiz.getRound(roundNr).getQuestions().size()) {
            position = newPosition;
        } else {
            position = 1;
        }
        positionChanged();
    }

    public void moveUp() {
        oldPosition = position;
        if (position == quiz.getRound(roundNr).getQuestions().size() - 1) {
            position = 0;
        } else {
            position++;
        }
        positionChanged();
    }

    public void moveDown() {
        oldPosition = position;
        if (position == 0) {
            position = quiz.getRound(roundNr).getQuestions().size() - 1;
        } else {
            position--;
        }
        positionChanged();
    }
/*
    public void setArrayList(ArrayList<ArrayList<Question>> questionsList) {
        this.questionsList = questionsList;
    }

    public Question getQuestion(int rndId, int questionId) {
        return questionsList.get(rndId).get(questionId);
    }
*/
    public void setAnswer(int rndNr, int questionNr, int teamNr, String answer) {
        quiz.getQuestion(rndNr,questionNr).getAnswerForTeam(teamNr).setTheAnswer(answer);
    }

    public String getAnswer(int rndNr, int questionNr,int teamNr) {
        return quiz.getQuestion(rndNr,questionNr).getAnswerForTeam(teamNr).getTheAnswer();
    }

    public void setRoundNr(int roundNr) {
        this.roundNr = roundNr;
    }

    public void clearAnswer() {
        answerField.setText("");
    }

    public int getPosition() {
        return position;
    }
}