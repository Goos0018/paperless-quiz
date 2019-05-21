package com.example.paperlessquiz.quiz;

import com.example.paperlessquiz.answer.Answer;
import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.question.Question;
import com.example.paperlessquiz.quizextradata.QuizExtraData;
import com.example.paperlessquiz.quizlistdata.QuizListData;
import com.example.paperlessquiz.round.Round;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Quiz implements Serializable {
    public static final String INTENT_EXTRANAME_THIS_QUIZ = "ThisQuiz";
    public static final int TARGET_WIDTH = 200;
    public static final int TARGET_HEIGHT = 200;
    private QuizListData listData;
    private QuizExtraData additionalData;
    private ArrayList<LoginEntity> teams;
    private ArrayList<LoginEntity> organizers;
    private ArrayList<Round> rounds;
    private ArrayList<ArrayList<Question>> allQuestionsPerRound;
    private ArrayList<ArrayList<Answer>> myAnswers;

    public Quiz() {
        this.listData = new QuizListData();
        this.additionalData = new QuizExtraData();
        this.teams = new ArrayList<>();
        this.organizers = new ArrayList<>();
        this.rounds = new ArrayList<>();
        //ArrayList<Answer> tmp = new ArrayList<>();
        this.myAnswers = new ArrayList<>();
        for (int i = 0; i < this.additionalData.getNrOfRounds(); i++) {
            rounds.add(i, new Round());
        }
    }


    public void setAnswersForRound(int rndId, ArrayList<Answer> answerList) {
        myAnswers.add(rndId, answerList);
        /*
        for(int i=0;i<answerList.size();i++)
        {
                ArrayList<Answer> tmp = myAnswers.get(rndId);
                Answer curAnswer = answerList.get(i);
                tmp.add(i,curAnswer);
        }
        */
    }

    public void initializeAnswers() {
        //for each round
        for (int i = 0; i < this.getAdditionalData().getNrOfRounds(); i++) {
            this.getMyAnswers().add(i, new ArrayList<>());
            ArrayList<Answer> answers = new ArrayList<>();
            //Create an array with the correct nr of answers
            for (int j = 0; j < this.getRound(i).getNrOfQuestions(); j++) {
                answers.add(j, new Answer(j, ""));
            }
            this.setAnswersForRound(i, answers);
        }
    }

    //Return the team/organizer with the given ID
    public LoginEntity getTeam(int id) {
        return teams.get(id);
    }

    //Return the team with the given ID
    public LoginEntity getOrganizer(int id) {
        return organizers.get(id);
    }

    /*
    private ArrayList<LoginEntity> getArrayList(HashMap<String, LoginEntity> entities) {
        //Convert HAshmap to Arraylist to pass it to the adapter
        ArrayList<LoginEntity> list = new ArrayList<LoginEntity>();
        int i = 0;
        for (LoginEntity entity : entities.values()) {
            list.add(i, entity);
            i++;
        }
        return list;
    }
    */

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

    public Round getRound(int i) {
        return rounds.get(i);
    }

    public void setRounds(ArrayList<Round> rounds) {
        this.rounds = rounds;
    }

    public ArrayList<ArrayList<Question>> getAllQuestionsPerRound() {
        return allQuestionsPerRound;
    }

    public void setAllQuestionsPerRound(ArrayList<ArrayList<Question>> allQuestionsPerRound) {
        this.allQuestionsPerRound = allQuestionsPerRound;
    }

    public Question getQuestion(int rndId, int questionId) {
        return allQuestionsPerRound.get(rndId).get(questionId);
    }

    public void setAnswer(int rndId, int questionId, String answer) {
        myAnswers.get(rndId).get(questionId).setAnswer(answer);
    }

    public ArrayList<ArrayList<Answer>> getMyAnswers() {
        return myAnswers;
    }

    public void setMyAnswers(ArrayList<ArrayList<Answer>> myAnswers) {
        this.myAnswers = myAnswers;
    }

}
