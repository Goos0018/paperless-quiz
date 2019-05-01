package com.example.paperlessquiz.question;

import com.example.paperlessquiz.google.adapter.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

public class QuestionParser implements JsonParser<Question> {

    @Override
    public Question parse(JSONObject jo) throws JSONException {
        return new Question();
    }
}
