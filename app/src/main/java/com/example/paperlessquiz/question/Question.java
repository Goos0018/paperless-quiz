package com.example.paperlessquiz.question;

import com.example.paperlessquiz.spinners.SpinnerData;

import java.io.Serializable;

public class Question implements Serializable, SpinnerData {
    private int id;
    private int roundID;
    private String name;
    private String hint;
    private String question;
    private String correctAnswer;
    private String thisAnswer;
    private int maxScore;

    public Question() {
        this.id = 0;
        this.roundID = 0;
        this.name="";
        this.hint = "";
        this.question = "";
        this.correctAnswer = "";
        this.maxScore = 0;
        this.thisAnswer = "";
    }

    public Question(int id, int roundID, String name, String hint, String question, String correctAnswer, int maxScore) {
        this.id = id;
        this.roundID = roundID;
        this.name=name;
        this.hint = hint;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.maxScore = maxScore;
        this.thisAnswer = "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoundID() {
        return roundID;
    }

    public void setRoundID(int roundID) {
        this.roundID = roundID;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getThisAnswer() {
        return thisAnswer;
    }

    public void setThisAnswer(String thisAnswer) {
        this.thisAnswer = thisAnswer;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    @Override
    public String getDescription() {
        return hint;
    }

    @Override
    public String getName() {
        return name;
    }
}
