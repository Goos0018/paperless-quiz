package com.example.paperlessquiz;

import org.json.JSONException;
import org.json.JSONObject;

public class QuestionParser implements JsonParser<Question> {

    @Override
    public Question parse(JSONObject jo) throws JSONException {
        return new Question();
    }
}
