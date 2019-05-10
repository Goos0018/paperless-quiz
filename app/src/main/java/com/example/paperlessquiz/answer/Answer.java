package com.example.paperlessquiz.answer;

import java.io.Serializable;

public class Answer implements Serializable {
    private int questionNr;
    private String answer;
    private String teamID;

    public Answer(int questionNr){
        this.questionNr=questionNr;
        this.answer = "Nothing was entered";
        this.teamID = "";
    }


    public Answer(int questionNr, String answer, String teamID) {
        this.questionNr = questionNr;
        this.answer = answer;
        this.teamID = teamID;
    }

    public int getQuestionNr() {
        return questionNr;
    }

    public String getAnswer() {
        return answer;
    }

    public String getTeamID() {
        return teamID;
    }
}
