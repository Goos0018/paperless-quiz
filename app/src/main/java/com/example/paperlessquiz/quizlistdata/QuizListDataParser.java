package com.example.paperlessquiz.quizlistdata;

import com.example.paperlessquiz.google.access.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;
//TODO : extract strings to constants
public class QuizListDataParser implements JsonParser<QuizListData> {

    //Strings here MUST match the headers in the QuizList tab of the Quizlist sheet
    public static final String QUIZ_ID = "QuizID";
    public static final String QUIZ_NAME = "QuizName";
    public static final String QUIZ_DESCRIPTION = "QuizDescription";
    public static final String QUIZ_SHEET_DOC_ID = "QuizSheetDocID";
    public static final String QUIZ_LOGO_URL = "QuizLogoURL";


    public QuizListData parse(JSONObject jo) throws JSONException {

        return new QuizListData(jo.getString(QUIZ_ID), jo.getString(QUIZ_NAME),
                jo.getString(QUIZ_DESCRIPTION), jo.getString(QUIZ_SHEET_DOC_ID),jo.getString(QUIZ_LOGO_URL));
    }
}
