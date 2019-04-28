package com.example.paperlessquiz;

public class Round {
    private String roundID;
    private int nrOfQuestions;
    private Question[] questionList;

    public Round(String roundID, int nrOfQuestions) {
        this.roundID = roundID;
        this.nrOfQuestions = nrOfQuestions;
        this.questionList = new Question[nrOfQuestions];
    }
}
