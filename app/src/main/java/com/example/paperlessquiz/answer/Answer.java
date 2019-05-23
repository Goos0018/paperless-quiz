package com.example.paperlessquiz.answer;

import java.io.Serializable;
import java.util.ArrayList;

public class Answer implements Serializable{

    private int questionNr;
    private String myAnswer;

    //private String teamID;
    private boolean correct=false;



    public Answer(int questionNr){
        this.questionNr=questionNr;
        this.myAnswer = "";

    }


    public Answer(int questionNr, String myAnswer) {
        this.questionNr = questionNr;
        this.myAnswer = myAnswer;
        //this.teamID = teamID;
    }

    public int getQuestionNr() {
        return questionNr;
    }

    public String getMyAnswer() {
        return myAnswer;
    }

    public void setMyAnswer(String myAnswer) {
        this.myAnswer = myAnswer;
    }


}
