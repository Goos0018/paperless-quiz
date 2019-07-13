package com.paperlessquiz.parsers;

import com.paperlessquiz.question.Question;
import com.paperlessquiz.quiz.QuizDatabase;

import org.json.JSONException;
import org.json.JSONObject;

public class QuestionParser implements JsonParser<Question> {

    @Override
    public Question parse(JSONObject jo) throws JSONException {
        int idRound, idQuestion,questionNr,questionScore,questionType;
        String questionName,questionHint,questionFull,questionAnswer;
        Question question;
        try {
            idRound = jo.getInt(QuizDatabase.COLNAME_ID_ROUND);
            idQuestion = jo.getInt(QuizDatabase.COLNAME_ID_QUESTION);
            questionNr = jo.getInt(QuizDatabase.COLNAME_QUESTION_NR);
            questionScore = jo.getInt(QuizDatabase.COLNAME_QUESTION_SCORE);
            questionType = jo.getInt(QuizDatabase.COLNAME_QUESTION_TYPE);
            questionName = jo.getString(QuizDatabase.COLNAME_QUESTION_NAME);
            questionHint = jo.getString(QuizDatabase.COLNAME_QUESTION_HINT);
            questionFull = jo.getString(QuizDatabase.COLNAME_QUESTION_FULL);
            questionAnswer = jo.getString(QuizDatabase.COLNAME_QUESTION_ANSWER);
        } catch (Exception e) {
            idRound = 0;
            idQuestion = 0;
            questionNr = 0;
            questionScore = 0;
            questionType = 0;
            questionName = "Error parsing " + jo.toString();
            questionHint = "";
            questionFull = "";
            questionAnswer = "";
            }
        question = new Question(idRound, idQuestion, questionNr, questionType, questionName, questionHint, questionFull, questionAnswer, questionScore);
        return question;
    }
}

