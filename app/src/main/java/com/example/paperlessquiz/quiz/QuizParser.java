package com.example.paperlessquiz.quiz;

import com.example.paperlessquiz.google.adapter.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

public class QuizParser implements JsonParser<Quiz>
{
    public Quiz parse(JSONObject jo) throws JSONException {
        return new Quiz(jo.getString("QuizName"), jo.getString("QuizDescription"),
                jo.getString("QuizSheetDocID"),jo.getBoolean("QuizOpen"));
    }

}
