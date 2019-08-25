package com.paperlessquiz.parsers;

import com.paperlessquiz.quiz.QuizDatabase;

import org.json.JSONException;
import org.json.JSONObject;

public class AnswerStatsParser implements JsonParser<Integer> {

    @Override
    public Integer parse(JSONObject jo) throws JSONException {
        int theStat;
        try {
            theStat = jo.getInt(QuizDatabase.COLNAME_NROFANSWERS);
        } catch (Exception e) {
            theStat = 0;
        }
        return Integer.valueOf(theStat);
    }
}