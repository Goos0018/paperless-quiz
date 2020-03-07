package com.paperlessquiz.parsers;

import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.quiz.ResultAfterRound;

import org.json.JSONException;
import org.json.JSONObject;

public class ResultAfterRoundParser implements JsonParser<ResultAfterRound> {

    @Override
    public ResultAfterRound parse(JSONObject jo) throws JSONException {
        int roundNr, teamNr, scoreForThisRound, maxScoreForThisRound, totalScoreAfterThisRound, maxTotalScoreAfterThisRound, posInStandingAfterThisRound;
        ResultAfterRound resultAfterRound;
        try {
            roundNr = jo.getInt(QuizDatabase.COLNAME_ROUND_NR);
            teamNr = jo.getInt(QuizDatabase.COLNAME_USER_NR);
            scoreForThisRound = jo.getInt(QuizDatabase.COLNAME_SCOREFORROUND);
            maxScoreForThisRound = jo.getInt(QuizDatabase.COLNAME_MAXSCOREFORROUND);
            totalScoreAfterThisRound = jo.getInt(QuizDatabase.COLNAME_SCOREAFTERROUND);
            maxTotalScoreAfterThisRound = jo.getInt(QuizDatabase.COLNAME_MAXSCOREAFTERROUND);
            posInStandingAfterThisRound = jo.getInt(QuizDatabase.COLNAME_STANDINGAFTERROUND);
        } catch (Exception e) {
            roundNr=0;
            teamNr=0;
            scoreForThisRound=0;
            maxScoreForThisRound=0;
            totalScoreAfterThisRound=0;
            maxTotalScoreAfterThisRound=0;
            posInStandingAfterThisRound=0;
        }
        resultAfterRound = new ResultAfterRound(teamNr, roundNr, scoreForThisRound, maxScoreForThisRound, totalScoreAfterThisRound, maxTotalScoreAfterThisRound,posInStandingAfterThisRound);
        return resultAfterRound;
    }
}