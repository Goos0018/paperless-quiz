package com.paperlessquiz.quiz;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Round statuses (acceptsAnswers/getAcceptsCorrections/Corrected can be one of the following:
 * false, false, false: round has not been opened yet (initial status)
 * true, false, false: answers can be submitted
 * false,true,false: answers are assumed to be available an dcan be corrected
 * false, false,true: round is assumed to be corrected (final status)
 * The questions Array is  initialized by the GetQuestionsLPL, the rest comes from the GetRoundsLPL
 */

public class Round implements Serializable {
    private int idQuiz,idRound;
    private int roundNr, roundStatus;
    private String name, description;
    //private int nrOfQuestions;
    //private boolean acceptsAnswers, acceptsCorrections, corrected;
    private boolean corrected;
    private ArrayList<Question> questions;


    //20190728 - used by the rounds parser
    public Round(int idQuiz, int idRound, int roundNr, int roundStatus, String name, String description) {
        this.idQuiz = idQuiz;
        this.idRound = idRound;
        this.roundNr = roundNr;
        this.roundStatus = roundStatus;
        this.name = name;
        this.description = description;
        this.questions=new ArrayList<>();
    }

    //20190728 - Create a dummy round with the given round Nr - used to make sure the Rounds Array always has sufficient elements
    public Round(int roundNr) {
        this.roundNr = roundNr;
        this.name = "EMPTY ROUND";
        this.description = "empty round - please check the input that was submitted";
        //this.nrOfQuestions = 0;
        /*
        this.acceptsAnswers = false;
        this.acceptsCorrections = false;
        this.corrected = false;
        */
        this.questions = new ArrayList<Question>();
    }

    //20190728 - Used to update a rounds object from info retrieved from the database
    public void updateRoundBasics(Round rnd) {
        //this.roundNr = rnd.getRoundNr();
        this.name = rnd.getName();
        this.description = rnd.getDescription();
        this.roundStatus = rnd.getRoundStatus();
        //this.nrOfQuestions = rnd.getNrOfQuestions();
        /*
        this.acceptsAnswers = rnd.getAcceptsAnswers();
        this.acceptsCorrections = rnd.getAcceptsCorrections();
        this.corrected = rnd.isCorrected();
        */
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getIdRound() {
        return idRound;
    }

    /*
    public int getNrOfQuestions() {
        return nrOfQuestions;
    }

        public void setNrOfQuestions(int nrOfQuestions) {
        this.nrOfQuestions = nrOfQuestions;
    }
    */

/*
    public boolean getAcceptsAnswers() {
        return acceptsAnswers;
    }

    public boolean getAcceptsCorrections() {
        return acceptsCorrections;
    }



     public void setAcceptsAnswers(boolean acceptsAnswers) {
        this.acceptsAnswers = acceptsAnswers;
    }

    public void setAcceptsCorrections(boolean acceptsCorrections) {
        this.acceptsCorrections = acceptsCorrections;
    }

    @Override
    public String toString() {
       String tmp = "[\"" + name + "\",\"" + description + "\",\"" + nrOfQuestions + "\",\"" + Boolean.toString(acceptsAnswers) +
               "\",\"" + Boolean.toString(acceptsCorrections) + "\",\"" + Boolean.toString(corrected) + "\"]";
        return tmp;
    }

public Round(int id, String name, String description, int nrOfQuestions,
                 boolean acceptsAnswers, boolean acceptsCorrections, boolean corrected) {
        this.roundNr = id;
        this.name = name;
        this.description = description;
        this.nrOfQuestions = nrOfQuestions;

        this.acceptsAnswers = acceptsAnswers;
        this.acceptsCorrections = acceptsCorrections;
        this.corrected = corrected;

}

*/
    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public Question getQuestion(int questionNr) {
        return questions.get(questionNr-1);
    }
    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public int getRoundNr() {
        return roundNr;
    }



    public int getMaxScore(){
        int res=0;
        for (int i = 0; i < questions.size(); i++) {
            res = res + questions.get(i).getMaxScore();
        }
        return res;
    }

    public int getRoundStatus() {
        return roundStatus;
    }

    public void setRoundStatus(int roundStatus) {
        this.roundStatus = roundStatus;
    }

    public boolean isCorrected() {

        return corrected;
    }

    public void setCorrected(boolean corrected) {
        this.corrected = corrected;
    }
}
