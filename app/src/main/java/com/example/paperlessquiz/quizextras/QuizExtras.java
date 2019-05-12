package com.example.paperlessquiz.quizextras;

import java.io.Serializable;

public class QuizExtras implements Serializable {
    public static final String INTENT_EXTRA_NAME_THIS_QUIZ_EXTRAS = "thisQuizExtras";
    private boolean open;
    private int nrOfRounds;
    private int nrOfParticipants;

    public QuizExtras(boolean open, int nrOfRounds, int nrOfParticipants) {
        this.open = open;
        this.nrOfRounds = nrOfRounds;
        this.nrOfParticipants = nrOfParticipants;
    }

    public QuizExtras(){
        this.open = false;
        this.nrOfRounds = 0;
        this.nrOfParticipants = 0;
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
