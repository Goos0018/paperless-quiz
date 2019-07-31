package com.paperlessquiz.parsers;

import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.quiz.ResultAfterRound;

import org.json.JSONException;
import org.json.JSONObject;

public class ResultAfterRoundParser implements JsonParser<ResultAfterRound> {

    @Override
    public ResultAfterRound parse(JSONObject jo) throws JSONException {
        int roundNr, teamNr, scoreForThisRound, totalScoreAfterThisRound, posInStandingForThisRound, posInStandingAfterThisRound;
        ResultAfterRound resultAfterRound;
        try {
            roundNr = jo.getInt(QuizDatabase.COLNAME_ROUND_NR);
            teamNr = jo.getInt(QuizDatabase.COLNAME_USER_NR);
            scoreForThisRound = jo.getInt(QuizDatabase.COLNAME_SCOREFORROUND);
            totalScoreAfterThisRound = jo.getInt(QuizDatabase.COLNAME_SCOREAFTERROUND);
            posInStandingForThisRound = jo.getInt(QuizDatabase.COLNAME_STANDINGFORROUND);
            posInStandingAfterThisRound = jo.getInt(QuizDatabase.COLNAME_STANDINGAFTERROUND);
        } catch (Exception e) {
            roundNr=0;
            teamNr=0;
            scoreForThisRound=0;
            totalScoreAfterThisRound=0;
            posInStandingForThisRound=0;
            posInStandingAfterThisRound=0;
        }
        resultAfterRound = new ResultAfterRound(teamNr, roundNr, scoreForThisRound, posInStandingForThisRound, totalScoreAfterThisRound, posInStandingAfterThisRound);
        return resultAfterRound;
    }
}