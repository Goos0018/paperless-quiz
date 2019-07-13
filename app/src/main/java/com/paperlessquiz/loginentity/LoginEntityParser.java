package com.paperlessquiz.loginentity;

import com.paperlessquiz.parsers.JsonParser;
import com.paperlessquiz.quiz.QuizGenerator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginEntityParser implements JsonParser<Team> {
    //Headers are taken from the QuizGenerator class and must match those of course

    public Team parse(JSONObject jo) throws JSONException {
        Team team = new Team(jo.getInt(QuizGenerator.LOGIN_ENTITY_ID), jo.getString(QuizGenerator.LOGIN_ENTITY_TYPE),
                jo.getString(QuizGenerator.LOGIN_ENTITY_NAME), jo.getString(QuizGenerator.LOGIN_ENTITY_PASSKEY));
        if (jo.getString(QuizGenerator.LOGIN_ENTITY_TYPE).equals(QuizGenerator.SELECTION_PARTICIPANT)) {
            // If this is a participant, fill out the rest of the info
            try {
                team.setPresent(jo.getBoolean(QuizGenerator.TEAM_PRESENT));
                team.setLoggedIn(jo.getBoolean(QuizGenerator.TEAM_LOGGED_IN));
            }
            catch (Exception e){
                team.setPresent(false);
                team.setLoggedIn(false);
            }
            ArrayList<Boolean> answersSubmitted = new ArrayList<>();
            boolean valueToSet;
            for (int i = QuizGenerator.TEAMSHEET_START_OF_ROUNDS; i < jo.length(); i++) {
               try {
                   valueToSet = jo.getBoolean(QuizGenerator.ROUNDS_PREFIX + (i - QuizGenerator.TEAMSHEET_START_OF_ROUNDS +1));
               }
               catch (Exception e){
                   //if we cannot make sense of what we get, return false
                   valueToSet = false;
                }
                answersSubmitted.add(i - QuizGenerator.TEAMSHEET_START_OF_ROUNDS, Boolean.valueOf(valueToSet));
            }
            team.setAnswerForRndsSubmitted(answersSubmitted);
        }
        return team;
    }
}
