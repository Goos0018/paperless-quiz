package com.example.paperlessquiz.quizextradata;

import java.io.Serializable;

public class QuizExtraData implements Serializable {
    public static final String INTENT_EXTRA_NAME_THIS_QUIZ_EXTRAS = "thisQuizExtras";
    private boolean open;
    private int nrOfRounds;
    private int nrOfParticipants;
    private int debugLevel;

    public QuizExtraData(boolean open, int nrOfRounds, int nrOfParticipants, int debugLevel) {
        this.open = open;
        this.nrOfRounds = nrOfRounds;
        this.nrOfParticipants = nrOfParticipants;
        this.debugLevel=debugLevel;
    }

    public QuizExtraData(){
        this.open = false;
        this.nrOfRounds = 0;
        this.nrOfParticipants = 0;
        this.debugLevel = 0;
    }


    public QuizExtraData(QuizExtraData quizExtraData) {
        this.open = quizExtraData.open;
        this.nrOfRounds = quizExtraData.nrOfRounds;
        this.nrOfParticipants = quizExtraData.nrOfParticipants;
        this.debugLevel=quizExtraData.getDebugLevel();
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

    public int getDebugLevel() {
        return debugLevel;
    }

    public void setNrOfRounds(int nrOfRounds) {
        this.nrOfRounds = nrOfRounds;
    }

    public void setNrOfParticipants(int nrOfParticipants) {
        this.nrOfParticipants = nrOfParticipants;
    }
}
