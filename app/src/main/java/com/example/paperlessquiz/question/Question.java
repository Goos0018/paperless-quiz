package com.example.paperlessquiz.question;

public class Question {
    private String id;
    private String roundID;
    private String hint;
    private String question;
    private String correctAnswer;
    private String thisAnswer;
    private int maxScore;

    public Question(String id, String roundID, String hint, String question, String correctAnswer, int maxScore) {
        this.id = id;
        this.roundID=roundID;
        this.hint = hint;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.maxScore = maxScore;
        this.thisAnswer="";
    }

    public String getId() {
        return id;
    }

    public String getRoundID() {
        return roundID;
    }

    public String getHint() {
        return hint;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public String getThisAnswer() {
        return thisAnswer;
    }

    public void setThisAnswer(String thisAnswer) {
        this.thisAnswer = thisAnswer;
    }
}
