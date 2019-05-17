package com.example.paperlessquiz.answer;

import java.io.Serializable;

public class Answer implements Serializable{
    private int questionNr;
    private String answer;
    //private String teamID;
    private boolean correct=false;

    public Answer(int questionNr){
        this.questionNr=questionNr;
        this.answer = "";

    }


    public Answer(int questionNr, String answer) {
        this.questionNr = questionNr;
        this.answer = answer;
        //this.teamID = teamID;
    }

    public int getQuestionNr() {
        return questionNr;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
