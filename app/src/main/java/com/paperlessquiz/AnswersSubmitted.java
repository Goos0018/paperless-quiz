package com.paperlessquiz;

public class AnswersSubmitted {
    int idRound,idTeam;
    boolean answersSubmitted;

    public AnswersSubmitted(int idRound, int idTeam, boolean answersSubmitted) {
        this.idRound = idRound;
        this.idTeam = idTeam;
        this.answersSubmitted = answersSubmitted;
    }

    public int getIdRound() {
        return idRound;
    }

    public int getIdTeam() {
        return idTeam;
    }

    public boolean isAnswersSubmitted() {
        return answersSubmitted;
    }
}
