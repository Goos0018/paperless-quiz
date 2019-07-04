package com.paperlessquiz.answer;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * AnswersList is used to store for each single question, an arraylist of Answers (one for each team)
 * The AnswersList object also contains the questionID, roundNr and questionNr, so we can associate it with the correct question
 */
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
