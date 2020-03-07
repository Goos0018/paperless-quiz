package com.paperlessquiz.quiz;

public class ResultAfterRound {

    /**
     * This class represents the result after a round for a team
     * It contains scores for this round, total score after this round and standing after this round as well as maximum scores for the round and the quiz until now
     * Updated 7/3: remove standing for the round + add max scores and removed unnecessary setters
     */

    private int teamNr, roundNr;
    private int scoreForThisRound, maxScoreForThisRound;
    private int totalScoreAfterThisRound, maxTotalScoreAfterThisRound;
    private int posInStandingAfterThisRound;

    public ResultAfterRound(int teamNr, int roundNr, int scoreForThisRound, int maxScoreForThisRound, int totalScoreAfterThisRound, int maxTotalScoreAfterThisRound, int posInStandingAfterThisRound) {
        this.teamNr = teamNr;
        this.roundNr = roundNr;
        this.scoreForThisRound = scoreForThisRound;
        this.maxScoreForThisRound = maxScoreForThisRound;
        this.totalScoreAfterThisRound = totalScoreAfterThisRound;
        this.maxTotalScoreAfterThisRound = maxTotalScoreAfterThisRound;
        this.posInStandingAfterThisRound = posInStandingAfterThisRound;
    }

    public ResultAfterRound() {
        scoreForThisRound=0;
        totalScoreAfterThisRound = 0;
        posInStandingAfterThisRound=1;
    }

    public int getScoreForThisRound() {
        return scoreForThisRound;
    }

    public int getTotalScoreAfterThisRound() {
        return totalScoreAfterThisRound;
    }

    public int getPosInStandingAfterThisRound() {
        return posInStandingAfterThisRound;
    }


    public int getTeamNr() {
        return teamNr;
    }

    public int getRoundNr() {
        return roundNr;
    }

    public int getMaxScoreForThisRound() {
        return maxScoreForThisRound;
    }

    public int getMaxTotalScoreAfterThisRound() {
        return maxTotalScoreAfterThisRound;
    }
}
