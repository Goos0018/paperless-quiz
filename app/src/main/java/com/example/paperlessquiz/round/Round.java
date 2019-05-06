package com.example.paperlessquiz.round;

import com.example.paperlessquiz.question.Question;

import java.io.Serializable;

public class Round implements Serializable {
    public static final String INTENT_EXTRA_NAME_THIS_ROUND = "thisRound";
    private String id, name, description;
    private int nrOfQuestions;
    private boolean acceptsAnswers,acceptsCorrections,corrected,closed;

    public Round(String id, String name, String description, int nrOfQuestions,
                 boolean acceptsAnswers, boolean acceptsCorrections, boolean corrected, boolean closed) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.nrOfQuestions = nrOfQuestions;
        this.acceptsAnswers = acceptsAnswers;
        this.acceptsCorrections = acceptsCorrections;
        this.corrected = corrected;
        this.closed = closed;
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
}
