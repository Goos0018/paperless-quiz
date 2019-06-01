package com.example.paperlessquiz.quiz;

import com.example.paperlessquiz.answer.Answer;
import com.example.paperlessquiz.answerslist.AnswersList;
import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.question.Question;
import com.example.paperlessquiz.quizextradata.QuizExtraData;
import com.example.paperlessquiz.quizlistdata.QuizListData;
import com.example.paperlessquiz.round.Round;

import java.io.Serializable;
import java.util.ArrayList;

public class Quiz implements Serializable {
    public static final String INTENT_EXTRANAME_THIS_QUIZ = "ThisQuiz";
    public static final int TARGET_WIDTH = 200;
    public static final int TARGET_HEIGHT = 200;
    private QuizListData listData;
    private QuizExtraData additionalData;
    private ArrayList<LoginEntity> teams;
    private ArrayList<LoginEntity> organizers;
    private ArrayList<Round> rounds;
    private LoginEntity myLoginentity;

    //We only need an empty constructor, the QuizLoader class will populate all fields of the quiz
    public Quiz() {
        this.listData = new QuizListData();
        this.additionalData = new QuizExtraData();
        this.teams = new ArrayList<>();
        this.organizers = new ArrayList<>();
        this.rounds = new ArrayList<>();
        for (int i = 0; i < this.additionalData.getNrOfRounds(); i++) {
            rounds.add(i, new Round());
        }
    }

    //Add the questions information we have from an array of array of questions.
    public void setAllQuestionsPerRound(ArrayList<ArrayList<Question>> allQuestionsPerRound){
        //For each entry in the allQuestionsPerRound array (=for each round)
        for (int i = 0; i < allQuestionsPerRound.size(); i++) {
            this.getRounds().get(i).setQuestions(allQuestionsPerRound.get(i));
        }
    }

    //Add the answers for each question from an array of array of AnswersLists
    public void setAllAnswersPerQuestion(ArrayList<ArrayList<AnswersList>> allAnswersPerRound){
        //For each entry in the allAnswersPerRound array (=for each round)
        for (int i = 0; i < allAnswersPerRound.size(); i++) {
            ArrayList<AnswersList> allAnswersForThisRound = allAnswersPerRound.get(i);
            //For each question in this round
            for (int j = 0; j < allAnswersForThisRound.size(); j++) {
                AnswersList allAnswersForThisQuestion = allAnswersForThisRound.get(j);
                this.getRounds().get(i).getQuestions().get(j).setAllAnswers(allAnswersForThisQuestion.getAllAnswers());
            }

        }
    }

    //Return the team/organizer with the given ID
    public LoginEntity getTeam(int teamNr) {
        return teams.get(teamNr-1);
    }

    //Return the team with the given ID
    public LoginEntity getOrganizer(int organizerNr) {
        return organizers.get(organizerNr-1);
    }

    public ArrayList<LoginEntity> getTeams() {
        return teams;
    }

    public ArrayList<LoginEntity> getOrganizers() {
        return organizers;
    }

    public void setTeams(ArrayList<LoginEntity> teams) {
        this.teams = teams;
    }

    public void setOrganizers(ArrayList<LoginEntity> organizers) {
        this.organizers = organizers;
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

    public ArrayList<Round> getRounds() {
        return rounds;
    }

    public Round getRound(int rndNr) {
        return rounds.get(rndNr-1);
    }

    public Question getQuestion(int rndNr,int questionNr) {
        return getRound(rndNr).getQuestion(questionNr);
    }

    public Answer getAnswerForTeam(int rndNr, int questionNr, int teamNr){
        return getQuestion(rndNr,questionNr).getAnswerForTeam(teamNr);
    }

    public void setAnswerForTeam(int rndNr, int questionNr, int teamNr, String answer){
        getQuestion(rndNr,questionNr).setAnswerForTeam(teamNr,answer);
    }



    public void setRounds(ArrayList<Round> rounds) {
        this.rounds = rounds;
    }

    public ArrayList<Answer> getAnswersForRound(int rndNr, int teamNr){
        ArrayList<Answer> answersList = new ArrayList<>();
        for (int i = 0; i < getRound(rndNr).getQuestions().size(); i++) {
            answersList.add(i, getAnswerForTeam(rndNr,i+1,teamNr)); //The
        }
        return answersList;

    }

    public ArrayList<Answer> getAllAnswersForQuestion(int rndNr, int questionNr){
        return getQuestion(rndNr,questionNr).getAllAnswers();
    }

    public LoginEntity getMyLoginentity() {
        return myLoginentity;
    }

    public void setMyLoginentity(LoginEntity myLoginentity) {
        this.myLoginentity = myLoginentity;
    }
    
}
