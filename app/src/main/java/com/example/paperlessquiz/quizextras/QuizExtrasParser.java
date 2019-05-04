package com.example.paperlessquiz.quizextras;

import com.example.paperlessquiz.google.access.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

public class QuizExtrasParser implements JsonParser<QuizExtras>
{
    public QuizExtras parse(JSONObject jo) throws JSONException {
        return new QuizExtras(jo.getBoolean("QuizOpen"),
                jo.getInt("QuizNrOfRounds"),jo.getInt("QuizNrOfParticipants"));
    }

}
