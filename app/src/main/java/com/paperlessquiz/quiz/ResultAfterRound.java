package com.paperlessquiz.quiz;

public class ResultAfterRound {
    private int teamNr, roundNr;
    private int scoreForThisRound;
    private int posInStandingForThisRound;
    private int totalScoreAfterThisRound;
    private int posInStandingAfterThisRound;

    public ResultAfterRound(int teamNr, int roundNr, int scoreForThisRound, int posInStandingForThisRound, int totalScoreAfterThisRound, int posInStandingAfterThisRound) {
        this.teamNr = teamNr;
        this.roundNr = roundNr;
        this.scoreForThisRound = scoreForThisRound;
        this.posInStandingForThisRound = posInStandingForThisRound;
        this.totalScoreAfterThisRound = totalScoreAfterThisRound;
        this.posInStandingAfterThisRound = posInStandingAfterThisRound;
    }

    public ResultAfterRound() {
        scoreForThisRound=0;
        posInStandingForThisRound = 1;
        totalScoreAfterThisRound = 0;
        posInStandingAfterThisRound=1;
    }

    public int getScoreForThisRound() {
        return scoreForThisRound;
    }

    public void setScoreForThisRound(int scoreForThisRound) {
        this.scoreForThisRound = scoreForThisRound;
    }

    public int getPosInStandingForThisRound() {
        return posInStandingForThisRound;
    }

    public void setPosInStandingForThisRound(int posInStandingForThisRound) {
        this.posInStandingForThisRound = posInStandingForThisRound;
    }

    public int getTotalScoreAfterThisRound() {
        return totalScoreAfterThisRound;
    }

    public void setTotalScoreAfterThisRound(int totalScoreAfterThisRound) {
        this.totalScoreAfterThisRound = totalScoreAfterThisRound;
    }

    public int getPosInStandingAfterThisRound() {
        return posInStandingAfterThisRound;
    }

    public void setPosInStandingAfterThisRound(int posInStandingAfterThisRound) {
        this.posInStandingAfterThisRound = posInStandingAfterThisRound;
    }

    public int getTeamNr() {
        return teamNr;
    }

    public int getRoundNr() {
        return roundNr;
    }
}
