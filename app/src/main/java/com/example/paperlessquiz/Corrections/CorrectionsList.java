package com.example.paperlessquiz.Corrections;

import java.util.ArrayList;

/**
 * This class represents a list of results for questions. A result is a pair of booleans:
 * isCorrect: is the answer correct
 * isCorrected: has this answer been corrected by someone?
 */

public class CorrectionsList {



    private String questionID;
    private int roundNr;
    private int questionNr;
    private ArrayList<Correction> allCorrections;

    public CorrectionsList(String questionID, int roundNr, int questionNr) {
        this.questionID = questionID;
        this.roundNr = roundNr;
        this.questionNr = questionNr;
        allCorrections = new ArrayList<>();
    }

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public int getRoundNr() {
        return roundNr;
    }

    public void setRoundNr(int roundNr) {
        this.roundNr = roundNr;
    }

    public int getQuestionNr() {
        return questionNr;
    }

    public void setQuestionNr(int questionNr) {
        this.questionNr = questionNr;
    }

    public ArrayList<Correction> getAllCorrections() {
        return allCorrections;
    }

    public void setAllCorrections(ArrayList<Correction> allCorrections) {
        this.allCorrections = allCorrections;
    }
}
