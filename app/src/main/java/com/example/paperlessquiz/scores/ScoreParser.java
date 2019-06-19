package com.example.paperlessquiz.scores;

import com.example.paperlessquiz.google.access.JsonParser;
import com.example.paperlessquiz.quiz.QuizGenerator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
//TODO:addlogtoQuizsettingsoverviewiferroristhrown

public class ScoreParser implements JsonParser<Score> {
    //Headers are taken from the QuizGenerator class and must match those of course

    @Override
    public Score parse(JSONObject jo) throws JSONException {
        Score score = new Score(jo.getInt(QuizGenerator.LOGIN_ENTITY_ID), jo.getString(QuizGenerator.LOGIN_ENTITY_NAME));
        try {
            score.setCurrentTotalScore(jo.getInt(QuizGenerator.SCORE_TOTAL));
        } catch (Exception e) {
            score.setCurrentTotalScore(0);
        }
        ArrayList<Integer> scorePerRoundForTeam = new ArrayList<>();
        int scoreToSet;
        for (int i = QuizGenerator.SCORESHEET_START_OF_ROUNDS; i < jo.length(); i++) {
            try {
                scoreToSet = jo.getInt(QuizGenerator.SCORE_RND_INDICATOR + (i - QuizGenerator.SCORESHEET_START_OF_ROUNDS + 1));
            } catch (Exception e) {
                //if we cannot make sense of what we get, return false
                scoreToSet = 0;
            }
            scorePerRoundForTeam.add(i - QuizGenerator.SCORESHEET_START_OF_ROUNDS, Integer.valueOf(scoreToSet));
        }
        score.setScorePerRoundForTeam(scorePerRoundForTeam);
        return score;
    }
}
