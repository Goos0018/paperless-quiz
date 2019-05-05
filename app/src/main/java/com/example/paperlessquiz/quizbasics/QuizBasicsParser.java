package com.example.paperlessquiz.quizbasics;

import com.example.paperlessquiz.google.access.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;
//TODO : extract strings to constants
public class QuizBasicsParser implements JsonParser<QuizBasics> {

    //Strings here MUST match the headers in the QuizList tab of the Quizlist sheet
    public static final String QUIZ_ID = "QuizID";
    public static final String QUIZ_NAME = "QuizName";
    public static final String QUIZ_DESCRIPTION = "QuizDescription";
    public static final String QUIZ_SHEET_DOC_ID = "QuizSheetDocID";

    public QuizBasics parse(JSONObject jo) throws JSONException {

        return new QuizBasics(jo.getString(QUIZ_ID), jo.getString(QUIZ_NAME),
                jo.getString(QUIZ_DESCRIPTION), jo.getString(QUIZ_SHEET_DOC_ID));
    }
}
