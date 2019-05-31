package com.example.paperlessquiz.question;

import com.example.paperlessquiz.answer.Answer;
import com.example.paperlessquiz.spinners.SpinnerData;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * QuestionID identifies the question uniquely
 * ThisAnswer is the answer given by the team that is logged inin the app (if any)
 * All answers contains all answers (for each team) for this question
 */
public class Question implements Serializable, SpinnerData {
    private String questionID;
    private int questionNr;
    private int roundNr;
    private String name;
    private String hint;
    private String question;
    private String correctAnswer;
    private Answer thisAnswer;
    private ArrayList<Answer> allAnswers;
    private int maxScore;

    public Question() {
        this.questionID = "";
        this.questionNr = 0;
        this.roundNr = 0;
        this.name="";
        this.hint = "";
        this.question = "";
        this.correctAnswer = "";
        this.maxScore = 0;
        this.thisAnswer = new Answer("");
        this.allAnswers=new ArrayList<>();
    }

    public Question(String questionID, int questionNr, int roundNr, String name, String hint, String question, String correctAnswer, int maxScore) {
        this.questionID=questionID;
        this.questionNr = questionNr;
        this.roundNr = roundNr;
        this.name=name;
        this.hint = hint;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.maxScore = maxScore;
        this.thisAnswer =  new Answer("");
        this.allAnswers=new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuestionNr() {
        return questionNr;
    }

    public void setQuestionNr(int questionNr) {
        this.questionNr = questionNr;
    }

    public int getRoundNr() {
        return roundNr;
    }

    public void setRoundNr(int roundNr) {
        this.roundNr = roundNr;
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

    public Answer getThisAnswer() {
        return thisAnswer;
    }

    public void setThisAnswer(Answer thisAnswer) {
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

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public ArrayList<Answer> getAllAnswers() {
        return allAnswers;
    }

    public void setAllAnswers(ArrayList<Answer> allAnswers) {
        this.allAnswers = allAnswers;
    }
}
