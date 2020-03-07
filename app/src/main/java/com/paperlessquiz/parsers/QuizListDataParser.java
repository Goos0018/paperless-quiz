package com.paperlessquiz.parsers;

import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.quiz.QuizListData;

import org.json.JSONException;
import org.json.JSONObject;

public class QuizListDataParser implements JsonParser<QuizListData> {

    @Override
    public QuizListData parse(JSONObject jo) throws JSONException {
        int idQuiz, hideTopRows;
        String name, description, logoURL;
        QuizListData quizListData;
        try {
            idQuiz = jo.getInt(QuizDatabase.COLNAME_ID_QUIZ);
            name = jo.getString(QuizDatabase.COLNAME_QUIZ_NAME);
            description = jo.getString(QuizDatabase.COLNAME_QUIZ_DESCRIPTION);
            logoURL = jo.getString(QuizDatabase.COLNAME_QUIZ_LOGO_URL);
            hideTopRows = jo.getInt(QuizDatabase.COLNAME_QUIZ_HIDETOPROWS);
        } catch (Exception e) {
            idQuiz = 0;
            name = "Error parsing " + jo.toString();;
            description = "";
            logoURL = "";
            hideTopRows = 0;
        }
        quizListData = new QuizListData(idQuiz, hideTopRows, name, description, logoURL);
        return quizListData;
    }
}
