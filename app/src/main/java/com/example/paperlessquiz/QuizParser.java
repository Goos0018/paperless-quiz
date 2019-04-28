package com.example.paperlessquiz;

import org.json.JSONException;
import org.json.JSONObject;

public class QuizParser extends JsonParser<Quiz> {

    @Override
    protected Quiz parse(JSONObject jo) throws JSONException {
        return new Quiz(jo.getString("QuizID"), jo.getString("QuizName"), jo.getString("QuizDescription"), jo.getString("QuizSheet"),true );
    }
}
