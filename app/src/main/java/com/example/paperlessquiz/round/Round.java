package com.example.paperlessquiz.round;

import com.example.paperlessquiz.question.Question;
import com.example.paperlessquiz.question.QuestionsList;
import com.example.paperlessquiz.spinners.SpinnerData;

import java.io.Serializable;
import java.util.ArrayList;

public class Round implements Serializable, SpinnerData {
    public static final String INTENT_EXTRA_NAME_THIS_ROUND = "thisRound";
    private int id;
    private String name, description;
    private int nrOfQuestions;
    private boolean acceptsAnswers, acceptsCorrections, corrected, closed;
    private QuestionsList questions;

    public Round() {
        this.id = 0;
        this.name = "";
        this.description = "";
        this.nrOfQuestions = 0;
        this.acceptsAnswers = false;
        this.acceptsCorrections = false;
        this.corrected = false;
        this.closed = false;
        this.questions = new QuestionsList();
    }

    public Round(int id, String name, String description, int nrOfQuestions,
                 boolean acceptsAnswers, boolean acceptsCorrections, boolean corrected, boolean closed) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.nrOfQuestions = nrOfQuestions;
        this.acceptsAnswers = acceptsAnswers;
        this.acceptsCorrections = acceptsCorrections;
        this.corrected = corrected;
        this.closed = closed;
        this.questions = new QuestionsList();
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

    public boolean isClosed() {
        return closed;
    }

    public QuestionsList getQuestions() {
        return questions;
    }

    public void setQuestions(QuestionsList questions) {
        this.questions = questions;
    }
}
