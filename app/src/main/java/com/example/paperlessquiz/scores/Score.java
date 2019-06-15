package com.example.paperlessquiz.scores;

import java.util.ArrayList;

public class Score {
    private ArrayList<Integer> scorePerRoundForTeam;
    private int currentTotalScore;
    private int teamId;
    private String teamName;

    public Score(int teamId,String teamName) {
        this.teamId=teamId;
        this.teamName=teamName;
        this.scorePerRoundForTeam =new ArrayList<>();
        this.currentTotalScore =0;
    }

    public void setScorePerRoundForTeam(ArrayList<Integer> scorePerRoundForTeam) {
        this.scorePerRoundForTeam = scorePerRoundForTeam;
    }

    public void setCurrentTotalScore(int currentTotalScore) {
        this.currentTotalScore = currentTotalScore;
    }

    public void setScoreForRound(int rndNr,int score){
        scorePerRoundForTeam.set(rndNr-1,new Integer(score));
    }

    public ArrayList<Integer> getScorePerRoundForTeam() {
        return scorePerRoundForTeam;
    }

    public int getCurrentTotalScore() {
        return currentTotalScore;
    }
}
