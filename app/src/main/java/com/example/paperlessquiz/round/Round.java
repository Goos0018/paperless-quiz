package com.example.paperlessquiz.round;

import com.example.paperlessquiz.question.Question;
import com.example.paperlessquiz.question.QuestionsList;
import com.example.paperlessquiz.spinners.SpinnerData;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Round statuses (acceptsAnswers/AcceptsCorrections/Corrected can be one of the following:
 * false, false, false: round has not been opened yet (initial status)
 * true, false, false: answers can be submitted
 * false,true,false: answers are assumed to be available an dcan be corrected
 * false, false,true: round is assumed to be corrected (final status)
 */
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
        this.questions = new QuestionsList();
        this.questions = new ArrayList<>();
        for (int i=0;i < nrOfQuestions;i++)
        {
            questions.add(i,new Question());
        }

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


    public boolean AcceptsAnswers() {
        return acceptsAnswers;
    }

    public boolean AcceptsCorrections() {
        return acceptsCorrections;
    }

    public boolean isCorrected() {
        return corrected;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public Question getQuestion(int i) {
        return questions.get(i);
    }
    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

}
