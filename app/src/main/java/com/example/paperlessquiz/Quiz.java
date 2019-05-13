package com.example.paperlessquiz;

import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.question.QuestionsList;
import com.example.paperlessquiz.quizextradata.QuizExtraData;
import com.example.paperlessquiz.quizlistdata.QuizListData;
import com.example.paperlessquiz.round.Round;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Quiz implements Serializable {
    public static final String INTENT_PUTEXTRANAME_THIS_QUIZ = "ThisQuiz";
    private QuizListData listData;
    private QuizExtraData additionalData;
    private HashMap<String, LoginEntity> teams;
    private HashMap<String, LoginEntity> organizers;
    private ArrayList<Round> rounds;
    private ArrayList<QuestionsList> questions;

    public Quiz() {
        this.listData = new QuizListData();
        this.additionalData = new QuizExtraData();
        this.teams = new HashMap<String, LoginEntity>();
        this.organizers = new HashMap<String,LoginEntity>();
        this.rounds = new ArrayList<Round>();
        for (int i=0;i<this.additionalData.getNrOfRounds();i++)
        {
            rounds.add(i,new Round());
        }
    }

    public QuizListData getListData() {
        return listData;
    }

    public void setListData(QuizListData listData) {
        this.listData = listData;
    }

    public QuizExtraData getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(QuizExtraData additionalData) {
        this.additionalData = additionalData;
    }

    public HashMap<String, LoginEntity> getTeams() {
        return teams;
    }

    public void setTeams(HashMap<String, LoginEntity> teams) {
        this.teams = teams;
    }

    public HashMap<String, LoginEntity> getOrganizers() {
        return organizers;
    }

    public void setOrganizers(HashMap<String, LoginEntity> organizers) {
        this.organizers = organizers;
    }

    public ArrayList<Round> getRounds() {
        return rounds;
    }
    public Round getRound(int i) {
        return rounds.get(i);
    }

    public void setRounds(ArrayList<Round> rounds) {
        this.rounds = rounds;
    }

    public ArrayList<QuestionsList> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<QuestionsList> questions) {
        this.questions = questions;
    }
}
