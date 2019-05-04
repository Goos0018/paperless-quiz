package com.example.paperlessquiz.quizbasics;

import com.example.paperlessquiz.google.access.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;
//TODO : extract strings to constants
public class QuizBasicsParser implements JsonParser<QuizBasics> {
    public QuizBasics parse(JSONObject jo) throws JSONException {
        //Strings here MUST match the headers in the QuizList tab of the Quizlist sheet
        return new QuizBasics(jo.getString("QuizID"), jo.getString("QuizName"),
                jo.getString("QuizDescription"), jo.getString("QuizSheetDocID"));
    }
}
