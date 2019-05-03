package com.example.paperlessquiz.quizbasics;

import com.example.paperlessquiz.google.adapter.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

public class QuizBasicsParser implements JsonParser<QuizBasics> {
    public QuizBasics parse(JSONObject jo) throws JSONException {
        //Strings here MUSt match the headers in the QuizList tab of the Quizlist sheet
        return new QuizBasics(jo.getString("QuizID"), jo.getString("QuizName"),
                jo.getString("QuizDescription"), jo.getString("QuizSheetDocID"),jo.getBoolean("QuizOpen"));
    }
}
