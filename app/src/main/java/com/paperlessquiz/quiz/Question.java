package com.paperlessquiz.quiz;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * QuestionID identifies the question uniquely and is used to locate questions SQL database
 * RoundNr and QuestionNr are used to locate it in the ArrayLists we have in the Quiz object
 * All answers contains all answers (for each team) for this question. This ArrayList is initialized with the data from the GetAnswersListLPL
 * The rest comes from the GetQuestionsLPL
 * We foresee methods here to directly get and set answers for a specific team
 */
public class Question implements Serializable {
    int idRound,idQuestion,questionNr,questionType;
    private String name;
    private String hint;
    private String question;
    private String correctAnswer;
    private int maxScore;

    private int roundNr;
    private ArrayList<Answer> allAnswers;
    private boolean allAnswersCorrected;
    //private String questionID;

    //20190728 - used by the parser
    public Question(int roundNr, int idQuestion, int questionNr, int questionType, String name, String hint, String question, String correctAnswer, int maxScore) {
        this.roundNr = roundNr;
        this.idQuestion = idQuestion;
        this.questionNr = questionNr;
        this.questionType = questionType;
        this.name = name;
        this.hint = hint;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.maxScore = maxScore;
        this.allAnswers=new ArrayList<>();
    }

    //20190728 - Create a dummy question with the given question Nr - used to make sure the Questions Array always has sufficient elements
    public Question(int questionNr) {
        //this.questionID = "";
        this.idQuestion = 0;
        this.questionNr = questionNr;
        this.questionType = 0;
        this.roundNr = 0;
        this.name="EMPTY QUESTION";
        this.hint = "empty";
        this.question = "";
        this.correctAnswer = "";
        this.maxScore = 0;
       this.allAnswers=new ArrayList<>();
    }

    public Question(String questionID, int questionNr, int roundNr, String name, String hint, String question, String correctAnswer, int maxScore) {
        //This constructor is used when parsing the results from the sheet
        //We only use the data we can get from this sheet
        //this.questionID=questionID;
        this.questionNr = questionNr;
        this.roundNr = roundNr;
        this.name=name;
        this.hint = hint;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.maxScore = maxScore;
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
    /*
        public void setHint(String hint) {
        this.hint = hint;
    }


    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }
    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }


    public boolean isAllAnswersCorrected() {
        return allAnswersCorrected;
    }

    public void setAllAnswersCorrected(boolean allAnswersCorrected) {
        this.allAnswersCorrected = allAnswersCorrected;
    }
     public void updateAllAnswersCorrected(){
        for (int i = 0; i < allAnswers.size(); i++) {
            allAnswersCorrected=allAnswersCorrected && allAnswers.get(i).isCorrect();
        }
    }
*/

    public int getMaxScore() {
        return maxScore;
    }



    public String getHint() {
        return hint;
    }

    public String getName() {
        return name;
    }

    /*
    public String getQuestionID() {
        return questionID;
    }
*/


    public ArrayList<Answer> getAllAnswers() {
       return allAnswers;
    }

    public void setAllAnswers(ArrayList<Answer> allAnswers) {
        this.allAnswers = allAnswers;
   }

    //Given a list of answers (loaded from the Google sheet, update only the theAnswer String without changing the isCorrect and isCorrected boolean
    public void updateAllAnswers(ArrayList<Answer> allAnswers) {
        for (int i = 0; i < allAnswers.size(); i++) {
            this.allAnswers.get(i).setTheAnswer(allAnswers.get(i).getTheAnswer());
        }
    }


    public Answer getAnswerForTeam(int teamNr){
        return allAnswers.get(teamNr-1);
    }
    public void setAnswerForTeam(int teamNr, String answer){
        getAnswerForTeam(teamNr).setTheAnswer(answer);
    }

    public int getIdRound() {
        return idRound;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public int getQuestionType() {
        return questionType;
    }



}
