package com.paperlessquiz.parsers;

import com.paperlessquiz.AnswersSubmitted;
import com.paperlessquiz.quiz.QuizDatabase;

import org.json.JSONException;
import org.json.JSONObject;

public class AnswersSubmittedParser implements JsonParser<AnswersSubmitted> {

    @Override
    public AnswersSubmitted parse(JSONObject jo) throws JSONException {
        int idTeam, idRound;
        boolean submitted;

        AnswersSubmitted answersSubmitted;
        try {
            idTeam = jo.getInt(QuizDatabase.COLNAME_ID_USER);
            idRound = jo.getInt(QuizDatabase.COLNAME_ID_ROUND);
            submitted = !(jo.getInt(QuizDatabase.COLNAME_SUBMITTED)==0);
        } catch (Exception e) {
            idTeam = 0;
            idRound=0;
            submitted=false;
        }
        answersSubmitted = new AnswersSubmitted(idRound,idTeam,submitted);
        return answersSubmitted;
    }
}
