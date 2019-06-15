package com.example.paperlessquiz.scores;

import java.util.ArrayList;

public class Score {
    private ArrayList<Integer> scorePerRoundForTeam;
    private int totalScore;
    private int teamId;
    private String teamName;

    public Score(int teamId,String teamName) {
        this.teamId=teamId;
        this.teamName=teamName;
        this.scorePerRoundForTeam =new ArrayList<>();
        this.totalScore=0;
    }

    public void setScorePerRoundForTeam(ArrayList<Integer> scorePerRoundForTeam) {
        this.scorePerRoundForTeam = scorePerRoundForTeam;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public ArrayList<Integer> getScorePerRoundForTeam() {
        return scorePerRoundForTeam;
    }

    public int getTotalScore() {
        return totalScore;
    }
}
