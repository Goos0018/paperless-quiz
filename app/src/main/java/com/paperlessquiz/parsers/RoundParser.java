package com.paperlessquiz.parsers;

import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.round.Round;

import org.json.JSONException;
import org.json.JSONObject;

public class RoundParser implements JsonParser<Round> {

    @Override
    public Round parse(JSONObject jo) throws JSONException {
        int idQuiz, idRound, roundNr, roundStatus;
        String roundName, roundDescription;
        Round round;
        try {
            idQuiz = jo.getInt(QuizDatabase.COLNAME_ID_QUIZ);
            idRound = jo.getInt(QuizDatabase.COLNAME_ID_ROUND);
            roundNr = jo.getInt(QuizDatabase.COLNAME_ROUND_NR);
            roundStatus = jo.getInt(QuizDatabase.COLNAME_ROUND_STATUS);
            roundDescription = jo.getString(QuizDatabase.COLNAME_ROUND_DESCRIPTION);
            roundName = jo.getString(QuizDatabase.COLNAME_ROUND_NAME);
        } catch (Exception e) {
            idQuiz = 0;
            idRound = 0;
            roundNr=0;
            roundStatus=0;
            roundDescription="";
            roundName = "Error parsing " + jo.toString();
        }
        round = new Round(idQuiz, idRound,roundNr, roundStatus, roundName,roundDescription);
        return round;
    }
}
