package com.example.paperlessquiz.quizextradata;

import com.example.paperlessquiz.google.access.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

public class QuizExtraDataParser implements JsonParser<QuizExtraData>
{
    //Strings here MUST match the headers in the QuizData tab of the Quiz sheet
    public static final String QUIZ_OPEN = "QuizOpen";
    public static final String QUIZ_NR_OF_ROUNDS = "QuizNrOfRounds";
    public static final String QUIZ_NR_OF_PARTICIPANTS = "QuizNrOfParticipants";
    public static final String QUIZ_EXTRAS_SHEETNAME = "QuizData";

    public QuizExtraData parse(JSONObject jo) throws JSONException {
        return new QuizExtraData(jo.getBoolean(QUIZ_OPEN),
                jo.getInt(QUIZ_NR_OF_ROUNDS),jo.getInt(QUIZ_NR_OF_PARTICIPANTS));
    }

}
