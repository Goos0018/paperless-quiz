package com.example.paperlessquiz.answerslist;

import java.util.ArrayList;

public class AnswersList {
    private String questionID;
    private int roundNr;
    private int questionNr;
    private ArrayList<String> allAnswers;

    public AnswersList(String questionID, int roundNr, int questionNr) {
        this.questionID = questionID;
        this.roundNr = roundNr;
        this.questionNr = questionNr;
        this.allAnswers = new ArrayList<>();
    }

    public ArrayList<String> getAllAnswers() {
        return allAnswers;
    }

    public void setAllAnswers(ArrayList<String> allAnswers) {
        this.allAnswers = allAnswers;
    }

    public int getRoundNr() {
        return roundNr;
    }

    public int getQuestionNr() {
        return questionNr;
    }
}
