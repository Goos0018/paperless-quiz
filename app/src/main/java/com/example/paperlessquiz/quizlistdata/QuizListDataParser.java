package com.example.paperlessquiz.quizlistdata;

import com.example.paperlessquiz.google.access.JsonParser;
import com.example.paperlessquiz.quiz.QuizGenerator;

import org.json.JSONException;
import org.json.JSONObject;
//TODO : extract strings to constants
public class QuizListDataParser implements JsonParser<QuizListData> {


    public QuizListData parse(JSONObject jo) throws JSONException {
        return new QuizListData(jo.getString(QuizGenerator.QUIZ_NAME),
                jo.getString(QuizGenerator.QUIZ_DESCRIPTION), jo.getString(QuizGenerator.QUIZ_SHEET_DOC_ID),jo.getString(QuizGenerator.QUIZ_LOGO_URL),
                jo.getInt(QuizGenerator.QUIZ_DEBUGLEVEL),jo.getBoolean(QuizGenerator.QUIZ_KEEPLOGS),jo.getInt(QuizGenerator.QUIZ_APPDEBUGLEVEL));
    }
}
