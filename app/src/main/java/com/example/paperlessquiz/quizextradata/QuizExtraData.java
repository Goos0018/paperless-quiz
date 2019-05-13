package com.example.paperlessquiz.quizextradata;

import java.io.Serializable;

public class QuizExtraData implements Serializable {
    public static final String INTENT_EXTRA_NAME_THIS_QUIZ_EXTRAS = "thisQuizExtras";
    private boolean open;
    private int nrOfRounds;
    private int nrOfParticipants;

    public QuizExtraData(boolean open, int nrOfRounds, int nrOfParticipants) {
        this.open = open;
        this.nrOfRounds = nrOfRounds;
        this.nrOfParticipants = nrOfParticipants;
    }

    public QuizExtraData(){
        this.open = false;
        this.nrOfRounds = 0;
        this.nrOfParticipants = 0;
    }


    public QuizExtraData(QuizExtraData quizExtraData) {
        this.open = quizExtraData.open;
        this.nrOfRounds = quizExtraData.nrOfRounds;
        this.nrOfParticipants = quizExtraData.nrOfParticipants;
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
