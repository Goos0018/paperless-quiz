package com.example.paperlessquiz.answer;

import java.io.Serializable;

public class Answer implements Serializable{

    private int questionNr;
    private String thisAnswer;

    //private String teamID;
    private boolean correct=false;

    public Answer(String myAnswer) {
        this.thisAnswer = myAnswer;
    }

    public Answer(int questionNr){
        this.questionNr=questionNr;
        this.thisAnswer = "";

    }


    public Answer(int questionNr, String myAnswer) {
        this.questionNr = questionNr;
        this.thisAnswer = myAnswer;
        //this.teamID = teamID;
    }

    public int getQuestionNr() {
        return questionNr;
    }

    public String getThisAnswer() {
        return thisAnswer;
    }

    public void setThisAnswer(String myAnswer) {
        this.thisAnswer = myAnswer;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}
