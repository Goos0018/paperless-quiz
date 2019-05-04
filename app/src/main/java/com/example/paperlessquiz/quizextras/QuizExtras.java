package com.example.paperlessquiz.quizextras;

import java.io.Serializable;

public class QuizExtras implements Serializable {
    private boolean open;
    private int nrOfRounds;
    private int nrOfParticipants;

    public QuizExtras(boolean open, int nrOfRounds, int nrOfParticipants) {
        this.open = open;
        this.nrOfRounds = nrOfRounds;
        this.nrOfParticipants = nrOfParticipants;
    }

    public QuizExtras(){

    }


    public QuizExtras(QuizExtras quizExtras) {
        this.open = quizExtras.open;
        this.nrOfRounds = quizExtras.nrOfRounds;
        this.nrOfParticipants = quizExtras.nrOfParticipants;
    }



    public boolean isOpen() {
        return open;
    }

    public int getNrOfRounds() {
        return nrOfRounds;
    }

    public int getNrOfParticipants() {
        return nrOfParticipants;
    }
}