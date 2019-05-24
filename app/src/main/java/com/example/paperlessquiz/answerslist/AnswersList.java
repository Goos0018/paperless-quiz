package com.example.paperlessquiz.answerslist;

import com.example.paperlessquiz.answer.Answer;

import java.io.Serializable;
import java.util.ArrayList;

public class AnswersList implements Serializable {
    private String questionID;
    private int roundNr;
    private int questionNr;
    private ArrayList<Answer> allAnswers;

    public AnswersList(String questionID, int roundNr, int questionNr) {
        this.questionID = questionID;
        this.roundNr = roundNr;
        this.questionNr = questionNr;
        this.allAnswers = new ArrayList<>();
    }

    public ArrayList<Answer> getAllAnswers() {
        return allAnswers;
    }

    public void setAllAnswers(ArrayList<Answer> allAnswers) {
        this.allAnswers = allAnswers;
    }

    public int getRoundNr() {
        return roundNr;
    }

    public int getQuestionNr() {
        return questionNr;
    }
}
