package com.example.paperlessquiz.quizgetter;

import com.example.paperlessquiz.google.adapter.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

public class QuizGetterParser implements JsonParser<QuizGetter> {
    public QuizGetter parse(JSONObject jo) throws JSONException {
        //Strings here MUSt match the headers in the QuizList tab of the Quizlist sheet
        return new QuizGetter(jo.getString("QuizID"), jo.getString("QuizName"),
                jo.getString("QuizDescription"), jo.getString("QuizSheetDocID"),jo.getBoolean("QuizOpen"));
    }
}
