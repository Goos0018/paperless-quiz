package com.example.paperlessquiz.question;

import com.example.paperlessquiz.google.access.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

public class QuestionParser implements JsonParser<Question> {

    //Strings here MUST match the headers in the Questions tab of the Quiz sheet
    public static final String ID = "ID";
    public static final String ROUND_ID = "RoundID";
    public static final String HINT = "Hint";
    public static final String QUESTION = "Question";
    public static final String CORRECT_ANSWER = "CorrectAnswer";
    public static final String MAX_SCORE = "MaxScore";

    @Override
    public Question parse(JSONObject jo) throws JSONException {
        return new Question(jo.getString(ID),jo.getString(ROUND_ID), jo.getString(HINT),
                jo.getString(QUESTION),jo.getString(CORRECT_ANSWER),jo.getInt(MAX_SCORE));
    }
}
