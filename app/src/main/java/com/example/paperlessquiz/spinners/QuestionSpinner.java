package com.example.paperlessquiz.spinners;

import android.widget.EditText;
import android.widget.TextView;

import com.example.paperlessquiz.answer.Answer;
import com.example.paperlessquiz.question.Question;

import java.util.ArrayList;

public class QuestionSpinner {
    private ArrayList<Question> questionsList;
    private int position;
    private int oldPosition;
    private TextView tvMain;
    private TextView tvSub;
    private ArrayList<Answer> roundAnswers;
    private EditText answerField;

    public QuestionSpinner(ArrayList<Question> questionsList, TextView tvMain, TextView tvSub, ArrayList<Answer> roundAnswers,EditText answerField) {
        this.questionsList = questionsList;
        this.tvMain = tvMain;
        this.tvSub = tvSub;
        this.position =0;
        this.oldPosition = questionsList.size()-1;
        this.roundAnswers = roundAnswers;
        this.answerField = answerField;
    }

    public void positionChanged() {
        //Show that the question has changed
        tvMain.setText(questionsList.get(position).getName());
        tvSub.setText(questionsList.get(position).getDescription());
        //Save the answer that was given in the correct position of the roundAnswers array
        roundAnswers.get(oldPosition).setAnswer(answerField.getText().toString().trim());
        //Set the value of the answer for the new question to what we have in the array
        answerField.setText(roundAnswers.get(position).getAnswer());
    }

    public void setRoundAnswers(ArrayList<Answer> roundAnswers) {
        this.roundAnswers = roundAnswers;
    }

    public void moveUp(){
        oldPosition=position;
        if(position == questionsList.size()){
            position =0;
        }
        else {
            position++;
        }
    }
    public void moveDown(){
        oldPosition=position;
        if(position == 0){
            position =questionsList.size();
        }
        else {
            position--;
        }
    }

    public void setArrayList(ArrayList<Question> questionsList) {
        this.questionsList = questionsList;
    }

    public ArrayList<Question> getQuestionsList() {
        return questionsList;
    }

    public ArrayList<Answer> getRoundAnswers() {
        return roundAnswers;
    }
}