package com.example.paperlessquiz;

import android.util.Log;

import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.question.QuestionsList;
import com.example.paperlessquiz.quizbasics.QuizBasics;
import com.example.paperlessquiz.quizextras.QuizExtras;
import com.example.paperlessquiz.round.Round;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Quiz implements Serializable {
    private QuizBasics listData;
    private QuizExtras additionalData;
    private HashMap<String, LoginEntity> teams;
    private HashMap<String, LoginEntity> organizers;
    private ArrayList<Round> rounds;
    private ArrayList<QuestionsList> questions;

    public Quiz() {
        this.listData = new QuizBasics();
        this.additionalData = new QuizExtras();
        this.teams = new HashMap<String, LoginEntity>();
        this.organizers = new HashMap<String,LoginEntity>();
        this.rounds = new ArrayList<Round>();

    }

    public QuizBasics getListData() {
        return listData;
    }

    public void setListData(QuizBasics listData) {
        this.listData = listData;
    }

    public QuizExtras getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(QuizExtras additionalData) {
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
