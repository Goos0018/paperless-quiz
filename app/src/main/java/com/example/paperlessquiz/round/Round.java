package com.example.paperlessquiz.round;

import com.example.paperlessquiz.question.Question;
import com.example.paperlessquiz.spinners.SpinnerData;

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

//TODO: Write ToString functions for each class to upload item of the class to QuizSheet
public class Round implements Serializable, SpinnerData {
    private int roundNr;
    private String id, name, description;
    private int nrOfQuestions;
    private boolean acceptsAnswers, acceptsCorrections, corrected;
    private ArrayList<Question> questions;

    public Round() {
        this.roundNr = 0;
        this.name = "";
        this.description = "";
        this.nrOfQuestions = 0;
        this.acceptsAnswers = false;
        this.acceptsCorrections = false;
        this.corrected = false;
        this.questions = new ArrayList<Question>();
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

    public void UpdateRoundBasics(Round rnd) {
        this.roundNr = rnd.getRoundNr();
        this.name = rnd.getName();
        this.description = rnd.getDescription();
        this.nrOfQuestions = rnd.getNrOfQuestions();
        this.acceptsAnswers = rnd.getAcceptsAnswers();
        this.acceptsCorrections = rnd.getAcceptsCorrections();
        this.corrected = rnd.isCorrected();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getNrOfQuestions() {
        return nrOfQuestions;
    }


    public boolean getAcceptsAnswers() {
        return acceptsAnswers;
    }

    public boolean getAcceptsCorrections() {
        return acceptsCorrections;
    }

    public boolean isCorrected() {
        return corrected;
    }

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

    public void setAcceptsAnswers(boolean acceptsAnswers) {
        this.acceptsAnswers = acceptsAnswers;
    }

    public void setAcceptsCorrections(boolean acceptsCorrections) {
        this.acceptsCorrections = acceptsCorrections;
    }

    public void setCorrected(boolean corrected) {
        this.corrected = corrected;
    }

    @Override
    public String toString() {
       String tmp = "[\"" + name + "\",\"" + description + "\",\"" + nrOfQuestions + "\",\"" + Boolean.toString(acceptsAnswers) +
               "\",\"" + Boolean.toString(acceptsCorrections) + "\",\"" + Boolean.toString(corrected) + "\"]";
        return tmp;
    }
}
