package com.example.paperlessquiz.round;

import com.example.paperlessquiz.google.access.JsonParser;
import com.example.paperlessquiz.quiz.QuizGenerator;

import org.json.JSONException;
import org.json.JSONObject;

public class RoundParser implements JsonParser<Round> {
    //Headers are taken from the QuizGenerator class and must match those of course

    @Override
    public Round parse(JSONObject jo) throws JSONException {
        Round round;
        try {
            round = new Round(jo.getInt(QuizGenerator.ROUND_NR), jo.getString(QuizGenerator.ROUND_NAME),
                    jo.getString(QuizGenerator.ROUND_DESCRIPTION), jo.getInt(QuizGenerator.ROUND_NR_OF_QUESTIONS),
                    jo.getBoolean(QuizGenerator.ROUND_ACCEPTS_ANSWERS), jo.getBoolean(QuizGenerator.ROUND_ACCEPTS_CORRECTIONS),
                    jo.getBoolean(QuizGenerator.ROUND_IS_CORRECTED));
        } catch (Exception e){
            round = new Round(jo.getInt(QuizGenerator.ROUND_NR), jo.getString(QuizGenerator.ROUND_NAME),
                    jo.getString(QuizGenerator.ROUND_DESCRIPTION), jo.getInt(QuizGenerator.ROUND_NR_OF_QUESTIONS),
                    false, false,false);
        }
        return round;
    }
}
