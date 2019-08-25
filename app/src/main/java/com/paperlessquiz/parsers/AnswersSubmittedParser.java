package com.paperlessquiz.parsers;

import com.paperlessquiz.answer.AnswersSubmitted;
import com.paperlessquiz.quiz.QuizDatabase;

import org.json.JSONException;
import org.json.JSONObject;

public class AnswersSubmittedParser {//implements JsonParser<AnswersSubmitted> {

    /*
    @Override
    public AnswersSubmitted parse(JSONObject jo) throws JSONException {
        int teamNr, roundNr;
        boolean submitted;

        AnswersSubmitted answersSubmitted;
        try {
            teamNr = jo.getInt(QuizDatabase.COLNAME_USER_NR);
            roundNr = jo.getInt(QuizDatabase.COLNAME_ROUND_NR);
            submitted = true;
        } catch (Exception e) {
            teamNr = 1;
            roundNr =1;
            submitted=false;
        }
        answersSubmitted = new AnswersSubmitted(roundNr,teamNr,submitted);
        return answersSubmitted;
    }
    */
}
