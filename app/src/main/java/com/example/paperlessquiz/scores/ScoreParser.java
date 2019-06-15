package com.example.paperlessquiz.scores;

import com.example.paperlessquiz.google.access.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
//TODO:addlogtoQuizsettingsoverviewiferroristhrown

public class ScoreParser implements JsonParser<Score> {

    //Strings here MUST match the headers in the Score tab of the Quiz sheet
    public static final String SCORE_TEAMID = "TeamID";
    public static final String SCORE_TEAMNAME = "Name";
    public static final String SCORE_TOTAL = "Total";
    public static final String SCORE_RND_INDICATOR = "Round";
    public static final int SCORE_START_OF_ROUNDS = 4;

    @Override
    public Score parse(JSONObject jo) throws JSONException {
        Score score = new Score(jo.getInt(SCORE_TEAMID), jo.getString(SCORE_TEAMNAME));
        try {
            score.setTotalScore(jo.getInt(SCORE_TOTAL));
        } catch (Exception e) {
            score.setTotalScore(0);
        }
        ArrayList<Integer> scorePerRoundForTeam = new ArrayList<>();
        int scoreToSet;
        for (int i = SCORE_START_OF_ROUNDS; i < jo.length(); i++) {
            try {
                scoreToSet = jo.getInt(SCORE_RND_INDICATOR + (i - SCORE_START_OF_ROUNDS + 1));
            } catch (Exception e) {
                //if we cannot make sense of what we get, return false
                scoreToSet = 0;
            }
            scorePerRoundForTeam.add(i - SCORE_START_OF_ROUNDS, Integer.valueOf(scoreToSet));
        }
        score.setScorePerRoundForTeam(scorePerRoundForTeam);
        return score;
    }
}
